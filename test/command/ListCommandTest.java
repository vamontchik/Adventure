package command;

import data.Layout;
import data.Monster;
import data.Player;
import data.Room;
import error.InvalidInputException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

public class ListCommandTest {
    private static Layout testLayout;
    private static OutputStream defaultOut;
    private static ByteArrayOutputStream outContent;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //creation of the test Room object by means of Reflection
        Constructor<Room> roomConstructor = Room.class.getDeclaredConstructor();
        roomConstructor.setAccessible(true);
        Room testRoom = roomConstructor.newInstance();

        //creation of the test Player object by means of Reflection
        Constructor<Player> playerConstructor = Player.class.getDeclaredConstructor(Room.class);
        playerConstructor.setAccessible(true);
        Player testPlayer = playerConstructor.newInstance(testRoom);

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
    public void testListing() throws InvalidInputException {
        Command list = new ListCommand(testLayout);
        list.execute();

        assertEquals("Your items: []" + System.lineSeparator(), outContent.toString());
    }
}