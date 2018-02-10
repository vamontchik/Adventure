package console;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ConsoleTest {

    // Route System.out for JUNIT: https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static String printMessage;

    @BeforeClass
    public static void setUp() {
        System.setOut(new PrintStream(outContent));
        printMessage = "Gimme gimme.";
    }

    @Before
    public void setUpStreamTests() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterClass
    public static void tearDown() {
        System.setOut(System.out);
        System.setErr(System.err);
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
}