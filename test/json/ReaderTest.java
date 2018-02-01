package json;

import data.Layout;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ReaderTest {
    /**
     * Necessary to be public because of annotation.
     */
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private static Reader reader;

    /**
     * Needs to be static because of annotation (ie. static "constructor")
     */
    @BeforeClass
    public static void setUp() {
        reader = new Reader();
    }

    @Test
    public void parseJsonFail() {
        String filename = "nadaherehaha.json";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Could not find file: " + filename);
        reader.parseJson(Collections.singletonList(filename));
    }

    @Test
    public void parseJsonSuccess() {
        String filename = "siebel.json";
        Reader reader = new Reader();
        List<Layout> layout = reader.parseJson(Collections.singletonList(filename));
        assertEquals(1, layout.size());
    }
}