package backend.authentication;

public class UnsuccessfulLoginException extends Exception{

    private String error = null;

    public UnsuccessfulLoginException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
