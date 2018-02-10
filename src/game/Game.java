package game;

import console.Console;
import data.Layout;
import error.IncompleteBuilderException;
import error.InvalidInputException;
import error.InvalidMapException;
import error.NoRoomException;
import validator.MapValidator;

import java.io.IOException;

/**
 * Game class, which encapsulates the concept of the 'game loop' in most games.
 */
public class Game {
    /**
     * The Layout object representing the state of the game.
     */
    private Layout layout;

    /**
     * Builds the Game object by:
     * <ul>
     *      <li> Attempts to parse the specified argument using GSON. </li>
     *      <li> Validates the layout of the game object (startRoom -> endRoom) </li>
     *      <li> Finishes any extra initialization processes for the game. </li>
     * </uL>
     * @param path the specified path to parse JSON from
     */
    public Game(String path) {
        try {
            //create the game world into the Layout object
            if (path.contains("https://")) {
                layout = new Layout.Builder().url(path).buildFromURL();
            } else {
                layout = new Layout.Builder().path(path).buildFromFile();
            }

            //validate that this game world is a valid one
            MapValidator.validate(layout);

            //finish initialization of the Player
            layout.getPlayer().setCurrentRoom(layout.getStartingRoom());

        } catch (InvalidInputException | IncompleteBuilderException | InvalidMapException | IOException | NoRoomException e) {

            //prints the error message with the cause
            Console.println("Error: " + e.getMessage());
            Console.println("Unable to initialize game!");

            //terminates the game, since initialization was incomplete
            System.exit(-1);
        }
    }

    /**
     * Runs the game loop.
     */
    public void gameLoop() {
        //noinspection InfiniteLoopStatement
        while (true) {
            //prints the room name and description
            Console.printUponEntrance(layout, layout.getPlayer().getCurrentRoom());

            //prints the items the player has
            Console.printPlayerContents(layout.getPlayer());

            //prints the items in the room
            Console.printRoomContents(layout.getPlayer().getCurrentRoom());

            //prints the monsters in the room
            Console.printMonstersInRoom(layout.getPlayer().getCurrentRoom());

            //prints the directions the player can go
            Console.printDirections(layout.getPlayer().getCurrentRoom());

            //prompt the user, and prints an extra line at the end
            Console.print("Your move: ");

            try {
                //reads the next line of input from the user
                Console.readInput();

                //executes the corresponding function, or fails by throwing an exception
                Console.processInput(layout.getPlayer(), layout);

            } catch (InvalidInputException e) {
                //displays the cause of the issue
                Console.println("Error: " + e.getMessage());

                //clears the internal "buffer" inside of the Console class
                Console.clear();
            }
        }
    }
}
