package console;

import command.Command;
import data.Layout;
import data.Monster;
import data.Player;
import data.Room;
import error.InvalidInputException;
import error.MonsterNotFoundException;
import error.NoItemFoundException;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Citations:
 *      - Setting and working with inputs: https://stackoverflow.com/questions/1647907/junit-how-to-simulate-system-in-testing
 *      - Setting and working with outputs: https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
 */
public class ConsoleTest {
    private static ByteArrayOutputStream outContent;
    private static ByteArrayInputStream inContent;
    private static InputStream defaultIn;
    private static OutputStream defaultOut;
    private static String printMessage;
    private static String sampleInput;
    private static String sampleTake;
    private static String sampleDrop;
    private static String sampleGo;
    private static String sampleList;
    private static Player testPlayer;
    private static Room testRoom;
    private static Layout testLayout;
    private static Monster testMonster;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
        testMonster = monsterConstructor.newInstance();

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
        defaultIn = System.in;
        defaultOut = System.out;

        //String constants used by processInput()/readInput() tests
        printMessage = "Gimme gimme." + System.lineSeparator();
        sampleInput = "Take apples and oranges" + System.lineSeparator();
        sampleTake = "take cherries" + System.lineSeparator();
        sampleDrop = "drop memes" + System.lineSeparator();
        sampleGo = "go North" + System.lineSeparator();
        sampleList = "list" + System.lineSeparator();
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
        System.setIn(defaultIn);
        System.setOut(new PrintStream(defaultOut));
    }

    @Test
    public void testConsolePrint() {
        Console.print(printMessage);
        assertEquals(outContent.toString(), printMessage);
    }

    @Test
    public void testConsolePrintln() {
        Console.println(printMessage);
        assertEquals(outContent.toString(), printMessage + System.lineSeparator());
    }

    @Test
    public void testConsolePrintWithExtra() {
        int amountOfExtra = 5;
        Console.printlnExtra(printMessage, amountOfExtra);

        StringBuilder buildResult = new StringBuilder();
        buildResult.append(printMessage);
        for (int i = 0; i < amountOfExtra + 1; i++) {
            buildResult.append(System.lineSeparator());
        }

        assertEquals(buildResult.toString(), outContent.toString());
    }

    @Test
    public void testConsolePrintArrayWithCommas() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] testing = new String[] {"testOne", "testTwo", "testThree"};

        Method method = Console.class.getDeclaredMethod("printArrayWithCommas", Object[].class);
        method.setAccessible(true);
        method.invoke(null, new Object[] {testing} );

        assertEquals("testOne, testTwo, testThree", outContent.toString());
    }

    @Test
    public void testConsolePrintPlayerContents() {
        Console.printPlayerContents(testPlayer);

        assertEquals("Your items: []" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void testConsolePrintRoomContents() {
        Console.printRoomContents(testRoom);

        assertEquals("Room items: []" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void testPrintUponEntrance() {
        Console.printUponEntrance(testLayout, testRoom);

        //both clauses fire off because all the Room objects are empty, and therefore, 'equal'
        String finalOutput =
                "Room name: testRoom" + System.lineSeparator() +
                "Room description: testDescription" + System.lineSeparator() +
                "Your journey begins here." + System.lineSeparator() +
                "You have reached your final destination!" + System.lineSeparator();

        assertEquals(finalOutput, outContent.toString());
    }

    @Test
    public void testPrintDirection() {
        Console.printDirections(testRoom);

        assertEquals("From here, you can go: " + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void testPrintMonstersInRoom() {
        Console.printMonstersInRoom(testRoom, testPlayer);

        //will not print out monsters if there are none
        assertEquals("", outContent.toString());
    }

    @Test
    public void testDuelStatus() {
        //set up Player for this test
        testPlayer.setDueling(true);
        testPlayer.setOpponent(testMonster);

        Console.printDuelStatus(testPlayer);

        //testMonster's name is an empty string...
        String duelStatus =
                "---------Duel Status----------" + System.lineSeparator() +
                        "testMonster's status: " + System.lineSeparator() +
                        "\tHealth: 0.0" + System.lineSeparator() +
                        "\tAttack: 0.0" + System.lineSeparator() +
                        "\tDefense: 0.0" + System.lineSeparator() +
                        "Your status: " + System.lineSeparator() +
                        "\tHealth: 0.0" + System.lineSeparator() +
                        "\tAttack: 0.0" + System.lineSeparator() +
                        "\tDefense: 0.0" + System.lineSeparator() +
                        "Your items: []" + System.lineSeparator() +
                "------------------------------" + System.lineSeparator();

        assertEquals(duelStatus, outContent.toString());

        //reset Player object
        testPlayer.setDueling(false);
        testPlayer.setOpponent(null);
    }

    @Test
    public void testReadInputTake() throws InvalidInputException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //finish finalizing the I/O testing process
        inContent = new ByteArrayInputStream(sampleInput.getBytes());
        System.setIn(inContent);

        Method setScannerMethod = Console.class.getDeclaredMethod("setScanner", InputStream.class);
        setScannerMethod.setAccessible(true);
        setScannerMethod.invoke(null, inContent);

        //
        // --------------------------------------------------------------------------------------------------
        //

        Console.readInput();

        Field commandField = Console.class.getDeclaredField("command");
        commandField.setAccessible(true);
        String valueOfCommand = (String)commandField.get(null);

        assertEquals("Take", valueOfCommand);

        Field argsField = Console.class.getDeclaredField("args");
        argsField.setAccessible(true);
        String[] args = (String[]) argsField.get(null);

        assertArrayEquals(args, new String[] {"apples", "and", "oranges"});

        Field fullInputField = Console.class.getDeclaredField("fullInput");
        fullInputField.setAccessible(true);
        String fullInput = (String) fullInputField.get(null);

        assertEquals(fullInput + System.lineSeparator(), sampleInput);
    }

    @Test
    public void testProcessInputWithSampleDrop() throws InvalidInputException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, MonsterNotFoundException, NoItemFoundException {
        //finish finalizing the I/O testing process
        inContent = new ByteArrayInputStream(sampleDrop.getBytes());
        System.setIn(inContent);

        Method setScannerMethod = Console.class.getDeclaredMethod("setScanner", InputStream.class);
        setScannerMethod.setAccessible(true);
        setScannerMethod.invoke(null, inContent);

        //
        // --------------------------------------------------------------------------------------------------
        //

        thrown.expect(InvalidInputException.class);
        thrown.expectMessage("I can't drop \'memes\'");

        Console.readInput();
        Command command = Console.processInput(testPlayer, testLayout);
        command.execute();
    }

    @Test
    public void testProcessInputWithSampleTake() throws InvalidInputException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, MonsterNotFoundException, NoItemFoundException {
        //finish finalizing the I/O testing process
        inContent = new ByteArrayInputStream(sampleTake.getBytes());
        System.setIn(inContent);

        Method setScannerMethod = Console.class.getDeclaredMethod("setScanner", InputStream.class);
        setScannerMethod.setAccessible(true);
        setScannerMethod.invoke(null, inContent);

        //
        // --------------------------------------------------------------------------------------------------
        //

        thrown.expect(InvalidInputException.class);
        thrown.expectMessage("I can't take \'cherries\'");

        Console.readInput();
        Command command = Console.processInput(testPlayer, testLayout);
        command.execute();
    }

    @Test
    public void testProcessInputWithSampleGo() throws InvalidInputException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, MonsterNotFoundException, NoItemFoundException {
        //finish finalizing the I/O testing process
        inContent = new ByteArrayInputStream(sampleGo.getBytes());
        System.setIn(inContent);

        Method setScannerMethod = Console.class.getDeclaredMethod("setScanner", InputStream.class);
        setScannerMethod.setAccessible(true);
        setScannerMethod.invoke(null, inContent);

        //
        // --------------------------------------------------------------------------------------------------
        //

        thrown.expect(InvalidInputException.class);
        thrown.expectMessage("I can't go \'North\'");

        Console.readInput();
        Command command = Console.processInput(testPlayer, testLayout);
        command.execute();
    }

    @Test
    public void testProcessInputWithSampleList() throws InvalidInputException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, MonsterNotFoundException, NoItemFoundException {
        //finish finalizing the I/O testing process
        inContent = new ByteArrayInputStream(sampleList.getBytes());
        System.setIn(inContent);

        Method setScannerMethod = Console.class.getDeclaredMethod("setScanner", InputStream.class);
        setScannerMethod.setAccessible(true);
        setScannerMethod.invoke(null, inContent);

        //
        // --------------------------------------------------------------------------------------------------
        //

        Console.readInput();
        Command command = Console.processInput(testPlayer, testLayout);
        command.execute();

        assertEquals("Your items: []" + System.lineSeparator(), outContent.toString());
    }
}