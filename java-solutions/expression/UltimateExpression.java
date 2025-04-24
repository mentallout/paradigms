package expression;

public interface UltimateExpression extends Expression, TripleExpression, ListExpression {
    @Override
    int evaluate(int x);

    @Override
    int evaluate(int x, int y, int z);
}
