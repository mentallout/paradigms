package expression.exceptions;

import expression.Add;
import expression.UltimateExpression;

import java.util.List;

public class CheckedAdd extends Add {
    public CheckedAdd(UltimateExpression left, UltimateExpression right) {
        super(left, right);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int l = left.evaluate(x, y, z);
        int r = right.evaluate(x, y, z);
        if ((r > 0 && l > Integer.MAX_VALUE - r) || (r < 0 && l < Integer.MIN_VALUE - r)) {
            throw new ArithmeticException("overflow");
        }
        return l + r;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        int l = left.evaluate(variables);
        int r = right.evaluate(variables);
        if ((r > 0 && l > Integer.MAX_VALUE - r) || (r < 0 && l < Integer.MIN_VALUE - r)) {
            throw new ArithmeticException("overflow");
        }
        return l + r;
    }
}