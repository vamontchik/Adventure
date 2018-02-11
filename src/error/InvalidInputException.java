package error;

/**
 * Exception class representing when an invalid input is passed in by user or by program. Used extensively
 * to cover different possible ways to have bad input (such as the user attempting to do a bad move or the program
 * not parsing in the correct values). Message string included.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
