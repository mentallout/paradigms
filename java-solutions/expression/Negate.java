package expression;

import java.util.List;
import java.util.Objects;

public class Negate implements UltimateExpression {
    protected final UltimateExpression value;

    public Negate(UltimateExpression value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return value.evaluate(x) * -1;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value.evaluate(x, y, z) * -1;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return value.evaluate(variables) * -1;
    }

    public String toString() {
        return "-(" + value + ")";
    }

    public int hashCode() {
        return Objects.hashCode(value);
    }
}
