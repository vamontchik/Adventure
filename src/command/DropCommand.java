package command;

import console.Console;
import data.Item;
import data.Player;
import error.InvalidInputException;

/**
 * Command representing the player dropping an item.
 */
public class DropCommand implements Command {
    private Player player;
    private String userItemName;

    public DropCommand(Player player, String userItemName) {
        this.player = player;
        this.userItemName = userItemName;
    }

    @Override
    public void execute() throws InvalidInputException {
        //Obtain all the items in the room
        Item[] roomItems = player.getItems();

        //Check to see if this is a valid item to pick up. If so, add the item to the room's inventory and
        //remove it from the player's inventory. Else, throw an InvalidInputException specifying that it cannot do so.
        for (Item item : roomItems) {
            if (userItemName.equalsIgnoreCase(item.getName())) {
                Console.println("Dropped " + item.getName() + "!");

                player.getCurrentRoom().addItem(item);
                player.removeItem(item);
                return;
            }
        }

        throw new InvalidInputException("I can't drop \'" + userItemName + "\'");
    }
}
