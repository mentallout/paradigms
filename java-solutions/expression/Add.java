package expression;

import java.util.List;

public class Add extends Operation {
    public Add(UltimateExpression left, UltimateExpression right) {
        super(left, right);
    }

    @Override
    public int evaluate(int x) {
        return left.evaluate(x) + right.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return left.evaluate(x, y, z) + right.evaluate(x, y, z);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return left.evaluate(variables) + right.evaluate(variables);
    }

    @Override
    public String toString() {
        return "(" + left + " + " + right + ")";
    }
}