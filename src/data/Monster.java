package data;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

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
     * Used in testing only.
     */
    private Monster() {
        name = "";
        attack = 0.0;
        defense = 0.0;
        currentHealth = 0.0;
    }

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

    /**
     *
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monster monster = (Monster) o;
        return Double.compare(monster.attack, attack) == 0 &&
                Double.compare(monster.defense, defense) == 0 &&
                Double.compare(monster.currentHealth, currentHealth) == 0 &&
                Objects.equals(name, monster.name);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, attack, defense, currentHealth);
    }
}
