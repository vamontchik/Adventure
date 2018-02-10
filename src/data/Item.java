package data;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Class representing the items within the game.
 */
public class Item {
    /**
     * String representation of the name of the Item.
     */
    @SerializedName("name")
    private String name;

    /**
     * Double value that represents the power of the Item.
     */
    @SerializedName("damage")
    private double damage;

    /**
     * Used for testing, and not used during live deployment of the game.
     */
    private Item() {
        damage = 0.0;
        name = "";
    }

    /**
     * Obtains the name of the Item.
     *
     * @return the name of the Item
     */
    public String getName() {
        return name;
    }

    /**
     * Obtains the double value that represents the power of the Item.
     *
     * @return the double value that represents the power of the Item
     */
    public double getDamage() {
        return damage;
    }

    /**
     * String representation of the Item object. Used when printing with the {@link console.Console} class
     *
     * @return the String representation of the Item object
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Overridden equality method. Tests each field of the Room object for equality.
     *
     * @param o the passed-in Object to compare against
     * @return true if it is equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.damage, damage) == 0 &&
                Objects.equals(name, item.name);
    }

    /**
     * Overridden hashCode generator method. Utilizes each field of the Room object.
     *
     * @return hashcode representation of this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, damage);
    }
}
