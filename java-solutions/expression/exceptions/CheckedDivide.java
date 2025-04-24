package expression.exceptions;

import expression.Divide;
import expression.UltimateExpression;

import java.util.List;

public class CheckedDivide extends Divide {
    public CheckedDivide(UltimateExpression left, UltimateExpression right) {
        super(left, right);
    }

    public int evaluate(int x, int y, int z) {
        int l = left.evaluate(x, y, z);
        int r = right.evaluate(x, y, z);
        if (r == 0) {
            throw new ArithmeticException("division by zero");
        }
        if (r == -1 && l == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }
        return l / r;
    }

    public int evaluate(List<Integer> variables) {
        int l = left.evaluate(variables);
        int r = right.evaluate(variables);
        if (r == 0) {
            throw new ArithmeticException("division by zero");
        }
        if (r == -1 && l == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }
        return l / r;
    }
}
