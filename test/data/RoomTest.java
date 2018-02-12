package data;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RoomTest {
    private static Room testRoom;
    private static Item testItem;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //creation of the test Room object by means of Reflection
        Constructor<Room> roomConstructor = Room.class.getDeclaredConstructor();
        roomConstructor.setAccessible(true);
        testRoom = roomConstructor.newInstance();

        //creation of the test Item object by means of Reflection
        Constructor<Item> itemConstructor = Item.class.getDeclaredConstructor();
        itemConstructor.setAccessible(true);
        testItem = itemConstructor.newInstance();
    }

    @Test
    public void testRoomGetters() {
        assertEquals("testRoom", testRoom.getName());
        assertArrayEquals(new Direction[0], testRoom.getDirections());
        assertArrayEquals(new Item[0], testRoom.getItems());
        assertEquals("testDescription", testRoom.getDescription());
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
}