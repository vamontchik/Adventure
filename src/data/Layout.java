package data;

import com.google.gson.annotations.SerializedName;
import error.IncompleteBuilderException;
import error.InvalidInputException;
import error.MonsterNotFoundException;
import error.NoRoomException;
import json.Reader;

import java.io.IOException;

public class Layout {
    public static class Builder {
        private String url;
        private String filePath;

        public Builder() {}

        public Builder url(String url) throws IncompleteBuilderException {
            if (filePath != null) {
                throw new IncompleteBuilderException("Only call url() if there's parsing from a URL!");
            }

            this.url = url;
            return this;
        }

        public Builder path(String filePath) throws IncompleteBuilderException {
            if (url != null) {
                throw new IncompleteBuilderException("Only call path() if there's parsing from a file!");
            }

            this.filePath = filePath;
            return this;
        }

        public Layout buildFromURL() throws InvalidInputException, IncompleteBuilderException {
            if (url == null) {
                throw new IncompleteBuilderException("URL has not been set! Use .url() method!");
            }
            if (filePath != null) {
                throw new IncompleteBuilderException("Only call buildFromURL() if building from a URL!");
            }

            Layout layout = Reader.parseJsonFromURL(url);
            layout.initAfterParse();
            return layout;
        }

        public Layout buildFromFile() throws IncompleteBuilderException, InvalidInputException, IOException {
            if (filePath == null) {
                throw new IncompleteBuilderException("filePath has not been set! Use .path() method!");
            }
            if (url != null) {
                throw new IncompleteBuilderException("Only call buildFromFile() if building from a file!");
            }

            Layout layout = Reader.parseJsonFromFile(filePath);
            layout.initAfterParse();
            return layout;
        }
    }

    private void initAfterParse() throws InvalidInputException {
        try {
            startingRoomObj = findRoomByName(startingRoomName);
        } catch (NoRoomException e) {
            throw new InvalidInputException("There is no starting room!");
        }

        try {
            endingRoomObj = findRoomByName(endingRoomName);
        } catch (NoRoomException e) {
            throw new InvalidInputException("There is no ending room!");
        }
    }

    private Layout() {}

    @SerializedName("startingRoom")
    private String startingRoomName;

    @SerializedName("endingRoom")
    private String endingRoomName;

    @SerializedName("rooms")
    private static Room[] rooms;

    @SerializedName("player")
    private Player player;

    @SerializedName("monsters")
    private static Monster[] monsters;

    private Room startingRoomObj;

    private Room endingRoomObj;

    public Room[] getRooms() {
        return rooms;
    }

    public String getStartingRoomName() {
        return startingRoomName;
    }

    public String getEndingRoomName() {
        return endingRoomName;
    }

    public Player getPlayer() {
        return player;
    }

    public Monster[] getMonsters() {
        return monsters;
    }

    public Room getStartingRoom() {
        return startingRoomObj;
    }

    public Room getEndingRoom() {
        return endingRoomObj;
    }

    public static Room findRoomByName(String roomName) throws NoRoomException {
        for (Room room : rooms) {
            if (roomName.equals(room.getName())) {
                return room;
            }
        }

        throw new NoRoomException("Room cannot be found for search string: " + roomName);
    }

    public static Monster findMonsterByName(String monsterName) throws MonsterNotFoundException {
        for (Monster monster : monsters) {
            if (monsterName.equals(monster.getName())) {
                return monster;
            }
        }

        throw new MonsterNotFoundException("Monster cannot be found for search string: " + monsterName);
    }
}