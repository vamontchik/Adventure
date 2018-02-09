package game;

import console.Console;
import data.Layout;
import error.IncompleteBuilderException;
import error.InvalidInputException;
import error.InvalidMapException;
import validator.MapValidator;

import java.io.IOException;

public class Game {
    private Layout layout;

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
            Console.println("Error: " + e.getMessage());
            Console.println("Unable to initialize game!");
            System.exit(-1);
        }
    }

    public void gameLoop() {
        while (true) {
            Console.printUponEntrance(layout, layout.getPlayer().getCurrentRoom());
            Console.printPlayerContents(layout.getPlayer());
            Console.printRoomContents(layout.getPlayer().getCurrentRoom());
            Console.printMonstersInRoom(layout.getPlayer().getCurrentRoom());
            Console.printDirections(layout.getPlayer().getCurrentRoom());
            Console.println("Your move: ");

            try {
                Console.readInput();
                Console.processInput(layout.getPlayer(), layout);
            } catch (InvalidInputException e) {
                Console.println(e.getMessage());
                Console.clear();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            Console.println("Usage: java Game [pathToJson]");
            Console.println("The argument should specify the path to the JSON by link or absolute path.");
            Console.println("Defaulting to local json file...");
            new Game("data\\extended_json.json").gameLoop();
        }

        new Game(args[0]).gameLoop();
    }
}
