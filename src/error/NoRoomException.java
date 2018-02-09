package error;

/**
 * Exception class representing when a Room is not found. Message string included.
 */
public class NoRoomException extends Exception {
    public NoRoomException(String message) {
        super(message);
    }
}
