package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


// Unit tests for WatchList class
public class WatchListTest {
    private WatchList watchList;

    @BeforeEach
    void runBefore() {
        watchList = new WatchList();
    }

    @Test
    void testConstructor() {
        assertEquals(0, watchList.getSize());
    }

    @Test
    void testAddMusical() {
        watchList.addMusical("Hamilton", "Queen Elizabeth Theater", 50);
        watchList.addMusical("Come From Away", "Queen Elizabeth Theater", 60);
        watchList.addMusical("Kinky Boots", "Vogue Theater", 70);
        watchList.addMusical("Chicago", "Broadway", 100);

        assertEquals(4, watchList.getSize());
        assertEquals("Chicago", watchList.getIndex(3).getTitle());
    }

    @Test
    void testRemoveMusical() {
        watchList.addMusical("Hamilton", "Queen Elizabeth Theater", 50);
        watchList.addMusical("Come From Away", "Queen Elizabeth Theater", 60);
        watchList.addMusical("Kinky Boots", "Vogue Theater", 70);
        Musical m1 = watchList.getIndex(0);
        Musical m2 = watchList.getIndex(1);
        Musical m3 = watchList.getIndex(2);

        watchList.removeMusical(m2);
        assertEquals(2, watchList.getSize());
        assertEquals("Hamilton", watchList.getIndex(0).getTitle());

        watchList.removeMusical(m1);
        assertEquals(1, watchList.getSize());
        assertEquals("Kinky Boots", watchList.getIndex(0).getTitle());
    }

    @Test
    void testFindMusical() {
        watchList.addMusical("Hamilton", "Queen Elizabeth Theater", 50);
        watchList.addMusical("Come From Away", "Queen Elizabeth Theater", 60);
        watchList.addMusical("Kinky Boots", "Vogue Theater", 70);

        Musical found = watchList.findMusical("Come From Away");
        assertEquals(watchList.getIndex(1), found);

        found = watchList.findMusical("Kinky Boots");
        assertEquals(watchList.getIndex(2), found);

        found = watchList.findMusical("Wicked");
        assertEquals(null, found);
    }

    @Test
    void testGetMusicals() {
        watchList.addMusical("Hamilton", "Queen Elizabeth Theater", 50);
        watchList.addMusical("Come From Away", "Queen Elizabeth Theater", 60);
        watchList.addMusical("Kinky Boots", "Vogue Theater", 70);
        Musical m1 = watchList.getIndex(0);
        Musical m2 = watchList.getIndex(1);
        Musical m3 = watchList.getIndex(2);

        assertEquals(3, watchList.getMusicals().size());
        assertEquals(m1, watchList.getMusicals().get(0));
        assertEquals(m2, watchList.getMusicals().get(1));
        assertEquals(m3, watchList.getMusicals().get(2));
    }
}
