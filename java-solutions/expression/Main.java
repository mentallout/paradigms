package expression;

public class Main {
    public static void main(String[] args) {
        try {
            boolean example = new Multiply(new Const(2), new Variable("x"))
                    .equals(new Multiply(new Const(2), new Variable("x")));
            //System.out.println(example);
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }
    }
}