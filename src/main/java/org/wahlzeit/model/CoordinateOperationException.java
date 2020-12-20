package org.wahlzeit.model;

public class CoordinateOperationException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -5271612211677010590L;

    public CoordinateOperationException() {
        super();
    }

    public CoordinateOperationException(String message) {
        super(message);
    }

    public CoordinateOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoordinateOperationException(Throwable cause) {
        super(cause);
    }
}