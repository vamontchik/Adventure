package data;

import com.google.gson.annotations.SerializedName;

public final class Layout {
    @SerializedName("startingRoom")
    private String startingRoom;

    @SerializedName("endingRoom")
    private String endingRoom;

    @SerializedName("rooms")
    private Room[] rooms;

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public Room[] getRooms() {
        return rooms;
    }
}