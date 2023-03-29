package org.testfx;

public class NoValidSwitchCaseException extends RuntimeException {
    private String error;

    public NoValidSwitchCaseException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
