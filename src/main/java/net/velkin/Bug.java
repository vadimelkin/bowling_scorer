package net.velkin;

// Any errors that are not due to the incorrect user input are bugs, and are represented by this unchecked exception
class Bug extends RuntimeException {
    public Bug() {
        super();
    }

    public Bug(String message) {
        super(message);
    }

    public Bug(String message, Throwable cause) {
        super(message, cause);
    }

    public Bug(Throwable cause) {
        super(cause);
    }
}
