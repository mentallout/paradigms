package expression;

import java.util.List;
import java.util.Objects;

public class L0 implements UltimateExpression {
    private final UltimateExpression value;

    public L0(UltimateExpression value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return Integer.numberOfLeadingZeros(value.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.numberOfLeadingZeros(value.evaluate(x, y, z));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return Integer.numberOfLeadingZeros(value.evaluate(variables));
    }

    public String toString() {
        return "l0(" + value + ")";
    }

    public int hashCode() {
        return Objects.hashCode(value);
    }
}
