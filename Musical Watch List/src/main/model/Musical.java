package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a musical having a title, theater and the ticket price (in dollars)
public class Musical implements Writable {
    private String title;
    private String theater;
    private double ticket;

    // REQUIRES: title and theater has a non-zero length, ticket >= 0
    // EFFECTS: constructs a musical with the given title, theater and the ticket price
    public Musical(String title, String theater, double ticket) {
        this.title = title;
        this.theater = theater;
        this.ticket = ticket;
    }

    public String getTitle() {
        return title;
    }

    public String getTheater() {
        return theater;
    }

    public double getTicket() {
        return ticket;
    }

    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/model/Thingy.java
    // EFFECTS: returns a musical as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("theater", theater);
        json.put("ticket", ticket);
        return json;
    }
}
