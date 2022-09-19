package ui;

import model.Musical;
import model.WatchList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/ui/WorkRoomApp.java
// Musical watchlist application
public class WatchListApp {
    private static final String JSON_STORE = "./data/Watchlist.json";
    private Scanner input;
    private WatchList watchList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs the watchlist and runs application
    public WatchListApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        watchList = new WatchList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWatchList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    //https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    private void runWatchList() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    private void processCommand(String command) {
        if (command.equals("a")) {
            addMusical();
        } else if (command.equals("r")) {
            removeMusical();
        } else if (command.equals("v")) {
            printSelectedMusicalInfo();
        } else if (command.equals("p")) {
            printWatchList();
        } else if (command.equals("f")) {
            searchMusical();
        } else if (command.equals("s")) {
            saveWatchList();
        } else if (command.equals("l")) {
            loadWatchList();
        } else {
            System.out.println("Selection not valid.");
        }
    }

    // EFFECTS: displays menu of options to user
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add");
        System.out.println("\tr -> remove");
        System.out.println("\tv -> view details");
        System.out.println("\tp -> print watchlist");
        System.out.println("\tf -> find");
        System.out.println("\ts -> save watchlist to file");
        System.out.println("\tl -> load watchlist from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds a new musical with details about title, theater
    // and the ticket price to the watchlist
    private void addMusical() {
        input = new Scanner(System.in);
        System.out.println("Enter the title: ");
        String title = input.nextLine();

        System.out.println("Enter the theater: ");
        String theater = input.nextLine();

        System.out.println("Enter the ticket price: ");
        double ticket = input.nextDouble();

        System.out.println("Musical: " + title + ", " + theater + ", " + ticket
                + " has been added to the watchlist.");

        watchList.addMusical(title, theater, ticket);
    }

    // MODIFIES: this
    // EFFECTS: remove a musical from the watchlist
    private void removeMusical() {
        if (watchList.getSize() == 0) {
            System.out.println("Empty watchlist. Please add more before you delete.");
        } else {
            printWatchList();
            Musical m = selectMusical();

            System.out.println("Musical: " + m.getTitle() + ", " + m.getTheater() + ", " + m.getTicket()
                    + " has been removed from the watchlist.");

            watchList.removeMusical(m);
        }
    }

    // EFFECTS: prompts user to select a musical by their index
    // on the watchlist (index of the watchlist starts from 1) and returns it
    private Musical selectMusical() {
        input = new Scanner(System.in);
        int index;
        do {
            System.out.println("Enter the valid index of the musical.");
            int selection = input.nextInt();
            index = selection - 1;

        } while (index < 0 || index >= watchList.getSize());

        return watchList.getIndex(index);
    }

    // EFFECTS: prints a list of titles of the musicals on the watchlist
    private void printWatchList() {
        for (int i = 0; i < watchList.getSize(); i++) {
            int index = i + 1;
            System.out.println(index + ". " + watchList.getIndex(i).getTitle());
        }
    }

    // EFFECTS: prints info (title, theater, ticket price) of the selected musical to the screen
    private void printSelectedMusicalInfo() {
        if (watchList.getSize() == 0) {
            System.out.println("Empty watchlist. Please add more before you view details.");
        } else {
            printWatchList();
            Musical selected = selectMusical();
            System.out.println("Title: " + selected.getTitle());
            System.out.println("Theater: " + selected.getTheater());
            System.out.println("Ticket price: " + selected.getTicket());
        }
    }

    // EFFECTS: finds the musical by its title given by the user from the watchlist
    private void searchMusical() {
        if (watchList.getSize() == 0) {
            System.out.println("Empty watchlist. Please add more before you search.");
        } else {
            input = new Scanner(System.in);
            System.out.println("Enter the title of the musical: ");
            String title = input.nextLine();

            Musical found = watchList.findMusical(title);
            if (found != null) {
                System.out.println("Title: " + found.getTitle());
                System.out.println("Theater: " + found.getTheater());
                System.out.println("Ticket price: " + found.getTicket());
            } else {
                System.out.println("Given musical not found in the watchlist.");
            }
        }
    }

    // EFFECTS: saves the watchlist to file
    private void saveWatchList() {
        try {
            jsonWriter.open();
            jsonWriter.write(watchList);
            jsonWriter.close();
            System.out.println("Saved the watchlist to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads watchlist from file
    private void loadWatchList() {
        try {
            watchList = jsonReader.read();
            System.out.println("Loaded the watchlist from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}