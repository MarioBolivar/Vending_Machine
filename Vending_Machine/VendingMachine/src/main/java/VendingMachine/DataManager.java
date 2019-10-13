package VendingMachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mario Bolivar
 *
 * This class handles the files necessary for the program. This includes file
 * integrity checking, reading from and writing to files.
 *
 */
public class DataManager {

    private final String itemsDB;
    private final String logEvents;
    public ArrayList<Item> items;

    /**
     *
     * @param itemsDB
     * @param logEvents
     */
    DataManager(String itemsDB, String logEvents) {

        //Set class variables
        this.itemsDB = itemsDB;
        this.logEvents = logEvents;

        //Fill items arraylist from db
        loadItemDB();
    }

    /**
     *
     * <h1>checkItemDB</h1>
     * Method to check if items.db file integrity and that calls another method
     * to import data from the file if possible loadDB()
     *
     */
    public void loadItemDB() {

        //File variable that will be used to check if the file exists
        //and if it can be read from
        File inputFile = new File(itemsDB);

        //Checks if file exists
        if (inputFile.exists()) {

            //If file cannot be read from, display error and terminate program.
            if (!inputFile.canRead()) {

                //Write errors to log.
                writeLogEvent(">File \"" + itemsDB + "\" cannot be read from.");
                writeLogEvent(">Please fix this error and restart the program.");
                System.exit(-1);

            } else { //If file exists and can be read from, continue

                try {

                    //Creating Scanner object for input
                    Scanner input = new Scanner(inputFile);

                    //If item.db is empty, write error to log and terminate program
                    if (!input.hasNextLine()) {
                        writeLogEvent(">File \"" + itemsDB + "\" cannot be empty.");
                        writeLogEvent(">Please fill file \"" + itemsDB + "\" with data and restart the program.");
                        System.exit(-1);
                    }

                    loadItemDBHelper(input);

                } catch (FileNotFoundException ex) { //Catch exception and write errors to log

                    writeLogEvent(">Error: Program could not open \"" + itemsDB + "\"");
                    writeLogEvent(ex.toString());
                    System.exit(-1);
                }

            }

            //If file does not exist, display error message and terminate program
        } else {
            writeLogEvent(">File \"" + itemsDB + "\" does not exist.");
            writeLogEvent(">Please add the file to the directory and restart the program.");
            System.exit(-1);
        }

    }

    /**
     * <h1>loadDB</h1>
     * Method to import data from items.db
     *
     * Helper function to checkItemDB()
     *
     * @param input
     */
    private void loadItemDBHelper(Scanner input) {

        items = new ArrayList<>();

        //While items database has a next line
        while (input.hasNextLine()) {

            //Get current item and split by properties
            String current = input.nextLine();
            String itemProperties[] = current.split(";");

            //If line not parsed correctly, file integrity has been broken
            if (itemProperties.length != 5) {

                //Output error information to log and exit
                writeLogEvent(">File: \"" + itemsDB + "\" integrity has been broken.");
                writeLogEvent(">Please verify that file follows assumed convention (delimited by semicolon, ;):");
                writeLogEvent("><ItemTitle>;<ItemDescription>;<ItemPrice>;<ItemQuantity>");
                System.exit(-1);
            }

            //Item variables from split array -- for understandibility
            int itemIndex = 0;
            String itemTitle = itemProperties[1];
            String itemDescription = itemProperties[2];
            double itemPrice = 0;
            int itemQuantity = 0;

            try { //Try parsing itemPrice and quantity from string
                itemIndex = Integer.parseInt(itemProperties[0]);
                itemPrice = Double.parseDouble(itemProperties[3]);
                itemQuantity = Integer.parseInt(itemProperties[4]);
            } catch (NumberFormatException ex) {

                //If error parsing double/int values output error message and exit
                //Output error information to log and exit
                writeLogEvent(">File: \"" + itemsDB + "\" integrity has been broken.");
                writeLogEvent(">Around \"" + itemTitle + "\" entry there was an error parsing double/int.");
                writeLogEvent(">Please amend entry and restart the program.");
                System.exit(-1);
            }

            writeLogEvent(">Item read from: \"" + itemsDB + "\":");
            writeLogEvent(">" + new Item(itemIndex, itemTitle, itemDescription, itemPrice, itemQuantity).toString());
            items.add(new Item(itemIndex, itemTitle, itemDescription, itemPrice, itemQuantity));
        }

        writeLogEvent(">Finished loading from \"" + itemsDB + "\"");

        //Close Scanner stream
        input.close();
    }

    /**
     * <h1>writeLogEvent</h1>
     * The writeLogEvent method takes a given String and outputs it to log after
     * verifying log file integrity.
     *
     * @param logEvent
     */
    public void writeLogEvent(String logEvent) {

        //File variable that will be used to check if the file exists and can be read from
        File inputFile = new File(logEvents);

        //Checks if file exists
        if (inputFile.exists()) {

            //If file cannot be read from, display error and terminate program.
            if (!inputFile.canRead()) {
                System.out.println(">File \"" + logEvents + "\" cannot be read from.\n");
                System.out.println(">Please fix this error and restart the program.\n");
                System.exit(-1);

                //If file exists and can be read from, continue
            } else {

                try {
                    //PrintWriter object for writing to file
                    PrintWriter fileWrite = new PrintWriter(new FileOutputStream(inputFile, true));

                    //Write logEvent to logEvents.txt
                    fileWrite.println(LocalDateTime.now().toString() + " ::: " + logEvent);

                    //Close filestream
                    fileWrite.close();

                } catch (Exception ex) {
                    System.out.println(">Error: Program could not open \"" + logEvents + "\"\n");
                    System.out.println(ex.toString());
                    System.exit(-1);
                }
            }
        } else { //If logEvents.txt does not exist, create file and recall writeLogEvent method

            try {
                System.out.println(">File \"" + logEvents + "\" does not exist.");
                System.out.println(">Creating..");

                //Create logEvents.txt
                inputFile.createNewFile();
                System.out.println(">Done!");

                //Recall writeLogEvent
                writeLogEvent(logEvent);

            } catch (Exception ex) {

                System.out.println(">Error: Could not create \"" + logEvents + "\"\n");
                System.out.println(ex.toString());
                System.exit(-1);
            }
        }
    }

    /**
     * This method prints all items in loaded arraylist
     */
    public void printItems() {

        //Log events
        writeLogEvent(">Printing all items to console..");
        System.out.println("\nItem Selection:");
        //Iterating through items and printing out
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).toString());
        }

        writeLogEvent(">Done!");
    }

    /**
     * <h1>getNumItems</h1>
     *
     * @return int This returns the number of items sold in the vending machine
     */
    public int getNumItems() {
        return items.size();
    }
}
