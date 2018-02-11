package command;

/**
 * Command representing the closure of the game.
 */
public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.exit(0);
    }
}
