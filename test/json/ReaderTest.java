package json;

import data.Layout;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public final class ReaderTest {
    /**
     * Necessary to be public because of annotation.
     */
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private static String urlToJson;

    /**
     * Needs to be static because of annotation (ie. static "constructor")
     */
    @BeforeClass
    public static void setUp() {
        urlToJson = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";
    }

    @Test
    public void parseJsonFail() {
        String filename = "https://somesite.com/json/nadaherehaha.json";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Connection reset");
        Layout layout = Reader.parseJson(filename);
    }

    @Test
    public void parseJsonSuccess() {
        Layout layout = Reader.parseJson(urlToJson);

        assertEquals("MatthewsStreet", layout.getStartingRoomName());
        assertEquals("Siebel1314", layout.getEndingRoomName());
        assertEquals(8, layout.getRooms().length);
    }
}