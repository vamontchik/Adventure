package data;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;

public final class Room {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("directions")
    private Direction[] directions;

    @SerializedName("items")
    private String[] items;

    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final Direction[] getDirections() {
        return directions;
    }

    public final String[] getItems() {
        return items;
    }

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

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description);
        result = 31 * result + Arrays.hashCode(directions);
        result = 31 * result + Arrays.hashCode(items);
        return result;
    }
}
