package VendingMachine;

import java.util.Scanner;

/**
 *
 * @author Mario Bolivar
 *
 * This class encapsulates all other classes to deliver the overall working
 * logic for the virtual vending machine
 *
 */
public class VendingMachine {

    private DataManager data;
    private Customer customer;

    //Default ctr
    public VendingMachine() {
        begin();
    }

    /**
     * <h1>begin</h1>
     * Method triggers the vending machine to begin processing.
     */
    public void begin() {

        /*
         * Creating file manager object..
         * Checks, files integrity, loads items into memory
         */
        data = new DataManager("items.db", "logEvents.txt");
        customer = new Customer();
        mainMenu();
    }

    private void mainMenu() {

        int selection = 0;
        Scanner systemInput = null;
        try { //Create console input stream
            systemInput = new Scanner(System.in);
        } catch (Exception ex) {
            data.writeLogEvent(">Error creating Console input stream with system!");
            data.writeLogEvent(ex.toString());
            System.exit(-1);
        }

        //Validating input
        while (true) {
            try {

                //Main menu
                clearConsole();
                System.out.println("\nMain Menu:");
                System.out.println("1. View items");
                System.out.println("2. Add item to cart");
                System.out.println("3. Add funds");
                System.out.println("4. Finalize transaction");
                System.out.println("5. Administrative login");
                System.out.println("6. Exit/cancel transaction");
                System.out.print("Please make a selection from the options above: ");
                int input = systemInput.nextInt();

                //If 0 > input > 6
                if (input < 1 || input > 6) {

                    throw new Exception("Invalid input in main menu."); //Throw an exception

                } else { //If input is valid, proceed.

                    if (input == 1) { //View Items
                        data.printItems();
                    } else if (input == 2) { //Add item to cart TODO
                        //Validating input
                        while (true) {
                            try {

                                //Item menu
                                clearConsole();
                                data.printItems();
                                System.out.println((data.getNumItems() + 1) + ". Exit to main menu");
                                System.out.print("Please make a selection from the options above: ");
                                input = systemInput.nextInt();

                                // 1 > input > number of available items -- invalid input
                                if (input < 1 || input > data.getNumItems() + 1) {

                                    throw new Exception("Invalid input in item selection."); //Throw an exception

                                } else { //Valid input
                                    if (input == data.getNumItems() + 1) { //If exit to main menu is selected
                                        break;
                                    } else { //Add item to cart

                                        //Decrement stock for item
                                        data.items.get(input - 1).setQuantity(data.items.get(input - 1).getQuantity() - 1);

                                        //Add item to cart
                                        customer.addItem(data.items.get(input - 1));

                                        System.out.println("Item added to shopping cart!");
                                        System.out.println("New Balance: $" + customer.getBalance());
                                    }
                                }

                            } catch (Exception ex) { //Catch invalid input, write to logs/screen and try again
                                System.out.println(">Invalid input. Please try again.");
                                data.writeLogEvent(">" + ex.toString());
                                sleep(data);
                                systemInput.nextLine();
                            }
                        }

                    } else if (input == 3) { //Add funds

                        // If funds already maxxed out
                        if (customer.getBalance() == 20) {
                            System.out.println("\n>Available funds maxxed out, cannot exceed $20.00");
                            sleep(data);
                            continue;
                        }

                        //Validating input
                        while (true) {
                            try {

                                System.out.println("Current balance: $" + customer.getBalance());
                                System.out.print("Please add funds in increments of $0.25: ");
                                double fundInput = systemInput.nextDouble();

                                // If fundInput < 0 or not in increments of $0.25
                                if (fundInput < 0 || fundInput % .25 != 0 || fundInput + customer.getBalance() > 20) {

                                    throw new Exception("Invalid input in fund input."); //Throw an exception

                                } else { //Valid input

                                    //Set funds and display new info
                                    customer.addFunds(fundInput);
                                    System.out.println("New balance: $" + customer.getBalance());
                                    break;
                                }

                            } catch (Exception ex) { //Catch invalid input, write to logs/screen and try again
                                System.out.println(">Invalid input. Please try again.");
                                data.writeLogEvent(">" + ex.toString());
                                sleep(data);
                                systemInput.nextLine();
                            }
                        }

                    } else if (input == 4) { //Finalize transaction

                        if (customer.getBalance() < 0) { //If not enough funds
                            System.out.println("\n>Insufficient funds.. Please add funds.");
                            sleep(data);
                            continue;
                        } else {
                            System.out.println("\n>Checking out...");
                            System.out.println("Dispending...");
                            for (int i = 0; i < customer.getCartSize(); i++) {
                                Item currentItem = customer.getCartItem(i);

                                System.out.println(currentItem.getTitle() + " " + currentItem.getDescription());
                            }

                            System.out.println("Dispensing change...: $" + customer.getBalance());
                            customer.clearCustomer();
                        }

                    } else if (input == 5) { //Administrative login TODO -- Did not have enough time to implement these features..
                        System.out.println("\nDid not have enough time to implement administrator features!");
                        System.out.println("Coming soon..");
                        continue;
                    } else { //Exit system.. Dispense change, empty cart etc..
                        System.out.println("Exiting..");
                        data.writeLogEvent(">Exiting...");
                        System.exit(0);
                    }
                }
            } catch (Exception ex) { //Catching any exceptions thrown and retrying user input retrieval.

                System.out.println(">Invalid input. Please try again.");
                data.writeLogEvent(">" + ex.toString());
                sleep(data);
                systemInput.nextLine();

            }
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void sleep(DataManager data) {
        //Sleep for 2 seconds to emphasize error
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex1) {
            data.writeLogEvent(ex1.toString());
        }
    }
}
