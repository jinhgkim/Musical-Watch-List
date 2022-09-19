package persistence;

import model.Musical;
import model.WatchList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonReaderTest.java
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WatchList wl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWatchList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWatchList.json");
        try {
            WatchList wl = reader.read();
            assertEquals(0, wl.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWatchList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWatchList.json");
        try {
            WatchList wl = reader.read();
            List<Musical> musicals = wl.getMusicals();
            assertEquals(3, musicals.size());
            checkMusical("The Phantom of the Opera", "Queen Elizabeth Theater", 120,
                    musicals.get(0));
            checkMusical("The Lion King", "Theater Under The Stars", 150,
                    musicals.get(1));
            checkMusical("Kinky Boots", "The Stanley Industrial Alliance Stage", 200,
                    musicals.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
