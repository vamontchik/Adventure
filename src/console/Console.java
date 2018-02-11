package console;

import command.*;
import data.*;
import error.InvalidInputException;
import error.MonsterNotFoundException;

import java.io.InputStream;
import java.util.Scanner;

/**
 * 'Static' class representing the Console and I/O operations within the game.
 * Also, this class delegates the processing of user input.
 * <p></p>
 * Note: this class is a 'static' class because there should only be one instance of this class at any time.
 */
public class Console {
    /**
     * Scanner object used to accept user input from the console.
     */
    private static Scanner scan;

    /**
     * String of the full input that the user puts in. Split before use, though if there is an error,
     * an error message is returned with this String.
     */
    private static String fullInput;

    /**
     * String representing the 'command' that the user inputs. Parsed from {@code fullInput}
     */
    private static String command;

    /**
     * String array housing all the arguments that the user inputs. Parsed from {@code fullInput}
     */
    private static String[] args;

    /**
     * Boolean value that represents if the entrance message has already been printed.
     */
    private static boolean printedStart;

    static {
        //initializes the Scanner to read from System.in, which is the standard input from the console
        scan = new Scanner(System.in);

        //initial value for the welcome message is false, since it has not been printed yet
        printedStart = false;
    }

    /**
     * Used by testing framework.
     *
     * @param in the InputStream to read from.
     */
    private static void setScanner(InputStream in) {
        scan = new Scanner(in);
    }

    /**
     * Prints a string to the standard output with a newline character.
     *
     * @param s the string to print out with a newline character
     */
    public static void println(String s) {
        System.out.println(s);
    }

    /**
     * Prints a string to the standard output without a newline character.
     *
     * @param s the string to print out without a newline character
     */
    public static void print(String s) {
        System.out.print(s);
    }

    /**
     * Prints a string to the standard output with a newline character, and then prints out an
     * additional amount of empty lines.
     *
     * @param s the string to print out with a newline character
     * @param amount the amount of extra lines to print
     */
    public static void printlnExtra(String s, int amount) {
        println(s);
        for (int i = 0; i < amount; i++) {
            println("");
        }
    }

    /**
     * Private helper method that prints out the contents of an array with commas.
     *
     * @param objects the Object array to print out
     */
    private static void printArrayWithCommas(Object[] objects) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                continue;
            }

            if (i != objects.length - 1) {
                print(objects[i].toString() + ", ");
            } else {
                print(objects[i].toString());
            }
        }
    }

    /**
     * Prints out the contents of the Player (ie. inventory) to the standard output.
     * If empty, will print out an empty array-like symbol.
     *
     * @param player the player whose inventory will be printed out
     */
    public static void printPlayerContents(Player player) {
        print("Your items: [");
        printArrayWithCommas(player.getItems());
        println("]");
    }

    /**
     * Prints out the contents of the Room (ie. Items in the Room) to the standard output.
     * If empty, will print out an empty array-like symbol.
     *
     * @param room the room whose contents will be printed out
     */
    public static void printRoomContents(Room room) {
        print("Room items: [");
        printArrayWithCommas(room.getItems());
        println("]");
    }

    /**
     * Prints out the description, and if necessary, welcome message depending on which Room is passed in.
     *
     * @param layout layout reference used to determine the starting and ending Rooms
     * @param room room whose description will be printed
     */
    public static void printUponEntrance(Layout layout, Room room) {
        println("Room name: "  + room.getName());
        println("Room description: " + room.getDescription());

        if (layout.getStartingRoom().equals(room) && !printedStart) {
            println("Your journey begins here.");
            printedStart = true;
        }

        if (layout.getEndingRoom().equals(room)) {
            println("You have reached your final destination!");
        }
    }

    /**
     * Prints out all the possible directions of that a Player may go within the specified Room.
     *
     * @param room Room whose directions will be printed
     */
    public static void printDirections(Room room) {
        print("From here, you can go: ");
        printArrayWithCommas(room.getDirections());
        println("");
    }

    /**
     * Prints out all the monsters in the specified Room.
     *
     * @param room the Room whose monsters will be printed
     */
    public static void printMonstersInRoom(Room room) {
        print("Monsters in room: [");
        printArrayWithCommas(room.getMonsterNames());
        println("]");
    }

    /**
     * Clears out the internal 'buffer' that {@link Console} uses to store input from the Player.
     */
    public static void clear() {
        command = null;
        args = null;
    }

    /**
     * Reads the input from the Player, from the standard input, into the corresponding storage values in {@link Console}
     * Throws an exception if the Player passes in bad input.
     *
     * @throws InvalidInputException if the Player passed in bad input.
     */
    public static void readInput() throws InvalidInputException {
        //read the user's input
        fullInput = scan.nextLine();

        //splits the user's input by ' ' characters
        String[] split = fullInput.trim().split("\\s+");

        //valid input check
        if (split.length == 0) {
            throw new InvalidInputException("Entered in an empty string or only whitespace!");
        }

        //stores the user's input into the corresponding storage values
        command = split[0];
        args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length - 1);
    }

    /**
     * Handles the processing of the user's input. Delegates the process to other classes if necessary.
     *
     * @param player the Player object of the current Player
     * @param layout the Layout reference necessary for some internal checks
     * @throws InvalidInputException if the Player has passed in bad input
     */
    public static Command processInput(Player player, Layout layout) throws InvalidInputException, MonsterNotFoundException {
        //QUIT/EXIT COMMAND
        if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit")) {
            return new ExitCommand();
        }

        //DUELING COMMANDS
        if (player.isDueling()) {
            //DISENGAGE COMMAND: Leaves the duel with the monster.
            if (command.equalsIgnoreCase("disengage")) {
                return new DisengageCommand(player);
            }

        //NORMAL COMMANDS
        } else {
            //GO COMMAND
            if (command.equalsIgnoreCase("go")) {
                //Check to ensure that arguments are not empty, to prevent a NullPointerException
                ensureArgsIsNonEmpty();

                return new GoCommand(player, args[0], layout, concatArgsIntoString());
            }

            //TAKE COMMAND
            if (command.equalsIgnoreCase("take")) {
                //Check to ensure that arguments are not empty, to prevent a NullPointerException
                ensureArgsIsNonEmpty();

                //Concatenate all the arg values into one String, in case the name of the item is more than one word
                String userItemName = concatArgsIntoString();

                return new TakeCommand(player, userItemName);
            }

            //DROP COMMAND
            if (command.equalsIgnoreCase("drop")) {
                //Check to ensure that arguments are not empty, to prevent a NullPointerException
                ensureArgsIsNonEmpty();

                //Concatenate all the arg values into one String, in case the name of the item is more than one word
                String userItemName = concatArgsIntoString();

                return new DropCommand(player, userItemName);
            }

            //LIST ITEMS COMMAND: Prints out all of the items in the Player's inventory
            if (command.equalsIgnoreCase("list")) {
                return new ListCommand(layout);
            }

            //DUEL COMMAND: Switches to the dueling state with the selected monster.
            if (command.equalsIgnoreCase("duel")) {
                ensureArgsIsNonEmpty();

                String nameOfMonster = concatArgsIntoString();
                Monster monsterToFight = Layout.findMonsterByName(nameOfMonster);

                return new DuelCommand(player, monsterToFight);
            }
        }

        //If this point is reached, the Player has input an invalid command.
        throw new InvalidInputException("I don't understand \'" + fullInput + "\'");
    }

    /**
     * Private helper methods that concatenates all of the stored arguments into a single String.
     *
     * @return the stored arguments as a single String
     */
    private static String concatArgsIntoString() {
        String userItem;

        if (args.length == 1) {
            userItem = args[0].trim();
        } else {
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg);
                builder.append(" ");
            }
            //trim() to remove the last empty space added....
            userItem = builder.toString().trim();
        }

        return userItem;
    }

    /**
     * Private helper method that ensures the stored arguments are not empty. Throws an exception if they are.
     *
     * @throws InvalidInputException if the stored arguments are empty
     */
    private static void ensureArgsIsNonEmpty() throws InvalidInputException {
        if (args.length == 0) {
            throw new InvalidInputException("Please specify your arguments!");
        }
    }
}
