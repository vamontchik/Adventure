package json;

import com.google.gson.Gson;
import data.Layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
     * Parses the passed-in URL into a Layout object.
     *
     * @param url the URL string to parse
     * @return Parses the passed-in URL into a Layout object.
     */
    public static Layout parseJson(String url) {
        if (isNull(url)) {
            throw new IllegalArgumentException("Passed in URL is null!");
        }

        String toParse = getFileContentsFromURL(url);

        Gson gson = new Gson();
        return gson.fromJson(toParse, Layout.class);
    }

    /**
     * Reads the file contents from a specified URL into a String.
     *
     * @param url the URL string to read
     * @return the file contents from a specified URL into a String.
     */
    private static String getFileContentsFromURL(String url) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}