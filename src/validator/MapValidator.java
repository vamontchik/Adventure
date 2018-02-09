package validator;

import data.Direction;
import data.Layout;
import data.Room;
import error.InvalidMapException;
import error.NoRoomException;

import java.util.ArrayList;

/**
 * Class handling the validation process for the game.
 */
public class MapValidator {
    /**
     * The ArrayList responsible for storing the room values to test.
     */
    private static ArrayList<Room> trip;

    static {
        trip = new ArrayList<>();
    }

    /**
     * Validates the passed-in Layout object by recursively adding in adjacent rooms from the starting room.
     * Will pass if there's a complete path between startingRoom and endingRoom.
     * Will thrown an exception if there is no complete path.
     *
     * @param layout the Layout object to test
     * @throws InvalidMapException if there is no complete path between startingRoom and endingRoom
     */
    public static void validate(Layout layout) throws InvalidMapException, NoRoomException {
        String errorMessage = "The layout JSON is not valid. The endingRoom cannot be reached from the startingRoom.";

        addAllAdjacentRooms(layout.getStartingRoom());
        if (trip.contains(layout.getEndingRoom())) {
            return;
        }
        throw new InvalidMapException(errorMessage);
    }

    /**
     * Recursive helper method that adds in all adjacent rooms to the ArrayList trip above the passed-in Room object.
     *
     * @param currentRoom the currentRoom being passed in
     * @throws NoRoomException if it cannot find the room with the specified name. This indicates an error with the JSON file.
     */
    private static void addAllAdjacentRooms(Room currentRoom) throws NoRoomException {
        if (!trip.contains(currentRoom)) {
            trip.add(currentRoom);
            Direction[] adjacentDirections = currentRoom.getDirections();
            for (Direction dir : adjacentDirections) {
                Room dirToRoom;
                try {
                    dirToRoom = Layout.findRoomByName(dir.getRoomName());
                } catch (NoRoomException e) {
                    throw new NoRoomException("Validation failed due to error in JSON: " +
                                              "Mismatch between Direction and Room object.");
                }
                addAllAdjacentRooms(dirToRoom);
            }
        }
    }
}
