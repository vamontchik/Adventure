package error;

/**
 * Exception class representing when a Monster is not found. Message string included.
 */
public class MonsterNotFoundException extends Exception {
    public MonsterNotFoundException(String message) {
        super(message);
    }
}
