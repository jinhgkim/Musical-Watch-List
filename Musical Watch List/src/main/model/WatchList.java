package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a watchlist having a list of musicals
public class WatchList implements Writable {
    private List<Musical> musicals;

    // EFFECTS: constructs an empty collection of musicals
    public WatchList() {
        this.musicals = new ArrayList<>();
    }

    // REQUIRES: title and theater has a non-zero length, ticket >= 0
    // MODIFIES: this
    // EFFECTS: adds a musical containing information like title, theater and ticket price to the watchlist
    public void addMusical(String title, String theater, double ticket) {
        this.musicals.add(new Musical(title, theater, ticket));
        EventLog.getInstance().logEvent(new Event("<" + title + ">" + " added to watchlist"));
    }

    // REQUIRES: watchlist is a non-empty list
    // MODIFIES: this
    // EFFECTS: deletes a musical from the watchlist
    public void removeMusical(Musical m) {
        musicals.remove(m);
        EventLog.getInstance().logEvent(new Event("<" + m.getTitle() + ">" + " removed from watchlist"));
    }

    // REQUIRES: watchlist is a non-empty list
    // EFFECTS: search the musical on the watchlist by its title given by the user
    public Musical findMusical(String title) {
        for (int i = 0; i < musicals.size(); i++) {
            if (title.equals(musicals.get(i).getTitle())) {
                return musicals.get(i);
            }
        }
        return null;
    }

    // EFFECTS: returns the list of musicals on the watchlist
    public List<Musical> getMusicals() {
        return musicals;
    }

    // EFFECTS: returns the size of the watchlist
    public int getSize() {
        return musicals.size();
    }

    // REQUIRES: 0 <= i < watchlist.getSize()
    // EFFECTS: returns the musical on the given index in the list of musicals
    public Musical getIndex(int i) {
        return musicals.get(i);
    }

    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/model/WorkRoom.java
    // EFFECTS: returns a watchlist as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("musicals", musicalsToJson());
        return json;
    }

    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/model/WorkRoom.java
    // EFFECTS: returns musicals in this watchlist as a JSON array
    private JSONArray musicalsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Musical m : musicals) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }
}
