package pl.polsl.lab.worldcupstats.exceptions;

/**
 *
 * Exception class for objects thrown when user try to add team to group that
 * already has 4 teams
 *
 * @author Paweł Szwajnoch
 * @version 1.0
 */
public class TooManyTeamsException extends Exception {

    public TooManyTeamsException() {
        super();
    }

    public TooManyTeamsException(String message) {
        super(message);
    }

    public TooManyTeamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyTeamsException(Throwable cause) {
        super(cause);
    }
}
