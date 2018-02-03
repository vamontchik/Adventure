package data;

import com.google.gson.annotations.SerializedName;

public class Direction {
    @SerializedName("directionName")
    private String direction;

    @SerializedName("room")
    private String room;

    public String getDirection() {
        return direction;
    }

    public String getRoomName() {
        return room;
    }

    @Override
    public String toString() {
        return direction;
    }
}
