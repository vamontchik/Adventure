package data;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Class representing the direction a Player may take to get to a Room.
 */
public class Direction {
    /**
     * Name of the direction.
     */
    @SerializedName("directionName")
    private String direction;

    /**
     * Name of the room that the direction points to.
     */
    @SerializedName("room")
    private String room;

    /**
     * Used in testing only.
     */
    private Direction() {
        direction = "";
        room = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction1 = (Direction) o;
        return Objects.equals(direction, direction1.direction) &&
                Objects.equals(room, direction1.room);
    }

    @Override
    public int hashCode() {

        return Objects.hash(direction, room);
    }

    /**
     * Obtains the name of the direction.
     *
     * @return the name of the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Obtains the name of the room that the direction points to.
     *
     * @return the name of the room that the direction points to
     */
    public String getRoomName() {
        return room;
    }

    /**
     * String representation of the Direction object. Used when printing with the {@link console.Console} class
     *
     * @return the string representation of the Direction object
     */
    @Override
    public String toString() {
        return direction;
    }
}
