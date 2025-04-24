package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements TripleParser {
    private int temp;
    @Override
    public UltimateExpression parse(String expression) {
        StringBuilder sb = new StringBuilder();
        for (char chars : expression.toCharArray()) {
            if (!Character.isWhitespace(chars)) {
                sb.append(chars);
            }
        }
        BaseParser(new StringSource(sb.toString()));
        return parseLvl6();
    }

    private UltimateExpression parseLvl6() {
        UltimateExpression exp = parseLvl5();
        while (!eof()) if (take('|')) {
            exp = new Or(exp, parseLvl5());
        } else return exp;
        return exp;
    }

    private UltimateExpression parseLvl5() {
        UltimateExpression exp = parseLvl4();
        while (!eof()) if (take('^')) {
            exp = new Xor(exp, parseLvl4());
        } else return exp;
        return exp;
    }

    private UltimateExpression parseLvl4() {
        UltimateExpression exp = parseLvl3();
        while (!eof()) if (take('&')) {
            exp = new And(exp, parseLvl3());
        } else return exp;
        return exp;
    }


    private UltimateExpression parseLvl3() {
        UltimateExpression exp = parseLvl2();
        while (!eof()) if (take('+')) {
            exp = new Add(exp, parseLvl2());
        } else if (take('-')) {
            exp = new Subtract(exp, parseLvl2());
        } else return exp;
        return exp;
    }

    private UltimateExpression parseLvl2() {
        UltimateExpression exp = parseLvl1();
        while (!eof()) if (take('*')) {
            exp = new Multiply(exp, parseLvl1());
        } else if (take('/')) {
            exp = new Divide(exp, parseLvl1());
        } else return exp;
        return exp;
    }
    private UltimateExpression parseLvl1() {
        if (take('(')) {
            UltimateExpression exp = parseLvl6();
            expect(')');
            return exp;
        } else if (between('0', '9')) {
            return parseConst("");
        } else if (between('x', 'z')) {
            return parseVariable();
        } else if (take('-')) {
            if (between('0', '9')) {
                return parseConst("-");
            } else if (between('x', 'z')) {
                return new Negate(parseVariable());
            } else if (take('(')) {
                UltimateExpression exp = new Negate(parseLvl6());
                expect(')');
                return exp;
            } else {
                take();
                int cnt = 0;
                while (take('-')) {
                    cnt++;
                }
                if (cnt % 2 != 0) {
                    if (take('(')) {
                        UltimateExpression exp = new Negate(parseLvl6());
                        expect(')');
                        return exp;
                    } else {
                        if (between('0', '9')) {
                            return parseConst("-");
                        } else {
                            return new Negate(parseVariable());
                        }
                    }
                } else {
                    if (take('(')) {
                        UltimateExpression exp = parseLvl6();
                        expect(')');
                        return exp;
                    } else {
                        if (between('0', '9')) {
                            return parseConst("");
                        } else {
                            return parseVariable();
                        }
                    }
                }
            }

        }
        return parseLvl6();
    }

    private UltimateExpression parseConst(String s) {
        StringBuilder sb = new StringBuilder(s);
        while (between('0', '9')) {
            sb.append(ch);
            take();
        }
        try {
            temp = Integer.parseInt(sb.toString());
        } catch (NumberFormatException ignored)  {
        }
        return new Const(temp);
    }
    private UltimateExpression parseVariable() {
        return new Variable(String.valueOf(take()));
    }
}