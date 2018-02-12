package command;

import data.Player;
import data.Room;
import error.InvalidInputException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class PlayerInfoCommandTest {
    private static Player testPlayer;
    private static Room testRoom;
    private static OutputStream outContent;
    private static OutputStream defaultOut;

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
        //Reset in and out streams after test is finished
        System.setOut(new PrintStream(defaultOut));
    }

    @Test
    public void testStatsOutput() throws InvalidInputException {
        Command command = new PlayerInfoCommand(testPlayer);
        command.execute();
        
        String result = "Your stats: " + System.lineSeparator() +
                "\tLevel: 0" + System.lineSeparator() +
                "\tAttack: 0.0" + System.lineSeparator() +
                "\tDefense: 0.0" + System.lineSeparator() +
                "\tHealth: 0.0" + System.lineSeparator() +
                "\tExperience: 0.0" + System.lineSeparator();

        assertEquals(result, outContent.toString());
    }
}