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
    private final String name;

    /**
     * Attack power of the monster.
     */
    @SerializedName("attack")
    private final double attack;

    /**
     * Defense power of the monster.
     */
    @SerializedName("defense")
    private final double defense;

    /**
     * Current health of the monster.
     */
    @SerializedName("health")
    private double currentHealth;

    private double initialHealth;

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
     * Overridden equality method. Tests each field of the Monster object for equality.
     *
     * @param o the passed-in Object to compare against
     * @return true if it is equal, false if not
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
     * Overridden hashCode generator method. Utilizes each field of the Monster object.
     *
     * @return hashcode representation of this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, attack, defense, currentHealth);
    }

    /**
     * Sets the health of the Monster object.
     *
     * @param newHealth the health to set it to
     */
    public void setHealth(double newHealth) {
        this.currentHealth = newHealth;
    }

    /**
     * Obtains the initial value of the health of this Monster.
     *
     * @return initial health value
     */
    public double getInitialHealth() {
        return initialHealth;
    }

    /**
     * Sets the initialHealth value of this Monster. Instantiated upon initialization of this game.
     *
     * @param initialHealth the initial health of the Monster
     * @return the initial health of this monster
     */
    public void setInitialHealth(double initialHealth) {
        this.initialHealth = initialHealth;
    }
}
