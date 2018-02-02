package data;

import com.google.gson.annotations.SerializedName;

public final class Direction {
    @SerializedName("directionName")
    private String direction;

    @SerializedName("room")
    private String room;

    @Override
    public final String toString() {
        return direction + ", pointing towards " + room;
    }

    public final String getDirection() {
        return direction;
    }

    public final String getRoomName() {
        return room;
    }
}
