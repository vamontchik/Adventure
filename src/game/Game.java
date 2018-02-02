package game;

import console.Console;
import data.Layout;
import error.InvalidInputException;
import json.Reader;

public final class Game {
    private final Console console;
    private final Layout layout;

    public Game() throws InvalidInputException {
        layout = Reader.parseJson("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");
        layout.initAfterParse();
        console = new Console(layout);
    }

    public final void start() {
        console.printUponEntrance(layout.getCurrentRoom());
        console.printRoomContents(layout.getCurrentRoom());
        console.printDirections(layout.getCurrentRoom());

        while (true) {
            console.println("Your move: ");

            try {
                console.readInput();
                console.processInput();
            } catch (InvalidInputException e) {
                console.println(e.getMessage());
                console.clear();
            }
        }
    }

    public static void main(String[] args) throws InvalidInputException {
        new Game().start();
    }
}
