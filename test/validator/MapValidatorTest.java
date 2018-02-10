package validator;

import data.Layout;
import error.IncompleteBuilderException;
import error.InvalidInputException;
import error.InvalidMapException;
import error.NoRoomException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class MapValidatorTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

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