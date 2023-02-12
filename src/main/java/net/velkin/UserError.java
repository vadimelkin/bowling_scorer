package net.velkin;

// Any errors that an end user can cause but also can correct, are represented by this checked exception
class UserError extends Exception {
    public UserError() {
        super();
    }

    public UserError(String message) {
        super(message);
    }

    public UserError(String message, Throwable cause) {
        super(message, cause);
    }

    public UserError(Throwable cause) {
        super(cause);
    }
}
