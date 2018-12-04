package pl.polsl.lab.worldcupstatsclient.exceptions;

/**
 * Exception class for objects thrown when command line parameters are incorrect
 *
 *
 * @author Paweł Szwajnoch
 * @version 1.3
 */
public class WrongCommandLineParameterException extends Exception {

    public WrongCommandLineParameterException() {
        super();
    }

    public WrongCommandLineParameterException(String message) {
        super(message);
    }

    public WrongCommandLineParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongCommandLineParameterException(Throwable cause) {
        super(cause);
    }

}
