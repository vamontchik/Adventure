package data;

import com.google.gson.annotations.SerializedName;

/**
 * Class representing the monsters found within the game.
 */
public class Monster {
    /**
     * Name of the monster.
     */
    @SerializedName("name")
    private String name;

    /**
     * Attack power of the monster.
     */
    @SerializedName("attack")
    private double attack;

    /**
     * Defense power of the monster.
     */
    @SerializedName("defense")
    private double defense;

    /**
     * Current health of the monster.
     */
    @SerializedName("health")
    private double currentHealth;

    /**
     * Obtains the name of the monster.
     *
     * @return the name of the monster
     */
    public String getName() {
        return name;
    }

    /**
     * Obtains the attack power of the monster.
     *
     * @return the attack power of the monster
     */
    public double getAttack() {
        return attack;
    }

    /**
     * Obtains the defense power of the monster.
     *
     * @return the defense power of the monster
     */
    public double getDefense() {
        return defense;
    }

    /**
     * Obtains the current health of the monster.
     *
     * @return the current health of the monster
     */
    public double getHealth() {
        return currentHealth;
    }
}
