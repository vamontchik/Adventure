package json;

import data.Layout;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public final class ReaderTest {
    /**
     * Necessary to be public because of annotation.
     */
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    /**
     * Needs to be static because of annotation (ie. static "constructor")
     */
    @BeforeClass
    public static void setUp() {
        //
    }

    @Test
    public void parseJsonFail() {
        String filename = "nadaherehaha.json";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Could not find file: " + filename);
        Reader.parseJson(filename);
    }

    @Test
    public void parseJsonSuccess() {
        String filename = "siebel.json";
        Layout layout = Reader.parseJson(filename);

        assertEquals("MatthewsStreet", layout.getStartingRoomName());
        assertEquals("Siebel1314", layout.getEndingRoomName());
        assertEquals(8, layout.getRooms().length);
    }
}