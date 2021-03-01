package backend.utility;

public class BadPasswordException extends Exception{
    private String message = null;

    public BadPasswordException(String message) {
        this.message = message;
    }
}
