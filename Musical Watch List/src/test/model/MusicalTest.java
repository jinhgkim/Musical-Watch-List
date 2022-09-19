package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for Musical class
class MusicalTest {
    private Musical musical;

    @BeforeEach
    void runBefore() {
        musical = new Musical("Hamilton", "Queen Elizabeth theater", 50);
    }

    @Test
    void testConstructor() {
        assertEquals("Hamilton", musical.getTitle());
        assertEquals("Queen Elizabeth theater", musical.getTheater());
        assertEquals(50, musical.getTicket());
    }
}