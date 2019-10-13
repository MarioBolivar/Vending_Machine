package VendingMachine;

import java.util.ArrayList;

/**
 *
 * @author Mario Bolivar
 *
 * This class handles encapsulating customer data and transaction calculations.
 *
 */
public class Customer {

    private ArrayList<Item> cart;
    private double funds;
    private double total;
    private double balance;

    //Default ctr
    public Customer() {
        cart = new ArrayList<>();
        balance = 0;
        total = 0;
    }

    /**
     * <h1>addItem</h1>
     * Add item to customer cart
     *
     * @param item
     */
    public void addItem(Item item) {
        cart.add(item);
        total += item.getPrice();
        balance = getBalance();
    }

    /**
     * <h1>addFunds</h1>
     * Set new customer funds
     *
     * @param funds
     */
    public void addFunds(double funds) {
        this.funds += funds;
        this.balance = funds - total;
    }

    /**
     * <h1>getFunds</h1>
     * Get customer funds
     *
     * @return double This returns customer funds
     */
    public double getFunds() {
        return funds;
    }

    /**
     * <h1>getBalance</h1>
     * Get customer balance
     *
     * @return double This returns customer balance
     */
    public double getBalance() {
        return funds - total;
    }

    /**
     * <h1>getCartSize</h1>
     * Get customer cart size
     *
     * @return int This returns customer cart size
     */
    public int getCartSize() {
        return cart.size();
    }

    /**
     * <h1>clear</h1>
     * Clear customer cart
     *
     */
    public void clearCustomer() {
        cart.clear();
        balance = 0;
        funds = 0;
        total = 0;
    }

    /**
     * <h1>getCartItem</h1>
     * Get cart item given index
     *
     * @param index
     * @return item This is the item in the customer cart given an index
     */
    public Item getCartItem(int index) {
        return cart.get(index);
    }
}
