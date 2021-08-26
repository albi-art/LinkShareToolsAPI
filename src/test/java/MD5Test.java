/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
import org.junit.jupiter.api.Test;
import utils.MD5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MD5Test {
    @Test
    public void getMd5_isCorrect() {
        String testString1 = "test string 1!";
        String testString2 = "test string 2!";
        assertEquals(MD5.getMd5(testString1), MD5.getMd5(testString1));
        assertNotEquals(MD5.getMd5(testString1), MD5.getMd5(testString2));
    }
}
