package console;

import data.Direction;
import data.Layout;
import data.Player;
import data.Room;
import error.InvalidInputException;
import error.NoRoomException;

import java.util.List;
import java.util.Scanner;

public class Console {
    private static final Scanner scan;
    private static String command;
    private static String[] args;

    static {
        scan = new Scanner(System.in);
    }

    public static final void println(String s) {
        System.out.println(s);
    }

    public static final void print(String s) {
        System.out.print(s);
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

    private static void printListWithCommas(List<?> items) {
        printArrayWithCommas(items.toArray());
    }

    public static void printPlayerContents(Player player) {
        print("Your Items: [");
        printListWithCommas(player.getItems());
        println("]");
    }

    public static void printRoomContents(Room room) {
        print("Room Items: [");
        printArrayWithCommas(room.getItems());
        println("]");
    }

    public static final void printUponEntrance(Layout layout, Room room) {
        println(room.getDescription());

        if (layout.getStartingRoom().equals(room)) {
            println("Your journey begins here.");
        }

        if (layout.getEndingRoom().equals(room)) {
            println("You have reached your final destination!");
        }
    }

    public static final void printDirections(Room room) {
        print("From here, you can go: ");
        printArrayWithCommas(room.getDirections());
        println("");
    }

    public static final void readInput() throws InvalidInputException {
        String input = scan.nextLine();
        String[] split = input.trim().split("\\s+");

        if (split.length == 0) {
            throw new InvalidInputException("Entered in an empty string or only whitespace!");
        }

        command = split[0];
        args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length - 1);
    }

    public static final ProcessConstant processInput(Player player, Layout layout) throws InvalidInputException {
        if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit")) {
            return ProcessConstant.EXIT;
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
                    if (newRoom.equals(layout.getEndingRoom())) {
                        return ProcessConstant.END;
                    }

                    return ProcessConstant.MOVE;
                }
            }
            throw new InvalidInputException("I can't go " + userDir);
        }

        if (command.equalsIgnoreCase("take")) {
            ensureArgsIsNonEmpty(args);

            String[] roomItems = player.getCurrentRoom().getItems();
            String userItem = args[0];

            for (String item : roomItems) {
                if (userItem.equalsIgnoreCase(item)) {
                    Console.println("Picked up " + item);

                    player.getCurrentRoom().removeItem(item);
                    player.addItem(item);

                    return ProcessConstant.TAKE;
                }
            }
        }

        if (command.equalsIgnoreCase("drop")) {
            ensureArgsIsNonEmpty(args);

            String[] roomItems = player.getCurrentRoom().getItems();
            String userItem = args[0];

            for (String item : roomItems) {
                if (userItem.equalsIgnoreCase(item)) {
                    println("Dropped " + item);

                    player.getCurrentRoom().addItem(item);
                    player.removeItem(item);

                    return ProcessConstant.TAKE;
                }
            }
        }

        throw new InvalidInputException("Cannot determine what to do!");
    }

    private static void ensureArgsIsNonEmpty(String[] args) throws InvalidInputException {
        if (args.length == 0) {
            throw new InvalidInputException("Please specify your arguments!");
        }
    }

    public static void clear() {
        command = null;
        args = null;
    }
}
