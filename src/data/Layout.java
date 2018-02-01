package data;

import com.google.gson.annotations.SerializedName;
import error.InvalidInputException;
import error.NoRoomException;

public final class Layout {
    @SerializedName("startingRoom")
    private String startingRoomName;

    @SerializedName("endingRoom")
    private String endingRoomName;

    private Room startingRoomObj;
    private Room endingRoomObj;
    private Room currentRoomObj;

    @SerializedName("rooms")
    private Room[] rooms;

    public void initAfterParse() throws InvalidInputException {
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

    public Room getCurrentRoom() {
        return currentRoomObj;
    }

    public Room findRoomByName(String roomName) throws NoRoomException {
        return roomNameToRoom(roomName);
    }

    private Room roomNameToRoom(String roomName) throws NoRoomException {
        for (Room room : rooms) {
            System.out.println(room.getName());
            if (roomName.equals(room.getName())) {
                return room;
            }
        }

        throw new NoRoomException("Room cannot be found for search string: " + roomName);
    }
}