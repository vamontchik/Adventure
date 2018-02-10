package command;

import error.InvalidInputException;

public interface Command {
    void execute() throws InvalidInputException;
}
