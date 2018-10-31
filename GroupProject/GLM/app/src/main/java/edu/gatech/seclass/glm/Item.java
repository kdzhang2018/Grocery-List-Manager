package edu.gatech.seclass.glm;

/**
 * Created by dhruv on 10/8/16.
 */

public class Item {

    private int Id = 0;
    private String itemName;
    private ItemType itemType;

    public Item(String itemName, ItemType itemType) {
        this.itemName = itemName;
        this.itemType = itemType;
    }

    public Item(int Id, String itemName, ItemType itemType) {
        this.Id = Id;
        this.itemName = itemName;
        this.itemType = itemType;
    }

    public int getId() { return Id; }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public ItemType getItemType() {
        return itemType;
    }
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
