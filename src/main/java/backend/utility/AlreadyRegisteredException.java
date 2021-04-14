package backend.utility;

public class AlreadyRegisteredException extends Exception{
    private final String message;

    public AlreadyRegisteredException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
