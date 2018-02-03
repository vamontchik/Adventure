package data;

import com.google.gson.annotations.SerializedName;
import error.IncompleteBuilderException;
import error.InvalidInputException;
import error.NoRoomException;
import json.Reader;

public final class Layout {
    public static class Builder {
        private String url;

        public Builder() {}

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Layout buildLayout() throws InvalidInputException, IncompleteBuilderException {
            if (url == null) {
                throw new IncompleteBuilderException("URL has not been set! Use .url() method!");
            }

            Layout layout = Reader.parseJson(url);
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
    private Room currentRoomObj;

    @SerializedName("rooms")
    private Room[] rooms;

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

        currentRoomObj = startingRoomObj;
    }

    public final Room getStartingRoom() {
        return startingRoomObj;
    }

    public final Room getEndingRoom() {
        return endingRoomObj;
    }

    public final Room getCurrentRoom() {
        return currentRoomObj;
    }

    private Room findRoomByName(String roomName) throws NoRoomException {
        for (Room room : rooms) {
            if (roomName.equals(room.getName())) {
                return room;
            }
        }

        throw new NoRoomException("Room cannot be found for search string: " + roomName);
    }
}