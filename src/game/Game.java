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
        layout = Reader.parseJson("siebel.json");
        console = new Console(layout);
        currentRoom = layout.getStartingRoom();
        endingRoom = layout.getEndingRoom();
    }

    public final void start() {
        console.printUponEnterRoom(currentRoom);
        console.printRoomContents(currentRoom);
        console.printDirections(currentRoom);
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
