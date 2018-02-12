package command;

import data.Layout;
import data.Monster;
import data.Player;
import data.Room;
import error.InvalidInputException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class GoCommandTest {
    private static Room testRoom;
    private static Player testPlayer;
    private static Layout testLayout;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();


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

        //creation of the test Monster object by means of Reflection
        Constructor<Monster> monsterConstructor = Monster.class.getDeclaredConstructor();
        monsterConstructor.setAccessible(true);
        Monster testMonster = monsterConstructor.newInstance();

        //creation of the test Layout object by means of Reflection
        Constructor<Layout> layoutConstructor = Layout.class.getDeclaredConstructor(
                Player.class,
                Monster[].class,
                Room[].class,
                Room.class,
                Room.class
        );
        layoutConstructor.setAccessible(true);
        testLayout = layoutConstructor.newInstance(
                testPlayer,
                new Monster[]{testMonster},
                new Room[]{testRoom},
                testRoom,
                testRoom
        );
    }

    @Test
    public void testGo() throws InvalidInputException {
        testPlayer.setCurrentRoom(testRoom);

        assertEquals(testRoom, testPlayer.getCurrentRoom());

        thrown.expect(InvalidInputException.class);
        thrown.expectMessage("I can't go ");

        //fails because none of the rooms are linked to each other, which is expected...
        GoCommand move = new GoCommand(testPlayer, testRoom.getName(), testLayout, testRoom.getName());
        move.execute();
    }
}