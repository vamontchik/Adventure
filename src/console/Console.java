package console;

import data.Direction;
import data.Layout;
import data.Room;

import java.util.Scanner;

public final class Console {
    private final Scanner scan;
    private final Layout layout;

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

    public final void printUponEnterRoom(Room room) {
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
                print(directions[i] + ", ");
            } else {
                print(directions[i].toString());
            }
        }
    }
}
