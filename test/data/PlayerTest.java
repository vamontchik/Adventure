package data;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PlayerTest {
    private static Room testRoom;
    private static Player testPlayer;
    private static Item testItem;
    private static double TOLERANCE;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //creation of the test Room object by means of Reflection
        Constructor<Room> roomConstructor = Room.class.getDeclaredConstructor();
        roomConstructor.setAccessible(true);
        testRoom = roomConstructor.newInstance();

        //creation of the test Player object by means of Reflection
        Constructor<Player> playerConstructor = Player.class.getDeclaredConstructor(Room.class);
        playerConstructor.setAccessible(true);
        testPlayer = playerConstructor.newInstance(testRoom);

        //creation of the test Item object by means of Reflection
        Constructor<Item> itemConstructor = Item.class.getDeclaredConstructor();
        itemConstructor.setAccessible(true);
        testItem = itemConstructor.newInstance();

        //delta value used to compare doubles
        TOLERANCE = 0.0001;
    }

    @Test
    public void testPlayerGetters() {
        testPlayer.setExperience(0);
        testPlayer.setLevel(1);

        assertEquals(testRoom, testPlayer.getCurrentRoom());
        assertArrayEquals(new Item[0], testPlayer.getItems());
        assertEquals(0.0, testPlayer.getAttack(), TOLERANCE);
        assertEquals(0.0, testPlayer.getDefense(), TOLERANCE);
        assertEquals(0.0, testPlayer.getHealth(), TOLERANCE);
        assertEquals(1, testPlayer.getLevel());
        assertEquals("testPlayer", testPlayer.getName());
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

    @Test
    public void testLevelingBasicOneTwo() {
        testPlayer.setExperience(0);
        testPlayer.setLevel(1);

        assertEquals(testPlayer.getLevel(), 1);
        assertEquals(testPlayer.getExperience(), 0, TOLERANCE);

        testPlayer.setExperience(25);

        assertEquals(testPlayer.getLevel(), 2);
        assertEquals(testPlayer.getExperience(), 0, TOLERANCE);
    }

    @Test
    public void testLevelingBasicOneThree() {
        testPlayer.setExperience(0);
        testPlayer.setLevel(1);

        assertEquals(testPlayer.getLevel(), 1);
        assertEquals(testPlayer.getExperience(), 0, TOLERANCE);

        testPlayer.setExperience(80);

        assertEquals(testPlayer.getLevel(), 3);
        assertEquals(testPlayer.getExperience(), 0, TOLERANCE);
    }

    @Test
    public void testLevelingBasicOneFour() {
        testPlayer.setExperience(0);
        testPlayer.setLevel(1);

        assertEquals(testPlayer.getLevel(), 1);
        assertEquals(testPlayer.getExperience(), 0, TOLERANCE);

        testPlayer.setExperience(168);

        assertEquals(testPlayer.getLevel(), 4);
        assertEquals(testPlayer.getExperience(), 0, TOLERANCE);
    }

    @Test
    public void testLevelingBasicOneFive() {
        testPlayer.setExperience(0);
        testPlayer.setLevel(1);

        assertEquals(testPlayer.getLevel(), 1);
        assertEquals(testPlayer.getExperience(), 0, TOLERANCE);

        testPlayer.setExperience(325.3);

        assertEquals(testPlayer.getLevel(), 5);
        assertEquals(testPlayer.getExperience(), 0, TOLERANCE);
    }

    @Test
    public void testLevelingHardAF() {
        testPlayer.setExperience(0);
        testPlayer.setLevel(1);

        assertEquals(testPlayer.getLevel(), 1);
        assertEquals(testPlayer.getExperience(), 0, TOLERANCE);

        //calculated using another program
        testPlayer.setExperience(52264.071013429326);

        assertEquals(testPlayer.getLevel(), 14);
        assertEquals(testPlayer.getExperience(), 0, TOLERANCE);
    }
}