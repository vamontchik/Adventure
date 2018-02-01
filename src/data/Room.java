package data;

import com.google.gson.annotations.SerializedName;

public final class Room {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("directions")
    private Direction[] directions;

    @SerializedName("items")
    private String[] items;
}
