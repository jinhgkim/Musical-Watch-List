package persistence;

import model.Musical;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonTest.java
public class JsonTest {
    protected void checkMusical(String title, String theater, double ticket, Musical musical) {
        assertEquals(title, musical.getTitle());
        assertEquals(theater, musical.getTheater());
        assertEquals(ticket, musical.getTicket());

    }
}
