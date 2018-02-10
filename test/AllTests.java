import data.*;
import error.*;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import validator.MapValidator;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AllTests {
    // Necessary to be public because of annotation.
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //
    // PARSER/BUILDER/VALIDATOR TESTS
    //

    @Test
    public void layoutParsingFail() throws InvalidInputException, IncompleteBuilderException {
        String badFilename = "https://somesite.com/json/nadaherehaha.json";
        thrown.expect(InvalidInputException.class);
        thrown.expectMessage("Connection reset"); //attempted to read from a non-existent URL
        new Layout.Builder().url(badFilename).buildFromURL();
    }

    @Test
    public void noUrlArgument() throws InvalidInputException, IncompleteBuilderException {
        thrown.expect(IncompleteBuilderException.class);
        thrown.expectMessage("URL has not been set! Use .url() method!");
        new Layout.Builder().buildFromURL();
    }

    @Test
    public void parseJsonSuccess() throws InvalidInputException, IncompleteBuilderException, NoSuchFieldException, IllegalAccessException, IOException {
        String goodFilename = "data\\extended_json.json";
        Layout layout = new Layout.Builder().path(goodFilename).buildFromFile();

        assertEquals("Entrance", layout.getStartingRoom().getName());
        assertEquals("Exit", layout.getEndingRoom().getName());

        /*
         * Accessing a private field with reflection:
         *
         * https://stackoverflow.com/questions/1555658/is-it-possible-in-java-to-access-private-fields-via-reflection
         *
         * Note: These exceptions are never thrown because the inputs are all valid...
         */
        Field roomField = Layout.class.getDeclaredField("rooms");
        roomField.setAccessible(true);
        Room[] rooms = (Room[])roomField.get(layout);

        assertEquals(2, rooms.length);
    }

    @Test
    public void mapValidatorPass() throws InvalidInputException, IncompleteBuilderException, InvalidMapException, IOException, NoRoomException {
        Layout goodMap = new Layout.Builder().path("data\\extended_json.json").buildFromFile();
        MapValidator.validate(goodMap);
    }

    @Test
    public void mapValidatorFail() throws InvalidInputException, IncompleteBuilderException, InvalidMapException, IOException, NoRoomException {
        String errorMessage = "The layout JSON is not valid. The endingRoom cannot be reached from the startingRoom.";
        Layout badMap = new Layout.Builder().path("data\\badmap.json").buildFromFile();

        thrown.expect(InvalidMapException.class);
        thrown.expectMessage(errorMessage);

        MapValidator.validate(badMap);
    }

    //
    // INITIALIZATION OF DUMMY OBJECTS
    //

    private static Room testRoom;
    private static Player testPlayer;
    private static Monster testMonster;
    private static Layout testLayout;
    private static Item testItem;
    private static Direction testDirection;
    private static double TOLERANCE;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Room> roomConstructor = Room.class.getDeclaredConstructor();
        roomConstructor.setAccessible(true);
        testRoom = roomConstructor.newInstance();

        Constructor<Player> playerConstructor = Player.class.getDeclaredConstructor(Room.class);
        playerConstructor.setAccessible(true);
        testPlayer = playerConstructor.newInstance(testRoom);

        Constructor<Monster> monsterConstructor = Monster.class.getDeclaredConstructor();
        monsterConstructor.setAccessible(true);
        testMonster = monsterConstructor.newInstance();

        Constructor<Layout> layoutConstructor = Layout.class.getDeclaredConstructor(
                Player.class,
                Room.class,
                Room.class
        );
        layoutConstructor.setAccessible(true);
        testLayout = layoutConstructor.newInstance(
                testPlayer,
                testRoom,
                testRoom
        );

        Constructor<Item> itemConstructor = Item.class.getDeclaredConstructor();
        itemConstructor.setAccessible(true);
        testItem = itemConstructor.newInstance();

        Constructor<Direction> directionConstructor = Direction.class.getDeclaredConstructor();
        directionConstructor.setAccessible(true);
        testDirection = directionConstructor.newInstance();

        TOLERANCE = 0.0001;
    }


    //
    //  ROOM TESTS
    //

    @Test
    public void testRoomGetters() {
        assertEquals("", testRoom.getName());
        assertArrayEquals(new Direction[0], testRoom.getDirections());
        assertArrayEquals(new Item[0], testRoom.getItems());
        assertEquals("", testRoom.getDescription());
        assertArrayEquals(new Monster[0], testRoom.getMonsterNames());
    }

    @Test
    public void testRoomAddAndRemoveItem() {
        assertArrayEquals(new Item[0], testRoom.getItems());
        testRoom.addItem(testItem);
        assertEquals(testItem, testRoom.getItems()[0]);
        testRoom.removeItem(testItem);
        assertArrayEquals(new Item[0], testRoom.getItems());
    }

    @Test
    public void testRoomAddAndRemoveMonster() {
        String monsterName = "muhBoy";

        assertArrayEquals(new String[0], testRoom.getMonsterNames());
        testRoom.addMonster(monsterName);
        assertEquals(monsterName, testRoom.getMonsterNames()[0]);
        testRoom.removeMonster(monsterName);
        assertArrayEquals(new String[0], testRoom.getMonsterNames());
    }

    //
    // PLAYER TESTS
    //

    @Test
    public void testPlayerGetters() {
        assertEquals(testRoom, testPlayer.getCurrentRoom());
        assertArrayEquals(new Item[0], testPlayer.getItems());
        assertEquals(0.0, testPlayer.getAttack(), TOLERANCE);
        assertEquals(0.0, testPlayer.getDefense(), TOLERANCE);
        assertEquals(0.0, testPlayer.getHealth(), TOLERANCE);
        assertEquals(0, testPlayer.getLevel());
        assertEquals("", testPlayer.getName());
    }

    @Test
    public void testPlayerGetAndSetCurrentRoom() {
        assertEquals(testRoom, testPlayer.getCurrentRoom());
        testPlayer.setCurrentRoom(null);
        assertEquals(null, testPlayer.getCurrentRoom());
        testPlayer.setCurrentRoom(testRoom);
        assertEquals(testRoom, testPlayer.getCurrentRoom());
    }

    @Test
    public void testPlayerAddAndRemoveItem() {
        assertArrayEquals(new Item[0], testPlayer.getItems());
        testPlayer.addItem(testItem);
        assertEquals(testItem, testPlayer.getItems()[0]);
        testPlayer.removeItem(testItem);
        assertArrayEquals(new Item[0], testPlayer.getItems());
    }

    //
    // MONSTER TESTS
    //

    @Test
    public void testMonsterGetters() {
        assertEquals("", testMonster.getName());
        assertEquals(0.0, testMonster.getAttack(), TOLERANCE);
        assertEquals(0.0, testMonster.getDefense(), TOLERANCE);
        assertEquals(0.0, testMonster.getHealth(), TOLERANCE);
    }

    //
    // LAYOUT TESTS
    //

    @Test
    public void testLayoutGetters() {
        assertEquals(testPlayer, testLayout.getPlayer());
        assertEquals(testRoom.getName(), testLayout.getStartingRoomName());
        assertEquals(testRoom, testLayout.getStartingRoom());
        assertEquals(testRoom.getName(), testLayout.getEndingRoomName());
        assertEquals(testRoom, testLayout.getEndingRoom());

        // these two test are bizarre and imply that the static fields in testLayout
        // are somehow initialized from the json file...?
        assertEquals(3, testLayout.getMonsters().length);
        assertEquals(2, testLayout.getRooms().length);
    }

    @Test
    public void testLayoutFindRoomSuccess() throws NoRoomException {

        // again, bizarre... why is the rooms field populated???
        Room found = Layout.findRoomByName("Entrance");
        assertEquals(found.getName(), "Entrance");
    }

    @Test
    public void testLayoutFindRoomFail() throws NoRoomException {
        String fakeRoom = "fakeRoom";

        thrown.expect(NoRoomException.class);
        thrown.expectMessage("Room cannot be found for search string: " + fakeRoom);

        Layout.findRoomByName(fakeRoom);
    }

    @Test
    public void testLayoutFindMonsterSuccess() throws MonsterNotFoundException {

        // again, bizarre... why is the monsters field populated???
        Monster found = Layout.findMonsterByName("SkeletonOne");
        assertEquals(found.getName(), "SkeletonOne");
    }

    @Test
    public void testLayoutFindMonsterFail() throws MonsterNotFoundException {
        String fakeMonster = "fakeMunsta";

        thrown.expect(MonsterNotFoundException.class);
        thrown.expectMessage("Monster cannot be found for search string: " + fakeMonster);

        Layout.findMonsterByName(fakeMonster);
    }

    //
    // ITEM TESTS
    //

    @Test
    public void testItemGetters() {
        assertEquals("", testItem.getName());
        assertEquals(0.0, testItem.getDamage(), TOLERANCE);
    }

    //
    // DIRECTION TESTS
    //

    @Test
    public void testDirectionGetters() {
        assertEquals("", testDirection.getRoomName());
        assertEquals("", testDirection.getDirection());
    }
}