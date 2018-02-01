package data;

import com.google.gson.annotations.SerializedName;

public final class Direction {
    @SerializedName("directionName")
    private String direction;

    @SerializedName("room")
    private String room;

    @Override
    public String toString() {
        return direction + ", pointing towards " + room;
    }

    public String getDirection() {
        return direction;
    }

    public String getRoomName() {
        return room;
    }
}
