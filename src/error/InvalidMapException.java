package error;

/**
 * Exception class representing when the MapValidator.validate() method fails. Message string included.
 */
public class InvalidMapException extends Exception {
    public InvalidMapException(String message) {
        super(message);
    }
}
