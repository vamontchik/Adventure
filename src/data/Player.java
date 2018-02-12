package data;

import com.google.gson.annotations.SerializedName;
import console.Console;
import error.NoItemFoundException;

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
    @SerializedName("name")
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
    private double attack;

    /**
     * Defense power of the player.
     */
    @SerializedName("defense")
    private double defense;

    /**
     * Total health of the player.
     */
    @SerializedName("health")
    private double currentHealth;

    private double totalHealth;

    /**
     * Experience level of the player.
     */
    @SerializedName("level")
    private int level;

    /**
     * Room object that the player is currently inhabiting.
     */
    private Room currentRoom;

    /**
     * Dueling state of the player.
     */
    private boolean isDueling;

    /**
     * The opponent to duel
     */
    private Monster opponent;

    /**
     * Boolean to check to see if the player has entered a new room or not.
     */
    private boolean hasEntered;

    /**
     * The Player's experience value.
     */
    private double experience;

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
        isDueling = false;
        opponent = null;
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
     * Returns whether or the the Player is dueling.
     *
     * @return Returns whether or the the Player is dueling.
     */
    public boolean isDueling() {
        return isDueling;
    }

    /**
     * Returns the experience value of the Player.
     *
     * @return the experience value of the Player.
     */
    public double getExperience() {
        return experience;
    }

    /**
     * Updates the Player's experience value.
     *
     * @param newExperience the new experience value to set to
     */
    public void setExperience(double newExperience) {
        this.experience = newExperience;

        levelUpIfNecessary();
    }

    /**
     * Updates the level of the player if the correct amount of experience has been reached.
     */
    private void levelUpIfNecessary() {
        //calculate how much XP is necessary to level up to the next level
        double getExperienceToLevelUp = calculateExperience(level + 1);

        //maintain a record of old level
        int oldLevel = level;

        //keep updating the level, one at a time, until the player has level correctly
        final double TOLERANCE = 0.0001;
        while (experience - getExperienceToLevelUp > TOLERANCE) {
            //Console.println("You have leveled up to level " + level + "!");

            level++;
            levelUpPlayerStats();
            experience -= getExperienceToLevelUp;
        }

        if (oldLevel != level) {
            Console.println("You have leveled up to level " + level + " from level " + oldLevel + "!");
        }
    }

    /**
     *
     */
    private void levelUpPlayerStats() {
       attack *= 1.5;
       defense *= 1.5;

       //leveling up will replenish the player's health
       totalHealth *= 1.3;
       currentHealth = totalHealth;
    }

    /**
     * Private, recursive calculation method that returns how much total experience is needed to level up.
     *
     * @param level the level to calculate XP for
     * @return the amount of total experience required to level up
     */
    private double calculateExperience(int level) {
        if (level <= 2) {
            return 25;
        } else {
            return (calculateExperience(level - 1) + calculateExperience(level - 2)) * 1.1;
        }
    }

    /**
     * Sets the health of the player.
     *
     * @param health the new health to set the Player's health to
     */
    public void setHealth(double health) {
        this.currentHealth = health;
    }

    /**
     * Sets the dueling state of the Player.
     *
     * @param setDueling the dueling state of the Player.
     */
    public void setDueling(boolean setDueling) {
        this.isDueling = setDueling;
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

    /**
     * Sets the opponent to reference during duels.
     *
     * @param opponent the Monster to fight
     */
    public void setOpponent(Monster opponent) {
        this.opponent = opponent;
    }

    /**
     * Returns the opponent to fight. Will return null if not in a duel state.
     *
     * @return the opponent to fight
     */
    public Monster getOpponent() {
        return opponent;
    }

    /**
     * Checks to see if the player has entered a room.
     *
     * @return if the player has entered a room.
     */
    public boolean hasEntered() {
        return hasEntered;
    }

    /**
     * Sets the state of entering the room.
     *
     * @param hasEntered the state to set hasEntered to
     */
    public void setEntered(boolean hasEntered) {
        this.hasEntered = hasEntered;
    }

    /**
     * Obtains the total possible health of the Player.
     *
     * @return the total health of the player.
     */
    public double getTotalHealth() {
        return totalHealth;
    }

    /**
     * Sets the total possible health of the Player.
     *
     * @param totalHealth the new total possible health
     */
    public void setTotalHealth(double totalHealth) {
        this.totalHealth = totalHealth;
    }

    /**
     * Finds the item within the Player's inventory by name.
     *
     * @param itemName the String name of the item to look for
     * @return the Item whose name matches the itemName parameter
     * @throws NoItemFoundException if no item exists with the given search parameter
     */
    public Item findItemByName(String itemName) throws NoItemFoundException {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }

        throw new NoItemFoundException("Item cannot be found for search string: " + itemName);
    }
}
