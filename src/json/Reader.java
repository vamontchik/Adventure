package json;

import com.google.gson.Gson;
import data.Layout;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class Reader {
    /**
     * Helper method that helps safely determine if any of the variable amount of Objects
     * passed in are {@code null}
     *
     * @param objects The variable amount of objects that were passed in
     * @return true if there is at least one object that is {@code null}
     */
    private boolean isNull(Object... objects) {
        //if objects only has one object it treats it as non-vararg...
        if (objects == null) {
            return true;
        }

        for (Object o : objects) {
            if (o == null) {
                return true;
            }
        }

        return false;
    }

    /**
     * Parses the files given by the {@code List} of {@code String} of file names
     * into a {@code List} of {@code Layout} objects.
     *
     * @param filenames the list of file names passed in
     * @return a {@code List} of {@code Layout} object, where each {@code Layout} is the parsed contents
     * of a JSON object found in the file names provided
     */
    public final List<Layout> parseJson(List<String> filenames) {
        if (isNull(filenames)) {
            return new ArrayList<>();
        }

        List<Layout> parsedLayouts = new ArrayList<>();
        Gson gson = new Gson();

        for (String filename : filenames) {
            String toParse = getFileContentsAsString(filename);
            Layout parsedToLayout = gson.fromJson(toParse, Layout.class);
            parsedLayouts.add(parsedToLayout);
        }

        return parsedLayouts;
    }

    /**
     * This function reads the contents of a file located in the project's 'data' directory into a String.
     * Note: This method is piggy-backed from the CourseGrades MP with minor modifications.
     *
     * @param filename contains the name of file
     * @return a String containing the file's contents
     */
    private String getFileContentsAsString(String filename) {
        final Path path = FileSystems.getDefault().getPath("data", filename);

        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not find file: " + filename);
        }
    }
}