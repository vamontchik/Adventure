import console.Console;
import game.Game;

/**
 * Starting point.
 */
class Application {
    /**
     * Starting point.
     * @param args if used, contains the specified path to the
     */
    public static void main(String[] args) {
        // Makes sure the correct amount of arguments are passed in.
        // Defaults to the local JSON file if it is incorrect.
        if (args.length != 1) {
            Console.println("Usage: java Application [pathToJson]");
            Console.println("The argument should specify the path to the JSON by link or absolute path.");
            Console.println("Defaulting to local json file...");
            new Game("data\\extended_json.json").gameLoop();
        }

        // Initialize and start the game
        new Game(args[0]).gameLoop();
    }
}
