package expression;

import java.util.Objects;

public abstract class Operation implements UltimateExpression {
    protected final UltimateExpression left, right;

    public Operation(UltimateExpression left, UltimateExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate(int x) {
        return left.evaluate(x);
    }

    @Override
    public abstract int evaluate(int x, int y, int z);

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        }
        return ((Operation) o).left.equals(left) && ((Operation) o).right.equals(right);
    }

    final public int hashCode() {
        return Objects.hash(this.left, this.right, getClass());
    }
}