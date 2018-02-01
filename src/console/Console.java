package console;

import data.Direction;
import data.Layout;
import data.Room;

import java.util.Scanner;

public final class Console {
    private final Scanner scan;
    private final Layout layout;

    private String command;
    private String[] args;

    public Console(Layout layout) {
        scan = new Scanner(System.in);
        this.layout = layout;
    }

    public final void println(String s) {
        System.out.println(s);
    }

    public final void print(String s) {
        System.out.print(s);
    }

    public final void printRoomContents(Room room) {
        print("Items: [");
        for (String item : room.getItems()) {
            print(item);
        }
        print("]");
        println("");
    }

    public final void printUponEntrance(Room room) {
        println(room.getDescription());

        if (layout.getStartingRoom().equals(room)) {
            println("Your journey begins here.");
        }

        if (layout.getEndingRoom().equals(room)) {
            println("You have reached your final destination");
        }
    }

    public final void printDirections(Room room) {
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

    public void readInput() {
        String input = scan.nextLine();
        String[] split = input.trim().split("\\s+");
        command = split[0];
        args = new String[split.length - 1];
        for (int i = 1; i < split.length; i++) {
            args[i - 1] = split[i];
        }
    }

    public void processInput() {
        if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        //... do more stuff
    }
}
