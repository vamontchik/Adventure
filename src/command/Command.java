package command;

import error.InvalidInputException;

/**
 * Command interface from which all Commands inherit from.
 */
public interface Command {
    void execute() throws InvalidInputException;
}
