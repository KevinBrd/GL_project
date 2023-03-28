public class NoSwitchCaseValidException extends RuntimeException {
    private String error;

    public NoSwitchCaseValidException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
