package org.wandotini.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestUtils {
    public static BufferedReader readFileIntoReader(String filename) {
        InputStream resourceAsStream = TestUtils.class.getClassLoader().getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(resourceAsStream));
    }
}
