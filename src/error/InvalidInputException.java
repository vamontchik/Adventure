package error;

/**
 * Exception class representing when an invalid input is passed in by user or other means. Message string included.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
