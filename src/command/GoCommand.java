package command;

import console.Console;
import data.Direction;
import data.Layout;
import data.Player;
import data.Room;
import error.InvalidInputException;
import error.NoRoomException;

/**
 * Command representing the movement of a Player into another Room.
 */
public class GoCommand implements Command {
    private Player player;
    private String whereTo;
    private Layout layout;
    private String fullInputForError;

    public GoCommand(Player player, String whereTo, Layout layout, String fullInputForError) {
        this.player = player;
        this.whereTo = whereTo;
        this.layout = layout;
        this.fullInputForError = fullInputForError;
    }

    @Override
    public void execute() throws InvalidInputException {
        //Checks to see if there are monsters still in the Room
        if (player.getCurrentRoom().containsMonsters()) {
            throw new InvalidInputException("There are still monsters here! I can’t move.");
        }

        //Obtain all the possible directions to move
        Direction[] validDirections = player.getCurrentRoom().getDirections();

        //Obtain the direction the user wishes to go, and check to see if it's a valid direction
        //If so, move in that direction by setting the Player to be in that room.
        //Else, throw an InvalidInputException specifying that it cannot do so
        String userDir = whereTo;
        for (Direction dir : validDirections) {
            if (userDir.equalsIgnoreCase(dir.getDirection())) {
                Console.println("Moved to " + dir.getRoomName());

                Room newRoom = null;
                try {
                    newRoom = Layout.findRoomByName(dir.getRoomName());
                } catch (NoRoomException e) {
                    //never thrown because if the above if statement occurs, there's a guarantee
                    //that there'll be a room that works
                }

                //set the new room
                player.setCurrentRoom(newRoom);

                //set the boolean that check if a player has move rooms. Used when printing output.
                player.setEntered(false);

                //end game check
                //noinspection ConstantConditions ---> see catch statement above
                if (newRoom.equals(layout.getEndingRoom())) {
                    Console.println("Congratulations you've won!");
                    System.exit(0);
                }

                return;
            }
        }

        throw new InvalidInputException("I can't go \'" + fullInputForError + "\'");
    }
}
