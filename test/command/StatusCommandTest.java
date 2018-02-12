package command;

import data.Monster;
import data.Player;
import data.Room;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class StatusCommandTest {
    private static Player testPlayer;
    private static Monster testMonster;
    private static OutputStream outContent;
    private static OutputStream defaultOut;

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

        //resets
        defaultOut = System.out;
    }


    @Before
    public void setUpStreamTests() {
        //this is done to ensure the outContent is fresh and empty for every single test, to avoid issues...
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        //Reset out stream after test is finished
        System.setOut(new PrintStream(defaultOut));
    }

    @Test
    public void testStatusOutput() {
        new StatusCommand(testPlayer, testMonster).execute();

        String goodResult =
                "Player:  ____________________" + System.lineSeparator() +
                "Monster: ____________________" + System.lineSeparator();

        assertEquals(goodResult, outContent.toString());
    }
}