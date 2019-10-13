package VendingMachine;

/**
 *
 * @author Mario Bolivar
 *
 * This class encapsulates item properties and characteristics that are offered
 * by the vending machine.
 *
 */
public class Item {

    private String title;
    private String description;
    private double price;
    private int quantity;

    /**
     *
     * @param title
     * @param description
     * @param price
     * @param quantity
     */
    public Item(String title, String description, double price, int quantity) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * <h1>setTitle</h1>
     * Set new item title.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <h1>setDescription</h1>
     * Set new item description.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <h1>setPrice</h1>
     * Set new item price.
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * <h1>setQuantity</h1>
     * Set new item quantity.
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * <h1>getTitle</h1>
     * Get the item's title.
     *
     * @return String This returns the item's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * <h1>getDescription</h1>
     * Get the item's description.
     *
     * @return String This returns the item's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <h1>getPrice</h1>
     * Get the item's price.
     *
     * @return double This returns the item's price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * <h1>getQuantity</h1>
     * Get the item's quantity.
     *
     * @return int This returns the item's quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * <h1>toString</h1>
     *
     * @return String This returns a string containing all item properties for
     * display purposes.
     */
    @Override
    public String toString() {
        return "Item: " + title + "\nDescription: " + description + "\nPrice: $"
                + price + "\nQuantity: " + quantity;
    }
}
