package command;

import data.Item;
import data.Player;
import data.Room;
import error.InvalidInputException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class TakeCommandTest {
    private static Player testPlayer;
    private static Item testItem;
    private static Room testRoom;

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
    }

    @Test
    public void testTake() throws InvalidInputException {
        assertEquals(testRoom.getItems().length, 0);
        testRoom.addItem(testItem);

        Command take = new TakeCommand(testPlayer, testItem.getName());
        take.execute();

        assertEquals(testPlayer.getItems()[0], testItem);
    }
}