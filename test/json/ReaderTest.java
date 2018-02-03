package json;

import data.Layout;
import data.Room;
import error.IncompleteBuilderException;
import error.InvalidInputException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class ReaderTest {
    /**
     * Necessary to be public because of annotation.
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void layoutParsingFail() throws InvalidInputException, IncompleteBuilderException {
        String badFilename = "https://somesite.com/json/nadaherehaha.json";
        thrown.expect(InvalidInputException.class);
        thrown.expectMessage("Connection reset"); //attempted to read from a non-existent URL
        new Layout.Builder().url(badFilename).buildLayout();
    }

    @Test
    public void noUrlArgument() throws InvalidInputException, IncompleteBuilderException {
        thrown.expect(IncompleteBuilderException.class);
        thrown.expectMessage("URL has not been set! Use .url() method!");
        new Layout.Builder().buildLayout();
    }

    /*
     * Accessing a private field with reflection:
     *
     * https://stackoverflow.com/questions/1555658/is-it-possible-in-java-to-access-private-fields-via-reflection
     *
     * Note: These exceptions are never thrown because the inputs are all valid...
     */
    @Test
    public void parseJsonSuccess() throws InvalidInputException, IncompleteBuilderException, NoSuchFieldException, IllegalAccessException {
        String goodFilename = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";

        Layout layout = new Layout.Builder().url(goodFilename).buildLayout();

        assertEquals("MatthewsStreet", layout.getStartingRoom().getName());
        assertEquals("Siebel1314", layout.getEndingRoom().getName());

        Field roomField = Layout.class.getDeclaredField("rooms");
        roomField.setAccessible(true);

        Room[] rooms = (Room[])roomField.get(layout);

        assertEquals(8, rooms.length);
    }
}