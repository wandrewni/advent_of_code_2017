package org.wandotini.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class TestUtils {
    public static BufferedReader readFileIntoReader(String filename) {
        InputStream resourceAsStream = TestUtils.class.getClassLoader().getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(resourceAsStream));
    }

    public static Stream<String> readFileIntoLines(String filename) {
        return readFileIntoReader(filename)
                .lines()
                .map(line -> line.replace("\n",""));
    }
}
