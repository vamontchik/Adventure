package game;

import console.Console;
import data.Layout;
import error.IncompleteBuilderException;
import error.InvalidInputException;

public final class Game {
    private static Layout layout;

    public Game(String url) {
        try {
            layout = new Layout.Builder().url(url).buildLayout();
        } catch (InvalidInputException | IllegalStateException | IncompleteBuilderException e) {
            Console.println("Error: " + e.getMessage());
            Console.println("Unable to initialize game!");
            System.exit(-1);
        }
    }

    public final void loop() {
        Console.printUponEntrance(layout, layout.getCurrentRoom());
        Console.printRoomContents(layout.getCurrentRoom());
        Console.printDirections(layout.getCurrentRoom());

        while (true) {
            Console.println("Your move: ");

            try {
                Console.readInput();
                Console.processInput();
            } catch (InvalidInputException e) {
                Console.println(e.getMessage());
                Console.clear();
            }
        }
    }

    public static void main(String[] args) {
        new Game(args[0]).loop();
    }
}
