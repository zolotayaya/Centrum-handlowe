package org.example.app;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class WindowTest {

    @Test
    void testValidInputWithinRange() {
        String simulatedInput = "3\n";
        Window.setScanner(new Scanner(new ByteArrayInputStream(simulatedInput.getBytes())));

        int result = Window.getIntInput(1, 5);
        assertEquals(3, result);
    }

    @Test
    void testInvalidThenValidInput() {
        String simulatedInput = "abc\n10\n2\n";
        Window.setScanner(new Scanner(new ByteArrayInputStream(simulatedInput.getBytes())));
        int result = Window.getIntInput(1, 5);
        assertEquals(2, result);
    }
}
