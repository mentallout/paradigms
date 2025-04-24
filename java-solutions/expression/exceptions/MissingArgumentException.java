package expression.exceptions;

public class MissingArgumentException extends IllegalArgumentException {
    public MissingArgumentException(String str) {
        super(str);
    }
}
