package command;

import console.Console;
import data.Item;
import data.Player;
import error.InvalidInputException;

/**
 * Command representing the Player taking an item from the Room.
 */
public class TakeCommand implements Command {
    private Player player;
    private String userItemName;

    public TakeCommand(Player player, String userItemName) {
        this.player = player;
        this.userItemName = userItemName;
    }

    @Override
    public void execute() throws InvalidInputException {
        //check if there are monsters in the room
        if (player.getCurrentRoom().containsMonsters()) {
            throw new InvalidInputException("There are still monsters here! I can't take that.");
        }

        //Obtain all the items in the room
        Item[] roomItems = player.getCurrentRoom().getItems();

        //Check to see if this is a valid item to pick up. If so, add the item to the player's inventory and
        //remove it from the room's inventory. Else, throw an InvalidInputException specifying that it cannot do so.
        for (Item item : roomItems) {
            if (userItemName.equalsIgnoreCase(item.getName())) {
                Console.println("Took " + item.getName() + "!");

                player.getCurrentRoom().removeItem(item);
                player.addItem(item);
                return;
            }
        }

        throw new InvalidInputException("I can't take " + userItemName);
    }
}
