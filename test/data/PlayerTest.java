package data;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PlayerTest {
    private static Room testRoom;
    private static Player testPlayer;
    private static Item testItem;
    private static double TOLERANCE;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Room> roomConstructor = Room.class.getDeclaredConstructor();
        roomConstructor.setAccessible(true);
        testRoom = roomConstructor.newInstance();

        Constructor<Player> playerConstructor = Player.class.getDeclaredConstructor(Room.class);
        playerConstructor.setAccessible(true);
        testPlayer = playerConstructor.newInstance(testRoom);

        Constructor<Item> itemConstructor = Item.class.getDeclaredConstructor();
        itemConstructor.setAccessible(true);
        testItem = itemConstructor.newInstance();

        TOLERANCE = 0.0001;
    }

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
}