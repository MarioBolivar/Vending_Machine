package VendingMachine;

/**
 *
 * @author Mario Bolivar
 *
 * This program is a virtual, console simulation for a basic vending machine. We
 * will assume the vending machine contains drinks that are for sale.
 *
 */
public class Main {

    /**
     *
     * @param args
     *
     */
    public static void main(String[] args) {

        /*
         * Creating file manager object..
         * Checks, files integrity, loads items into memory
         */
        DataManager data = new DataManager("items.db", "logEvents.txt");

    }

}
