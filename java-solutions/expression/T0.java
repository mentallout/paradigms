package expression;

import java.util.List;
import java.util.Objects;

public class T0 implements UltimateExpression {
    private final UltimateExpression value;

    public T0(UltimateExpression value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return Integer.numberOfTrailingZeros(value.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.numberOfTrailingZeros(value.evaluate(x, y, z));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return Integer.numberOfTrailingZeros(value.evaluate(variables));
    }

    public String toString() {
        return "t0(" + value + ")";
    }

    public int hashCode() {
        return Objects.hashCode(value);
    }
}
