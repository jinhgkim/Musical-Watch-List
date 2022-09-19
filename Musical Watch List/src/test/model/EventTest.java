package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EventTest {
    private static final int HASH_CONSTANT = 13;
    private Event e;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("event");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testConstructor() {
        assertEquals("event", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testEquals() {
        Event e1 = new Event("event 1");
        Event e2 = new Event("event 2");
        Event e3 = new Event("event 1");
        assertTrue(e1.equals(e1));
        assertFalse(e1.equals(e2));
        assertTrue(e1.equals(e3));
        assertFalse(e2.equals(e3));

        assertFalse(e1.equals(null));
        assertFalse(e1.equals(new Event("event")));
    }

    @Test
    public void testHashCode() {
        assertEquals(e.hashCode(), HASH_CONSTANT * e.getDate().hashCode() + e.getDescription().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "event", e.toString());
    }
}
