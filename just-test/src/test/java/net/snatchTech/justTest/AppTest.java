package net.snatchTech.justTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    void sumEqualTen() {
        assertTrue(App.sumEqualTen(4, 6));
        assertFalse(App.sumEqualTen(3, 2));
    }
}
