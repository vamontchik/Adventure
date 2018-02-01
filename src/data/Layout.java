package data;

import com.google.gson.annotations.SerializedName;

public class Layout {
    @SerializedName("startingRoom")
    private String startingRoom;

    @SerializedName("endingRoom")
    private String endingRoom;

    @SerializedName("rooms")
    private Room[] rooms;
}