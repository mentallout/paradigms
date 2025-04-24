package expression;

import java.util.List;
import java.util.Objects;

public class Variable implements UltimateExpression {
    private String variable = "";
    private int no;

    public Variable(String variable) {
        this.variable = variable;
    }

    public Variable(int no) {
        this.no = no;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return variables.get(no);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (variable.equals("x")) {
            return x;
        } else if (variable.equals("y")) {
            return y;
        } else {
            return z;
        }
    }


    public String toString() {
        if (this.variable.isEmpty()) {
            this.variable = "$" + String.valueOf(no);
        }
        return variable;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        }
        return variable.equals(((Variable) o).variable);
    }

    public int hashCode() {
        return Objects.hashCode(variable);
    }
}
