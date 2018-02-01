package data;

import com.google.gson.annotations.SerializedName;

public final class Layout {
    @SerializedName("startingRoom")
    private String startingRoom;

    @SerializedName("endingRoom")
    private String endingRoom;

    @SerializedName("rooms")
    private Room[] rooms;

    public final String getStartingRoomName() {
        return startingRoom;
    }

    public final Room getStartingRoom() {
        for (Room room : rooms) {
            if (startingRoom.equals(room.getName())) {
                return room;
            }
        }
        throw new IllegalStateException("No starting room found!");
    }

    public final String getEndingRoomName() {
        return endingRoom;
    }

    public final Room getEndingRoom() {
        for (Room room : rooms) {
            if (endingRoom.equals(room.getName())) {
                return room;
            }
        }
        throw new IllegalStateException("No ending room found!");
    }

    public final Room[] getRooms() {
        return rooms;
    }
}