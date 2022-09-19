package persistence;

import model.Musical;
import model.WatchList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonWriterTest.java
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WatchList wl = new WatchList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWatchList() {
        try {
            WatchList wl = new WatchList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWatchList.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWatchList.json");
            wl = reader.read();
            assertEquals(0, wl.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWatchList() {
        try {
            WatchList wl = new WatchList();
            wl.addMusical("Come from away", "Queen Elizabeth Theater", 110);
            wl.addMusical("Chicago", "Theater Under The Stars", 150);
            wl.addMusical("Phantom of the Opera", "The Stanley Industrial Alliance Stage", 200);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWatchList.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWatchList.json");
            wl = reader.read();
            List<Musical> musicals = wl.getMusicals();
            assertEquals(3, musicals.size());
            checkMusical("Come from away", "Queen Elizabeth Theater", 110, musicals.get(0));
            checkMusical("Chicago", "Theater Under The Stars", 150, musicals.get(1));
            checkMusical("Phantom of the Opera", "The Stanley Industrial Alliance Stage", 200,
                    musicals.get(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
