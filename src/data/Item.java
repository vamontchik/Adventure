package data;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("name")
    private String name;

    @SerializedName("damage")
    private double damage;

    public String getName() {
        return name;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return name;
    }
}
