package expression.exceptions;

import expression.*;
import expression.parser.ListParser;
import expression.parser.TripleParser;

import java.util.Arrays;
import java.util.List;

public class ExpressionParser extends BaseParser implements TripleParser, ListParser {
    private final List<Character> allowed = Arrays.asList('(', ')', '+', '-', '*', '/', 'x', 'y', 'z', 'l', 't');

    public void catchException(String expression) {
        int opening = 0;
        int closing = 0;
        int args = 0;
        int digits = 0;
        int ops = 0;
        boolean first = false;
        for (char chars : expression.toCharArray()) {
            if (!allowed.contains(chars) && !Character.isDigit(chars) && !Character.isWhitespace(chars) && chars != '$') {
                    throw new IllegalArgumentException("Unexpected symbol: " + chars);
            }
            if (Character.isLetter(chars)) {
                if (!first) {
                    first = true;
                }
                args++;
            }
            if (Character.isDigit(chars)) {
                if (!first) {
                    first = true;
                }
                digits++;
            }
            if (chars == '+' || chars == '-' || chars == '*' || chars == '/') {
                ops++;
                if (chars != '-' && !first) {
                    throw new MissingArgumentException("No first argument");
                } else {
                    first = true;
                }
            }
            if (chars == '(') {
                if (!first) {
                    first = true;
                }
                opening++;
            }
            if (chars == ')') {
                if (!first) {
                    first = true;
                }
                closing++;
            }
        }
        if (opening < closing) {
            throw new MissingArgumentException("No opening parenthesis");
        } else if (opening > closing) {
            throw new MissingArgumentException("No closing parenthesis");
        }
        if (ops == 0 && digits == 0 && args == 0) {
            throw new MissingArgumentException("No arguments and operands at all");
        } else if (ops > 0 && digits == 0 && args == 0) {
            throw new ExpressionException("Bare operand");
        }
        BaseParser(new StringSource(expression));
        skipWhitespace();
    }
    @Override
    public UltimateExpression parse(String expression) {
        catchException(expression);
        return parseLvl4();
    }

    @Override
    public UltimateExpression parse(String expression, List<String> variables) {
        catchException(expression);
        return parseLvl4();
    }

    private UltimateExpression parseLvl4() {
        skipWhitespace();
        UltimateExpression exp = parseLvl3();
        skipWhitespace();
        while (eof()) if (take('+')) {
            exp = new CheckedAdd(exp, parseLvl3());
        } else if (take('-')) {
            exp = new CheckedSubtract(exp, parseLvl3());
        } else return exp;
        return exp;
    }

    private UltimateExpression parseLvl3() {
        skipWhitespace();
        UltimateExpression exp = parseLvl2();
        skipWhitespace();
        while (eof()) if (take('*')) {
            exp = new CheckedMultiply(exp, parseLvl2());
        } else if (take('/')) {
            exp = new CheckedDivide(exp, parseLvl2());
        } else return exp;
        return exp;
    }

    private UltimateExpression parseLvl2() {
        skipWhitespace();
        if (take('l')) {
            expect('0');
            if (ch == ' ' || ch == '(') {
                skipWhitespace();
                return new L0(parseLvl2());
            } else {
                throw new WrongUsageException("Wrong l0 usage");
            }
        } else if (take('t')) {
            expect('0');
            if (ch == ' ' || ch == '(') {
                skipWhitespace();
                return new T0(parseLvl2());
            } else {
                throw new WrongUsageException("Wrong t0 usage");
            }
        }
        return parseLvl1();
    }

    private UltimateExpression parseLvl1() {
        skipWhitespace();
        if (take('(')) {
            UltimateExpression exp = parseLvl4();
            skipWhitespace();
            expect(')');
            skipWhitespace();
            return exp;
        } else if (between('0', '9')) {
            UltimateExpression exp = parseConst("");
            skipWhitespace();
            if (ch != '+' && ch != '-' && ch != '*' && ch != '/' && ch != ')' && eof()) {
                throw new WrongUsageException("Spaces between numbers");
            }
            skipWhitespace();
            return exp;
        } else if (take('$')) {
            UltimateExpression exp = parseVariable();
            skipWhitespace();
            return exp;
        } else if (between('x', 'z')) {
            skipWhitespace();
            UltimateExpression exp = parseVariable();
            skipWhitespace();
            return exp;
        } else if (take('-')) {
            if (between('0', '9')) {
                UltimateExpression exp = parseConst("-");
                skipWhitespace();
                return exp;
            } else {
                skipWhitespace();
                return new CheckedNegate(parseLvl2());
            }
        } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
            skipWhitespace();
            if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                throw new MissingArgumentException("Two operands in a row");
            }
        }
        throw new MissingArgumentException("No last argument");

    }

    private UltimateExpression parseConst(String s) {
        StringBuilder sb = new StringBuilder(s);
        while (between('0', '9')) {
            sb.append(ch);
            take();
        }
        try {
            return new Const(Integer.parseInt(sb.toString()));
        } catch (NumberFormatException e) {
            throw new OverflowException("Constant overflow: " + sb);
        }
    }

    private UltimateExpression parseVariable() {
        char temp = take();
        try {
            return new Variable(Integer.parseInt(String.valueOf(temp)));
        } catch (NumberFormatException ignored) {
        }
        return new Variable(String.valueOf(temp));
    }
}