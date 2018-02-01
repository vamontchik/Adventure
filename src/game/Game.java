package game;

import console.Console;
import data.Layout;
import data.Room;
import json.Reader;

public final class Game {
    private final Console console;
    private final Layout layout;
    private Room currentRoom;
    private final Room endingRoom;

    public Game() {
        layout = Reader.parseJson("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");
        console = new Console(layout);
        currentRoom = layout.getStartingRoom();
        endingRoom = layout.getEndingRoom();
    }

    public final void start() {
        console.printUponEntrance(currentRoom);
        console.printRoomContents(currentRoom);
        console.printDirections(currentRoom);

        while (true) {
            console.println("Your move: ");
            console.readInput();
            console.processInput();
        }
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
