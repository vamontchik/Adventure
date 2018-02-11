package command;

import data.Monster;
import data.Player;
import data.Room;
import error.InvalidInputException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class DisengageCommandTest {
    private static Player testPlayer;
    private static Monster testMonster;

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
    }

    @Test
    public void changeStateTest() throws InvalidInputException {
        testPlayer.setDueling(true);
        testPlayer.setOpponent(testMonster);

        assertEquals(testPlayer.isDueling(), true);
        assertEquals(testPlayer.getOpponent(), testMonster);

        Command changeState = new DisengageCommand(testPlayer);
        changeState.execute();

        assertEquals(testPlayer.isDueling(), false);
        assertEquals(testPlayer.getOpponent(), null);
    }
}