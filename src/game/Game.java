package game;

import console.Console;
import console.ProcessConstant;
import data.Layout;
import data.Player;
import error.IncompleteBuilderException;
import error.InvalidInputException;

public class Game {
    private Layout layout;
    private Player player;

    public Game(String url) {
        try {
            layout = new Layout.Builder().url(url).buildLayout();
            player = new Player(layout.getStartingRoom());
        } catch (InvalidInputException | IncompleteBuilderException e) {
            Console.println("Error: " + e.getMessage());
            Console.println("Unable to initialize game!");
            System.exit(-1);
        }
    }

    public void loop() {
        while (true) {
            printStatus();
            Console.println("Your move: ");

            try {
                Console.readInput();
                ProcessConstant result = Console.processInput(player, layout);
                doResult(result);
            } catch (InvalidInputException e) {
                Console.println(e.getMessage());
                Console.clear();
            }
        }
    }

    private void printStatus() {
        Console.printUponEntrance(layout, player.getCurrentRoom());
        Console.printPlayerContents(player);
        Console.printRoomContents(player.getCurrentRoom());
        Console.printDirections(player.getCurrentRoom());
    }

    private void doResult(ProcessConstant result) {
        switch (result) {
            case EXIT:
                System.exit(0);
                break;
            case MOVE:
            case TAKE:
            case DROP:
                // don't do anything...
                break;
            case END:
                Console.println("Congratulations you've won!");
                System.exit(0);
                break;
            case LIST:
                Console.printPlayerContents(player);
            default:
                //... should never happen ...
        }
    }

    public static void main(String[] args) {
        new Game(args[0]).loop();
    }
}
