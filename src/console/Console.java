package console;

import data.*;
import error.InvalidInputException;
import error.NoRoomException;

import java.util.Scanner;

public class Console {
    private static Scanner scan;
    private static String fullInput;
    private static String command;
    private static String[] args;
    private static boolean printedStart;

    static {
        scan = new Scanner(System.in);
        printedStart = false;
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static void printlnExtra(String s, int amount) {
        System.out.println(s);
        for (int i = 0; i < amount; i++) {
            System.out.println();
        }
    }

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

    public static void printPlayerContents(Player player) {
        print("Your Items: [");
        printArrayWithCommas(player.getItems());
        println("]");
    }

    public static void printRoomContents(Room room) {
        print("Room Items: [");
        printArrayWithCommas(room.getItems());
        println("]");
    }

    public static void printUponEntrance(Layout layout, Room room) {
        println("");
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

    public static void printDirections(Room room) {
        print("From here, you can go: ");
        printArrayWithCommas(room.getDirections());
        println("");
    }

    public static void printMonstersInRoom(Room room) {
        print("Monsters in room: [");
        printArrayWithCommas(room.getMonsterNames());
        println("]");
    }

    public static void clear() {
        command = null;
        args = null;
    }

    public static void readInput() throws InvalidInputException {
        fullInput = scan.nextLine();
        String[] split = fullInput.trim().split("\\s+");

        if (split.length == 0) {
            throw new InvalidInputException("Entered in an empty string or only whitespace!");
        }

        command = split[0];
        args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length - 1);
    }

    //TODO: Decouple functionality of game events this method... maybe make another class to do so??? Like 'Command' or something...
    public static void processInput(Player player, Layout layout) throws InvalidInputException {
        if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        if (command.equalsIgnoreCase("go")) {
            ensureArgsIsNonEmpty(args);

            Direction[] validDirections = player.getCurrentRoom().getDirections();
            String userDir = args[0];

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

                    player.setCurrentRoom(newRoom);

                    //end game check?
                    //noinspection ConstantConditions
                    if (newRoom.equals(layout.getEndingRoom())) {
                        Console.println("Congratulations you've won!");
                        System.exit(0);
                    }

                    return;
                }
            }
            throw new InvalidInputException("I can't go " + userDir);
        }

        if (command.equalsIgnoreCase("take")) {
            ensureArgsIsNonEmpty(args);

            Item[] roomItems = player.getCurrentRoom().getItems();
            String userItemName = concatArgsIntoString(args);

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

        if (command.equalsIgnoreCase("drop")) {
            ensureArgsIsNonEmpty(args);

            Item[] roomItems = player.getItems();
            String userItemName = concatArgsIntoString(args);

            for (Item item : roomItems) {
                if (userItemName.equalsIgnoreCase(item.getName())) {
                    Console.println("Dropped " + item.getName() + "!");

                    player.getCurrentRoom().addItem(item);
                    player.removeItem(item);
                    return;
                }
            }

            throw new InvalidInputException("I can't drop " + userItemName);
        }

        if (command.equalsIgnoreCase("list")) {
            Console.printPlayerContents(layout.getPlayer());
            return;
        }

        throw new InvalidInputException("I don't understand \'" + fullInput + "\'");
    }

    private static String concatArgsIntoString(String[] args) {
        String userItem;

        if (args.length == 1) {
            userItem = args[0];
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

    private static void ensureArgsIsNonEmpty(String[] args) throws InvalidInputException {
        if (args.length == 0) {
            throw new InvalidInputException("Please specify your arguments!");
        }
    }
}
