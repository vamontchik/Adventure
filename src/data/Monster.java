package data;

import com.google.gson.annotations.SerializedName;

public class Monster {
    @SerializedName("name")
    private String name;

    @SerializedName("attack")
    private double attack;

    @SerializedName("defense")
    private double defense;

    @SerializedName("health")
    private double health;

    public String getName() {
        return name;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefense() {
        return defense;
    }

    public double getHealth() {
        return health;
    }
}
