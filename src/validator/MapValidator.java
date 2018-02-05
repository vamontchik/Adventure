package validator;

import data.Direction;
import data.Layout;
import data.Room;
import error.InvalidMapException;
import error.NoRoomException;

import java.util.ArrayList;

public class MapValidator {
    private static ArrayList<Room> trip;

    static {
        trip = new ArrayList<>();
    }

    public static void validate(Layout layout) throws InvalidMapException {
        String errorMessage = "The layout JSON is not valid. The endingRoom cannot be reached from the startingRoom.";

        addAllAdjacentRooms(layout.getStartingRoom());
        if (trip.contains(layout.getEndingRoom())) {
            return;
        }
        throw new InvalidMapException(errorMessage);
    }

    private static void addAllAdjacentRooms(Room currentRoom) {
        if (!trip.contains(currentRoom)) {
            trip.add(currentRoom);
            Direction[] adjacentDirections = currentRoom.getDirections();
            for (Direction dir : adjacentDirections) {
                Room dirToRoom = null;
                try {
                    dirToRoom = Layout.findRoomByName(dir.getRoomName());
                } catch (NoRoomException e) {
                    //assuming no mismatch between the room names in the direction objects and the room objects...
                }
                addAllAdjacentRooms(dirToRoom);
            }
        }
    }
}
