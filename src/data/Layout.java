package data;

import com.google.gson.annotations.SerializedName;
import error.IncompleteBuilderException;
import error.InvalidInputException;
import error.NoRoomException;
import json.Reader;

import java.io.IOException;

public class Layout {
    public static class Builder {
        private String url;
        private String filePath;

        public Builder() {}

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder path(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Layout buildLayoutFromURL() throws InvalidInputException, IncompleteBuilderException {
            if (url == null) {
                throw new IncompleteBuilderException("URL has not been set! Use .url() method!");
            }

            Layout layout = Reader.parseJsonFromURL(url);
            layout.initAfterParse();
            return layout;
        }

        public Layout buildLayoutFromFile() throws IncompleteBuilderException, InvalidInputException, IOException {
            if (filePath == null) {
                throw new IncompleteBuilderException("filePath has not been set! Use .path() method!");
            }

            Layout layout = Reader.parseJsonFromFile(filePath);
            layout.initAfterParse();
            return layout;
        }
    }

    private Layout() {}

    @SerializedName("startingRoom")
    private String startingRoomName;

    @SerializedName("endingRoom")
    private String endingRoomName;

    private Room startingRoomObj;
    private Room endingRoomObj;

    @SerializedName("rooms")
    private static Room[] rooms;

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
}