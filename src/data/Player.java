package data;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Room currentRoom;
    private List<String> items;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.items = new ArrayList<>();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<String> getItems() {
        return items;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }
}
