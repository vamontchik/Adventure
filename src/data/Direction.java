package data;

import com.google.gson.annotations.SerializedName;

public final class Direction {
    @SerializedName("directionName")
    private String pointingAtDirection;

    @SerializedName("room")
    private String pointingAtRoom;
}
