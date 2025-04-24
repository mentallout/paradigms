package expression;

import java.util.List;
import java.util.Objects;

public class Const implements UltimateExpression {
    private final int constant;

    public Const(int constant) {
        this.constant = constant;
    }

    @Override
    public int evaluate(int x) {
        return constant;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return constant;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return constant;
    }

    public String toString() {
        return String.valueOf(constant);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        }
        return ((Const) o).constant == constant;
    }

    public int hashCode() {
        return Objects.hashCode(this.constant);
    }
}
