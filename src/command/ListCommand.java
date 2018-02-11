package command;

import console.Console;
import data.Layout;

/**
 * Command representing the listing of items that Player has.
 */
public class ListCommand implements Command {
    private Layout layout;

    public ListCommand(Layout layout) {
        this.layout = layout;
    }

    @Override
    public void execute() {
        Console.printPlayerContents(layout.getPlayer());
    }
}
