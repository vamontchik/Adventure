package json;

import com.google.gson.Gson;
import data.Layout;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Reader {
    /**
     * Helper method that helps safely determine if any of the variable amount of Objects
     * passed in are {@code null}
     *
     * @param objects The variable amount of objects that were passed in
     * @return true if there is at least one object that is {@code null}
     */
    private static boolean isNull(Object... objects) {
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
     * Parses the passed-in JSON file into a Layout object.
     *
     * @param filename the name of the file to parse
     * @return the passed-in JSON file into a Layout object
     */
    public static Layout parseJson(String filename) {
        if (isNull(filename)) {
            return null;
        }
        Gson gson = new Gson();
        String toParse = getFileContentsAsString(filename);
        return gson.fromJson(toParse, Layout.class);
    }

    /**
     * This function reads the contents of a file located in the project's 'data' directory into a String.
     * Note: This method is piggy-backed from the CourseGrades MP with minor modifications.
     *
     * @param filename contains the name of file
     * @return a String containing the file's contents
     */
    private static String getFileContentsAsString(String filename) {
        final Path path = FileSystems.getDefault().getPath("data", filename);

        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not find file: " + filename);
        }
    }
}