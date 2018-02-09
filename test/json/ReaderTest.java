package json;

import data.Layout;
import data.Room;
import error.IncompleteBuilderException;
import error.InvalidInputException;
import error.InvalidMapException;
import error.NoRoomException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import validator.MapValidator;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class ReaderTest {
    // Necessary to be public because of annotation.
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void layoutParsingFail() throws InvalidInputException, IncompleteBuilderException {
        String badFilename = "https://somesite.com/json/nadaherehaha.json";
        thrown.expect(InvalidInputException.class);
        thrown.expectMessage("Connection reset"); //attempted to read from a non-existent URL
        new Layout.Builder().url(badFilename).buildFromURL();
    }

    @Test
    public void noUrlArgument() throws InvalidInputException, IncompleteBuilderException {
        thrown.expect(IncompleteBuilderException.class);
        thrown.expectMessage("URL has not been set! Use .url() method!");
        new Layout.Builder().buildFromURL();
    }

    @Test
    public void parseJsonSuccess() throws InvalidInputException, IncompleteBuilderException, NoSuchFieldException, IllegalAccessException, IOException {
        String goodFilename = "data\\extended_json.json";

        Layout layout = new Layout.Builder().path(goodFilename).buildFromFile();

        assertEquals("Entrance", layout.getStartingRoom().getName());
        assertEquals("Exit", layout.getEndingRoom().getName());

        /*
         * Accessing a private field with reflection:
         *
         * https://stackoverflow.com/questions/1555658/is-it-possible-in-java-to-access-private-fields-via-reflection
         *
         * Note: These exceptions are never thrown because the inputs are all valid...
         */
        Field roomField = Layout.class.getDeclaredField("rooms");
        roomField.setAccessible(true);

        Room[] rooms = (Room[])roomField.get(layout);

        assertEquals(2, rooms.length);
    }

    @Test
    public void mapValidatorPass() throws InvalidInputException, IncompleteBuilderException, InvalidMapException, IOException, NoRoomException {
        Layout goodMap = new Layout.Builder().path("data\\extended_json.json").buildFromFile();
        MapValidator.validate(goodMap);
    }

    @Test
    public void mapValidatorFail() throws InvalidInputException, IncompleteBuilderException, InvalidMapException, IOException, NoRoomException {
        String errorMessage = "The layout JSON is not valid. The endingRoom cannot be reached from the startingRoom.";
        Layout badMap = new Layout.Builder().path("data\\badmap.json").buildFromFile();

        thrown.expect(InvalidMapException.class);
        thrown.expectMessage(errorMessage);

        MapValidator.validate(badMap);
    }
}