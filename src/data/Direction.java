package data;

import com.google.gson.annotations.SerializedName;

public class Direction {
    @SerializedName("directionName")
    private String pointingAtDirection;

    @SerializedName("room")
    private String pointingAtRoom;
}
