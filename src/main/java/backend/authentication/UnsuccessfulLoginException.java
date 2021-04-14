package backend.authentication;

public class UnsuccessfulLoginException extends Exception{

    private final String error;

    public UnsuccessfulLoginException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
