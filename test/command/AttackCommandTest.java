package command;

import data.Item;
import data.Monster;
import data.Player;
import data.Room;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AttackCommandTest {
    private static Player testPlayer;
    private static Monster testMonster;
    private static Item testItem;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //creation of the test Room object by means of Reflection
        Constructor<Room> roomConstructor = Room.class.getDeclaredConstructor();
        roomConstructor.setAccessible(true);
        Room testRoom = roomConstructor.newInstance();

        //creation of the test Player object by means of Reflection
        Constructor<Player> playerConstructor = Player.class.getDeclaredConstructor(Room.class);
        playerConstructor.setAccessible(true);
        testPlayer = playerConstructor.newInstance(testRoom);

        //creation of the test Monster object by means of Reflection
        Constructor<Monster> monsterConstructor = Monster.class.getDeclaredConstructor();
        monsterConstructor.setAccessible(true);
        testMonster = monsterConstructor.newInstance();

        //creation of the test Item object by means of Reflection
        Constructor<Item> itemConstructor = Item.class.getDeclaredConstructor();
        itemConstructor.setAccessible(true);
        testItem = itemConstructor.newInstance();
    }

    @Test
    public void testAttackWithItem() {
        testPlayer.addItem(testItem);

        new AttackCommand(testPlayer, testMonster, testItem).execute();
    }

    @Test
    public void testAttackWithoutItem() {
        new AttackCommand(testPlayer, testMonster).execute();
    }
}