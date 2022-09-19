package ui;

import model.Event;
import model.EventLog;
import model.Musical;
import model.WatchList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// Represents application's main window frame.
public class WatchListUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/Watchlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private WatchList watchList;
    private JButton addButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton loadButton;
    private JTextField titleField;
    private JTextField theaterField;
    private JTextField ticketField;
    private JLabel titleLabel;
    private JLabel theaterLabel;
    private JLabel ticketLabel;
    private Container contentPane;
    private JTable musicalTable;
    private JScrollPane scrollPane;
    private JPanel buttonPane;
    private JPanel inputPane;
    private JPanel imagePane;
    private JPanel inputAndButtonPane;
    private DefaultTableModel tableModel;
    private ImageIcon icon;

    // EFFECTS: constructs a watchlist frame with an empty watchlist
    public WatchListUI() {
        super("Musical Watchlist");
        this.setLayout(new BorderLayout());
        this.setSize(1100, 600);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // https://stackoverflow.com/questions/60516720/java-how-to-print-message-when-a-jframe-is-closed
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });

        watchList = new WatchList();
        buttonPane = new JPanel();
        inputPane = new JPanel();
        imagePane = new JPanel();
        inputAndButtonPane = new JPanel();
        contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        initialize();

        contentPane.add(inputAndButtonPane, BorderLayout.NORTH);
        contentPane.add(imagePane, BorderLayout.CENTER);
        contentPane.add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    // EFFECTS: prints the log
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    // EFFECTS: populates create methods for fields, buttons, table, and image to be used in JFrame
    public void initialize() {
        createTitleField();
        createTheaterField();
        createTicketField();

        createAddButton();
        createDeleteButton();
        createSaveButton();
        createLoadButton();

        createTable();
        createImage();
        createInputAndButtonPane();
    }

    // MODIFIES: this
    // EFFECTS: adds input pane and button pane into a single pane
    public void createInputAndButtonPane() {
        inputAndButtonPane.add(inputPane);
        inputAndButtonPane.add(buttonPane);
    }

    // MODIFIES: this
    // EFFECTS: adds an image to the imagePane
    public void createImage() {
        icon = new ImageIcon("./data/chicago.jpeg");
        Image scaled = icon.getImage().getScaledInstance(400, 100, Image.SCALE_DEFAULT);
        icon.setImage(scaled);
        imagePane.add(new JLabel(icon));
    }

    // MODIFIES: this
    // EFFECTS: creates a JTable that stores and displays the musicals in the watchlist
    public void createTable() {
        String[] columns = {"Title", "Theater", "Ticket price"};
        tableModel = new DefaultTableModel(columns, 0);
        musicalTable = new JTable(tableModel);
        musicalTable.getTableHeader().setOpaque(false);

        scrollPane = new JScrollPane(musicalTable);
        musicalTable.setFillsViewportHeight(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a button that adds a musical and
    // add this button to the button pane
    public void createAddButton() {
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        buttonPane.add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: creates a button that deletes a musical and
    // add this button to the button pane
    public void createDeleteButton() {
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        buttonPane.add(deleteButton);
    }

    // MODIFIES: this
    // EFFECTS: creates a button that saves the watchlist and
    // add this button to the button pane
    public void createSaveButton() {
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        buttonPane.add(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: creates a button that loads the watchlist from a file and
    // add this button to the button pane
    public void createLoadButton() {
        loadButton = new JButton("Load");
        loadButton.addActionListener(this);
        buttonPane.add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: creates a field where user can type in the title
    // and add this field to the input pane
    public void createTitleField() {
        titleLabel = new JLabel("Title: ");
        titleField = new JTextField(15);

        inputPane.add(titleLabel);
        inputPane.add(titleField);
    }

    // MODIFIES: this
    // EFFECTS: creates a field where user can type in the theater
    // and add this field to the input pane
    public void createTheaterField() {
        theaterLabel = new JLabel("Theater: ");
        theaterField = new JTextField(15);

        inputPane.add(theaterLabel);
        inputPane.add(theaterField);
    }

    // MODIFIES: this
    // EFFECTS: creates a field where user can type in the ticket price
    // and add this field to the input pane
    public void createTicketField() {
        ticketLabel = new JLabel("Ticket: ");
        ticketField = new JTextField(15);

        inputPane.add(ticketLabel);
        inputPane.add(ticketField);
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.addButton) {
            this.addMusicalToWatchlist();
        } else if (e.getSource() == this.deleteButton) {
            this.deleteMusicalFromWatchlist();
        } else if (e.getSource() == this.saveButton) {
            this.saveCurrentWatchList();
        } else if (e.getSource() == this.loadButton) {
            this.loadFromWatchList(tableModel);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a musical's title, theater, and ticket price to the JTable,
    // and also adds it to the watchList
    public void addMusicalToWatchlist() {
        if (!titleField.getText().equals("")) {
            tableModel.addRow(new Object[]{
                    titleField.getText(),
                    theaterField.getText(),
                    ticketField.getText()});

            watchList.addMusical(
                    titleField.getText(),
                    theaterField.getText(),
                    Double.parseDouble(ticketField.getText()));

            titleField.setText("");
            theaterField.setText("");
            ticketField.setText("");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a musical in a selected row of the JTable
    // and also deletes it from the file
    public void deleteMusicalFromWatchlist() {
        if (musicalTable.getSelectedRow() != -1) {
            String title = (String) tableModel.getValueAt(musicalTable.getSelectedRow(), 0);
            Musical m = watchList.findMusical(title);
            watchList.removeMusical(m);
            tableModel.removeRow(musicalTable.getSelectedRow());
        }
    }

    // EFFECTS: saves current watchlist to a file
    public void saveCurrentWatchList() {
        jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
            jsonWriter.write(watchList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Failed to save this data to a file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: clears current watchlist and loads saved watchlist from a file
    public void loadFromWatchList(DefaultTableModel model) {
        jsonReader = new JsonReader(JSON_STORE);
        tableModel.setRowCount(0);
        try {
            watchList = jsonReader.read();
            List<Musical> musicals = watchList.getMusicals();
            for (Musical m : musicals) {
                model.addRow(new Object[]{m.getTitle(), m.getTheater(), m.getTicket()});
            }
        } catch (IOException e) {
            System.out.println("Failed to load data from a file.");
        }
    }
}
