package org.wahlzeit.model;

public class IllegalResultException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -5271612211677010590L;

    public IllegalResultException() {
        super();
    }

    public IllegalResultException(String message) {
        super(message);
    }

    public IllegalResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalResultException(Throwable cause) {
        super(cause);
    }
}
