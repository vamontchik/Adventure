package game;

import console.Console;
import data.Layout;
import error.IncompleteBuilderException;
import error.InvalidInputException;
import error.InvalidMapException;
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
     *      (1) Attempts to parse the specified argument using GSON.
     *      (2) Validates the layout of the game object (startRoom -> endRoom)
     *      (3) Finishes any extra initialization processes for the game.
     *
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

        } catch (InvalidInputException | IncompleteBuilderException | InvalidMapException | IOException e) {

            //prints the error message with the cause
            Console.printlnExtra("Error: " + e.getMessage(), 1);
            Console.printlnExtra("Unable to initialize game!", 1);

            //terminates the game, since initialization was incomplete
            System.exit(-1);
        }
    }

    public void gameLoop() {
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
            Console.printlnExtra("Your move: ", 1);

            try {
                //reads the next line of input from the user
                Console.readInput();

                //executes the corresponding function, or fails by throwing an exception
                Console.processInput(layout.getPlayer(), layout);

            } catch (InvalidInputException e) {
                //displays the cause of the issue
                Console.printlnExtra("Error: " + e.getMessage(), 1);

                //clears the internal "buffer" inside of the Console class
                Console.clear();
            }
        }
    }

    public static void main(String[] args) {
        // Makes sure the correct amount of arguments are passed in.
        // Defaults to the local JSON file if it is incorrect.
        if (args.length != 1) {
            Console.println("Usage: java Game [pathToJson]");
            Console.println("The argument should specify the path to the JSON by link or absolute path.");
            Console.printlnExtra("Defaulting to local json file...", 2);
            new Game("data\\extended_json.json").gameLoop();
        }

        // Initialize and start the game
        new Game(args[0]).gameLoop();
    }
}
