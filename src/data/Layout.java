package data;

import com.google.gson.annotations.SerializedName;
import error.IncompleteBuilderException;
import error.InvalidInputException;
import error.MonsterNotFoundException;
import error.NoRoomException;
import json.Reader;

import java.io.IOException;

/**
 * Class representing the layout (ie. Game World) of the game.
 */
public class Layout {
    /**
     * Public inner class that handles the creation of the Layout of the game.
     * <p></p>
     * This was made in order to handle the fact that some fields exist in the Layout object
     * that do not exist in the JSON, and having a separate method to initialize these values after
     * the Reader parses the JSON to create this objects leaves the potential for the object to be in a
     * partially constructed state. As a result, a design pattern known as the 'Builder' can be used to hide away
     * these detail in order to enforce complete and safe creation of these objects.
     * <p></p>
     * Usage: The general pattern for using a Builder involves calling an empty constructor to obtain an instance
     * of the Builder, and then calling methods whose name mimics the name of the field they want to fill. While a
     * simple Builder may only end up accepting a few fields and creating an object based on a single build() method,
     * this Builder must have the functionality to build from either a file path or a url to the internet. As a result,
     * a few checks and exceptions are in place in order to enforce the proper use of this Builder.
     * <p></p>
     * Note: A constructor is not used to initialize these fields because some of these fields rely on the state of
     * the fields read in from the JSON file. Since GSON encapsulates that process away from the programmer, it is not
     * possible to know when GSON will read from the JSON fields and when GSON will try to instantiate by means of the
     * constructor. In order to avoid problems arising from this, this Builder pattern enforces the policy that
     * the fields that rely on the field read from the JSON (by GSON) are <b>always</b> done after.
     */
    public static class Builder {
        /**
         * String representation of the link url, if necessary. <b>Must be null if reading from a file.</b>
         */
        private String url;

        /**
         * String representation of the file path, if necessary. <b>Must be null if reading from a url.</b>
         */
        private String filePath;

        /**
         * Empty constructor used to obtain an instance of this builder.
         */
        public Builder() {}

        /**
         * Sets the url field if parsing from a url. Will throw an exception if used improperly.
         *
         * @param url path to url
         * @return an instance of the Builder object with the url field set
         * @throws IncompleteBuilderException if this method is called alongside of path()
         */
        public Builder url(String url) throws IncompleteBuilderException {
            if (filePath != null) {
                throw new IncompleteBuilderException("Only call url() if there's parsing from a URL!");
            }

            this.url = url;
            return this;
        }

        /**
         * Set the file path field inside of the Builder object. Will throw an exception if used improperly.
         *
         * @param filePath path to url
         * @return an instance of the Builder object with file path field set
         * @throws IncompleteBuilderException if this method is called alongside url()
         */
        public Builder path(String filePath) throws IncompleteBuilderException {
            if (url != null) {
                throw new IncompleteBuilderException("Only call path() if there's parsing from a file!");
            }

            this.filePath = filePath;
            return this;
        }

        /**
         * Creates a Layout object read from a url link. Will throw an exception if used improperly,
         * or if there is an issue with the JSON reading process.
         *
         * @return a complete Layout object read from a url
         * @throws InvalidInputException if the JSON parsing process goes awry
         * @throws IncompleteBuilderException if url field is not set OR the filePath field is set
         */
        public Layout buildFromURL() throws InvalidInputException, IncompleteBuilderException {
            if (url == null) {
                throw new IncompleteBuilderException("URL has not been set! Use .url() method!");
            }
            if (filePath != null) {
                throw new IncompleteBuilderException("Only call buildFromURL() if building from a URL!");
            }

            Layout layout = Reader.parseJsonFromURL(url);
            layout.initAfterParse();
            return layout;
        }

        /**
         * Creates a Layout object read from a local file path. WIll throw an exception if used improperly,
         * or if there is an issue with the JSON reading process, or there is an issue with reading from the local
         * file.
         *
         * @return a complete Layout object read from a url
         * @throws InvalidInputException if the JSON parsing process goes awry
         * @throws IncompleteBuilderException if the filePath field is not set OR the url field is set
         * @throws IOException if there is an issue with reading from the file
         */
        public Layout buildFromFile() throws InvalidInputException, IncompleteBuilderException,  IOException {
            if (filePath == null) {
                throw new IncompleteBuilderException("filePath has not been set! Use .path() method!");
            }
            if (url != null) {
                throw new IncompleteBuilderException("Only call buildFromFile() if building from a file!");
            }

            Layout layout = Reader.parseJsonFromFile(filePath);
            layout.initAfterParse();
            return layout;
        }
    }

    /**
     * Private instantiation method that sets the remaining fields in the Layout object that aren't set by GSON.
     * See {@link Layout.Builder} for more information.
     *
     * @throws InvalidInputException if there is no starting or ending room in the JSON file
     */
    private void initAfterParse() throws InvalidInputException {
        try {
            startingRoomObj = findRoomByName(startingRoomName);
        } catch (NoRoomException e) {
            throw new InvalidInputException("There is no starting room!");
        }

        try {
            endingRoomObj = findRoomByName(endingRoomName);
        } catch (NoRoomException e) {
            throw new InvalidInputException("There is no ending room!");
        }
    }

    /**
     * Private constructor that is never used. See {@link Layout.Builder} for more information.
     */
    private Layout() {}

    /**
     * String representation of the name of starting room.
     */
    @SerializedName("startingRoom")
    private String startingRoomName;

    /**
     * String representation of the name of the ending room.
     */
    @SerializedName("endingRoom")
    private String endingRoomName;

    /**
     * Internal array structure housing all of the Room objects.
     */
    @SerializedName("rooms")
    private static Room[] rooms;

    /**
     * Internal representation of the Player object.
     */
    @SerializedName("player")
    private Player player;

    /**
     * Internal array housing all of the Monster objects.
     */
    @SerializedName("monsters")
    private static Monster[] monsters;

    /**
     * Reference to Room object corresponding to the starting room.
     */
    private Room startingRoomObj;

    /**
     * Reference to the Room object corresponding to the ending room.
     */
    private Room endingRoomObj;

    /**
     * Obtains the Room array housing all of the Room objects within the game.
     *
     * @return the Room array housing all of the Room objects within the game
     */
    public Room[] getRooms() {
        return rooms;
    }

    /**
     * Obtains the name of the starting room.
     *
     * @return the name of the starting room
     */
    public String getStartingRoomName() {
        return startingRoomName;
    }

    /**
     * Obtains the name of the ending room.
     *
     * @return the name of the ending room
     */
    public String getEndingRoomName() {
        return endingRoomName;
    }

    /**
     * Obtains the Player object.
     *
     * @return the Player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Obtains the Monster array housing all of the Monster objects within the game.
     *
     * @return the Monster array housing all of the Monster objects within the game
     */
    public Monster[] getMonsters() {
        return monsters;
    }

    /**
     * Returns the reference to the Room object representing the starting Room in the game.
     *
     * @return the reference to the Room object representing the starting Room in the game
     */
    public Room getStartingRoom() {
        return startingRoomObj;
    }

    /**
     * Returns the reference to the Room object representing the ending Room in the game.
     *
     * @return the reference to the Room object representing the ending Room in the game
     */
    public Room getEndingRoom() {
        return endingRoomObj;
    }

    /**
     * Public helper method that converts the passed in name to a reference of the Room object that it represents.
     * If none exists, throws a {@link NoRoomException} exception
     *
     * @param roomName the room name to look for
     * @return the reference to the Room object whose name equal to the roomName argument passed in
     * @throws NoRoomException if there is no Room object whose name is equal to the roomName argument passed in
     */
    public static Room findRoomByName(String roomName) throws NoRoomException {
        for (Room room : rooms) {
            if (roomName.equals(room.getName())) {
                return room;
            }
        }

        throw new NoRoomException("Room cannot be found for search string: " + roomName);
    }

    /**
     * Public helper method that converts the passed in name to a reference of the Room object that it represents.
     * If none exists, throws a {@link MonsterNotFoundException} exception
     *
     * @param monsterName the monster name to look for
     * @return the reference to the Monster object whose name is equals to the monsterName argument passed in
     * @throws MonsterNotFoundException if there is no Monster object whose name is equal to
     * the monsterName argument passed in
     */
    public static Monster findMonsterByName(String monsterName) throws MonsterNotFoundException {
        for (Monster monster : monsters) {
            if (monsterName.equals(monster.getName())) {
                return monster;
            }
        }

        throw new MonsterNotFoundException("Monster cannot be found for search string: " + monsterName);
    }
}