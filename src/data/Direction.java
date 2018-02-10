package data;

import com.google.gson.annotations.SerializedName;

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
