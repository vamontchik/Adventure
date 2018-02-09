package data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    @SerializedName("player")
    private String name;

    @SerializedName("items")
    private Item[] items;

    @SerializedName("attack")
    private double attack;

    @SerializedName("defense")
    private double defense;

    @SerializedName("health")
    private double health;

    @SerializedName("level")
    private int level;

    private Room currentRoom;

    public String getName() {
        return name;
    }

    public Item[] getItems() {
        return items;
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

    public int getLevel() {
        return level;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void removeItem(Item item) {
        ArrayList<Item> listRepresentation = new ArrayList<>(Arrays.asList(items));
        listRepresentation.remove(item);

        //.toArray(T[]) uses the size of T to copy over values...
        //if the sizes mismatch, extra places are filled with null
        //to avoid this, pass in an empty array one less in size
        items = listRepresentation.toArray(new Item[items.length - 1]);
    }

    public void addItem(Item item) {
        ArrayList<Item> listRepresentation = new ArrayList<>(Arrays.asList(items));
        listRepresentation.add(item);

        //.toArray(T[]) uses the size of T to copy over values...
        //if the sizes mismatch, extra places are filled with null
        items = listRepresentation.toArray(new Item[items.length + 1]);
    }
}
