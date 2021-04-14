package backend.utility;

public class BadPasswordException extends Exception{
    private final String message;

    public BadPasswordException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
