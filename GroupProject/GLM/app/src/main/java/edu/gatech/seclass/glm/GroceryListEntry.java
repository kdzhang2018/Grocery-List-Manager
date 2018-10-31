package edu.gatech.seclass.glm;

/**
 * @author Kaidi Zhang
 */

public class GroceryListEntry {

    private int mId = 0;
    private Item item;
    private double quantity;
    private String unit;
    private boolean checked;


    /**
     *
     * @param i
     * @param q
     * @param u
     */
    public GroceryListEntry(Item i, double q, String u) {
        item = i;
        quantity = q;
        unit = u;
        checked = false;
    }

    public GroceryListEntry(Item i, double q, String u, boolean c) {
        item = i;
        quantity = q;
        unit = u;
        checked = c;
    }

    public GroceryListEntry(int Id, Item i, double q, String u) {
        mId = Id;
        item = i;
        quantity = q;
        unit = u;
        checked = false;
    }

    public GroceryListEntry(int Id, Item i, double q, String u, boolean c) {
        mId = Id;
        item = i;
        quantity = q;
        unit = u;
        checked = c;
    }

    /**
     * change the quantity to new quantity
     * @param q new quantity
     */
    public void changeQuantity(double q) {
        if (q == 0) throw new IllegalArgumentException();
        quantity = q;
    }

    /**
     * change unit to new unit
     * @param u new unit
     */
    public void changeUnit(String u) {
        if (u.equals("")) throw new IllegalArgumentException();
        unit = u;
    }

    /**
     * check the entry
     */
    public void checkItem() { checked = true;}

    /**
     * uncheck the entry
     */
    public void unCheckItem() { checked = false; }

    public int getId() { return mId; }
    public Item getItem() { return item; }
    public double getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public boolean getChecked() { return checked; }

}
