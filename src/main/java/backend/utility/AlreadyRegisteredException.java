package backend.utility;

public class AlreadyRegisteredException extends Exception{
    private String message = null;

    public AlreadyRegisteredException(String message) {
        this.message = message;
    }
}
