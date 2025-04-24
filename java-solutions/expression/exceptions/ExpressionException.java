package expression.exceptions;

import expression.Expression;

public class ExpressionException extends RuntimeException {
    public ExpressionException(String str) {
        super(str);
    }
}
