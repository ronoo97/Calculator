package calculator.core.errors;

public final class MemoryOverflowException extends CustomException {

    public MemoryOverflowException() {
        super();
    }

    public MemoryOverflowException(String message) {
        super(message);
    }

}
