package console;

import data.Direction;
import data.Layout;
import data.Room;
import error.InvalidInputException;

import java.util.Scanner;

public final class Console {
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

    public static final void printRoomContents(Room room) {
        print("Items: [");
        for (String item : room.getItems()) {
            print(item);
        }
        print("]");
        println("");
    }

    public static final void printUponEntrance(Layout layout, Room room) {
        println(room.getDescription());

        if (layout.getStartingRoom().equals(room)) {
            println("Your journey begins here.");
        }

        if (layout.getEndingRoom().equals(room)) {
            println("You have reached your final destination");
        }
    }

    public static final void printDirections(Room room) {
        Direction[] directions = room.getDirections();

        print("From here, you can go: ");
        for (int i = 0; i < directions.length; i++) {
            if (i != directions.length - 1) {
                print(directions[i].getDirection() + ", ");
            } else {
                print(directions[i].getDirection());
            }
        }
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

    public static final void processInput() throws InvalidInputException {
        if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        throw new InvalidInputException("Cannot determine what to do!");
    }

    public static final void clear() {
        command = null;
        args = null;
    }
}
