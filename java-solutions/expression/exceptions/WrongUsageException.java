package expression.exceptions;

public class WrongUsageException extends IllegalArgumentException {
    public WrongUsageException(String str) {
        super(str);
    }
}
