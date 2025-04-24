package expression.exceptions;

import expression.Negate;
import expression.UltimateExpression;

import java.util.List;

public class CheckedNegate extends Negate {
    public CheckedNegate(UltimateExpression value) {
        super(value);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int res = value.evaluate(x, y, z);
        if (res == Integer.MIN_VALUE) {
            throw new RuntimeException("overflow");
        }
        return res * -1;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        int res = value.evaluate(variables);
        if (res == Integer.MIN_VALUE) {
            throw new RuntimeException("overflow");
        }
        return res * -1;
    }
}