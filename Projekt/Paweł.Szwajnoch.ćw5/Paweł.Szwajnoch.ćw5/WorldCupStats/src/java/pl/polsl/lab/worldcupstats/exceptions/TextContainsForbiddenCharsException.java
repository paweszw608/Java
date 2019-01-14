package pl.polsl.lab.worldcupstats.exceptions;

/**
 *
 * Exception class for objects thrown when user try to set team name text
 * contains of other charactres than letters (like numbers, commas...) or is
 * empty
 *
 * @author Paweł Szwajnoch
 * @version 1.0
 */
public class TextContainsForbiddenCharsException extends Exception {

    public TextContainsForbiddenCharsException() {
        super();
    }

    public TextContainsForbiddenCharsException(String message) {
        super(message);
    }

    public TextContainsForbiddenCharsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextContainsForbiddenCharsException(Throwable cause) {
        super(cause);
    }
}
