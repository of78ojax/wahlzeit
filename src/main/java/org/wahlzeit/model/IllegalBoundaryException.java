package org.wahlzeit.model;

public class IllegalBoundaryException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -5271612211677010590L;

    public IllegalBoundaryException() {
        super();
    }

    public IllegalBoundaryException(String message) {
        super(message);
    }

    public IllegalBoundaryException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalBoundaryException(Throwable cause) {
        super(cause);
    }
}
