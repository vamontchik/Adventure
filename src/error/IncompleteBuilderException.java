package error;

/**
 * Exception class representing when the Builder is not used correctly. Message string included.
 */
public class IncompleteBuilderException extends Exception {
    public IncompleteBuilderException(String message) {
        super(message);
    }
}
