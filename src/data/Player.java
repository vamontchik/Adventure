package data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class representing the player of the game.
 */
public class Player {
    /**
     * Name of the player.
     */
    @SerializedName("player")
    private final String name;

    /**
     * Internal array structure representing the items that the player has.
     */
    @SerializedName("items")
    private Item[] items;

    /**
     * Attack power of the player.
     */
    @SerializedName("attack")
    private final double attack;

    /**
     * Defense power of the player.
     */
    @SerializedName("defense")
    private final double defense;

    /**
     * Total health of the player.
     */
    @SerializedName("health")
    private final double currentHealth;

    /**
     * Experience level of the player.
     */
    @SerializedName("level")
    private final int level;

    /**
     * Room object that the player is currently inhabiting.
     */
    private Room currentRoom;

    /**
     * Used only in testing.
     */
    private Player(Room testRoom) {
        name = "";
        items = new Item[0];
        attack = 0.0;
        defense = 0.0;
        currentHealth = 0.0;
        level = 0;
        currentRoom = testRoom;
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
        Player player = (Player) o;
        return Double.compare(player.attack, attack) == 0 &&
                Double.compare(player.defense, defense) == 0 &&
                Double.compare(player.currentHealth, currentHealth) == 0 &&
                level == player.level &&
                Objects.equals(name, player.name) &&
                Arrays.equals(items, player.items) &&
                Objects.equals(currentRoom, player.currentRoom);
    }

    /**
     * Overridden hashCode generator method. Utilizes each field of the Player object.
     *
     * @return hashcode representation of this object.
     */
    @Override
    public int hashCode() {

        int result = Objects.hash(name, attack, defense, currentHealth, level, currentRoom);
        result = 31 * result + Arrays.hashCode(items);
        return result;
    }

    /**
     * Obtains the name of the Player.
     *
     * @return the name of the Player
     */
    public String getName() {
        return name;
    }

    /**
     * Obtains the array of Item objects that the Player has.
     *
     * @return array of Item objects
     */
    public Item[] getItems() {
        return items;
    }

    /**
     * Obtains the attack power of the Player.
     *
     * @return the attack power of the Player
     */
    public double getAttack() {
        return attack;
    }

    /**
     * Obtains the defense power of the Player.
     *
     * @return the defense power of the Player
     */
    public double getDefense() {
        return defense;
    }

    /**
     * Obtains the current health of the Player.
     *
     * @return the current health of the Player
     */
    public double getHealth() {
        return currentHealth;
    }

    /**
     * Obtains the current experience level of the Player.
     *
     * @return the current experience level of the Player.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Obtains the current Room that the Player inhabits.
     *
     * @return the current Room that the Player inhabits
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Changes the Room in which the Player is at.
     *
     * @param currentRoom the Room to move to
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Removes the specified Item from the internal items array.
     * This is done by converting the internal array structure to an ArrayList, using the ArrayList's .remove() method,
     * and then storing the resulting contents back into the internal array structure.
     *
     * @param item the item to remove
     */
    public void removeItem(Item item) {
        ArrayList<Item> listRepresentation = new ArrayList<>(Arrays.asList(items));
        listRepresentation.remove(item);

        //.toArray(T[]) uses the size of T to copy over values...
        //if the sizes mismatch, extra places are filled with null
        //to avoid this, pass in an empty array one less in size
        items = listRepresentation.toArray(new Item[items.length - 1]);
    }

    /**
     * Adds in the specified Item to the internal items array.
     * This is done by converting the internal array structure to an ArrayList, using the ArrayList's .add() method,
     * and then storing the resulting contents back into the internal array structure.
     *
     * @param item the item to add
     */
    public void addItem(Item item) {
        ArrayList<Item> listRepresentation = new ArrayList<>(Arrays.asList(items));
        listRepresentation.add(item);

        //.toArray(T[]) uses the size of T to copy over values...
        //if the sizes mismatch, extra places are filled with null
        items = listRepresentation.toArray(new Item[items.length + 1]);
    }
}
