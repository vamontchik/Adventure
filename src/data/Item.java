package data;

import com.google.gson.annotations.SerializedName;

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
}
