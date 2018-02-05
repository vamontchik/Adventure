package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.Layout;
import error.InvalidInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Reader {
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
     * Parses the passed-in url into a {@code Layout} object.
     *
     * @param url the url string to parse
     * @return Parses the passed-in URL into a {@code Layout} object.
     */
    public static Layout parseJsonFromURL(String url) throws InvalidInputException {
        if (isNull(url)) {
            throw new InvalidInputException("Passed in URL is null!");
        }

        String toParse = getFileContentsFromURL(url);

        /*
            How to allow GSON to parse into static variables:

            https://stackoverflow.com/questions/15116537/how-to-convert-static-variables-in-class-to-json
        */
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .create();
        return gson.fromJson(toParse, Layout.class);
    }

    /**
     * Reads the file contents from a specified URL into a String.
     *
     * @param url the URL string to read
     * @return the file contents from a specified URL into a String.
     */
    private static String getFileContentsFromURL(String url) throws InvalidInputException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }


    public static Layout parseJsonFromFile(String path) throws InvalidInputException, IOException {
        if (isNull(path)) {
            throw new InvalidInputException("Passed in filename is null!");
        }

        //get json from local file...
        Path pathTo = FileSystems.getDefault().getPath(path);
        String toParse = new String(Files.readAllBytes(pathTo));

        /*
            How to allow GSON to parse into static variables:

            https://stackoverflow.com/questions/15116537/how-to-convert-static-variables-in-class-to-json
        */
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .create();
        return gson.fromJson(toParse, Layout.class);
    }
}