package data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class representing a room in the game.
 */
public class Room {
    /**
     * Name of the room. Used in Layout.findRoomByName(String roomName) to obtain the corresponding Room object.
     */
    @SerializedName("name")
    private String name;

    /**
     * Brief description of the room displayed upon entrance.
     */
    @SerializedName("description")
    private String description;

    /**
     * All possible directions that the Player may move. Used during the Player movement process.
     */
    @SerializedName("directions")
    private Direction[] directions;

    /**
     * Collection of all the items that are located in the room.
     */
    @SerializedName("items")
    private Item[] items;

    /**
     * Collection of all the monsters' names in the room. Used by Layout.findMonsterByName(String monsterName)
     * to obtain the corresponding Monster object.
     */
    @SerializedName("monstersInRoom")
    private String[] monsterNames;

    /**
     * Used for testing, and not used during live deployment of the game.
     */
    private Room() {
        items = new Item[0];
        monsterNames = new String[0];
        directions = new Direction[0];
        name = "";
        description = "";
    }

    /**
     * Obtain the name of the Room.
     *
     * @return the name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * Obtain the description of the Room.
     *
     * @return the description of the room
     */
    public String getDescription() {
        return description;
    }

    /**
     * Obtain the directions that one can go from this Room.
     *
     * @return the directions that one can go from this Room
     */
    public Direction[] getDirections() {
        return directions;
    }

    /**
     * Obtain all the items that are found within this Room.
     * </P>Will handle initialization of the internal items array if it is null.
     *
     * @return all the items that are found within this Room
     */
    public Item[] getItems() {
        //some JSON objects don't have an items field...
        if (items == null) {
            items = new Item[0];
        }

        return items;
    }

    /**
     * Obtain the list of monsters' names in the Room.
     * Used by findMonsterByName(String monsterName) to find the corresponding Monster.
     *
     * @return
     */
    public String[] getMonsterNames() {
        return monsterNames;
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
        //to avoid issues, the array is increased in size by one
        items = listRepresentation.toArray(new Item[items.length + 1]);
    }

    /**
     * Removes the specified Monster name from the internal array structure.
     * This is done by converting the internal array structure to an ArrayList, using the ArrayList's .remove() method,
     * and then storing the resulting contents back into the internal array structure.
     *
     * @param monsterName the monster name to remove
     */
    public void removeMonster(String monsterName) {
        ArrayList<String> listRepresentation = new ArrayList<>(Arrays.asList(monsterNames));
        listRepresentation.remove(monsterName);

        //.toArray(T[]) uses the size of T to copy over values...
        //if the sizes mismatch, extra places are filled with null
        //to avoid this, pass in an empty array one less in size
        monsterNames = listRepresentation.toArray(new String[monsterNames.length - 1]);
    }

    /**
     * Adds in the specified Monster name to the internal items array.
     * This is done by converting the internal array structure to an ArrayList, using the ArrayList's .add() method,
     * and then storing the resulting contents back into the internal array structure.
     *
     * @param monsterName the monster name to add
     */
    public void addMonster(String monsterName) {
        ArrayList<String> listRepresentation = new ArrayList<>(Arrays.asList(monsterNames));
        listRepresentation.add(monsterName);

        //.toArray(T[]) uses the size of T to copy over values...
        //if the sizes mismatch, extra places are filled with null
        //to avoid issues, the array is increased in size by one
        monsterNames = listRepresentation.toArray(new String[monsterNames.length + 1]);
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
        Room room = (Room) o;
        return Objects.equals(name, room.name) &&
                Objects.equals(description, room.description) &&
                Arrays.equals(directions, room.directions) &&
                Arrays.equals(items, room.items);
    }

    /**
     * Overridden hashCode generator method. Utilizes each field of the Room object.
     *
     * @return hashcode representation of this object.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(name, description);
        result = 31 * result + Arrays.hashCode(directions);
        result = 31 * result + Arrays.hashCode(items);
        return result;
    }
}
