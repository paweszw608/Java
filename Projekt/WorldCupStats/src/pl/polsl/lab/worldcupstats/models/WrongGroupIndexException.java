package pl.polsl.lab.worldcupstats.models;

/**
 *
 * Exception class for objects thrown when user try to add teams to group that
 * doesn't exists
 *
 * @author Paweł Szwajnoch
 * @version 1.0
 */
public class WrongGroupIndexException extends Exception {

    public WrongGroupIndexException() {
        super();
    }

    public WrongGroupIndexException(String message) {
        super(message);
    }

    public WrongGroupIndexException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongGroupIndexException(Throwable cause) {
        super(cause);
    }
}
