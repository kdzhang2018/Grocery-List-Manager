package edu.gatech.seclass.glm;

/**
 * Created by rehlers3
 */

import java.util.ArrayList;

/**
 * GroceryList class
 *
 * Contains definition for a GroceryList object
 */

public class GroceryList {
    private int mId = 0;
    private String mName;
    private ArrayList<GroceryListEntry> mEntries;

    /**
     * constructor for GroceryList
     * @param name the string name of the grocery list
     * @param entries ArrayList of GroceryListEntry objects
     */
    public GroceryList(String name, ArrayList<GroceryListEntry> entries) {
        mName = name;
        mEntries = entries;
    }

    public GroceryList(int Id, String name, ArrayList<GroceryListEntry> entries) {
        mId = Id;
        mName = name;
        mEntries = entries;
    }

    /**
     * addEntry
     *
     * add the specified groceryListEntry object to the list of entries
     * @param e GroceryListEntry object to add
     */
    public void addEntry(GroceryListEntry e) {
        mEntries.add(e);
    }

    /**
     * deleteEntry
     *
     * delete the specified groceryListEntry object from the list of entries
     * @param e GroceryListEntry object to delete
     */
    public void deleteEntry(GroceryListEntry e) {
        mEntries.remove(e);
    }

    /**
     * clearAllCheckMarks
     *
     * iterates over every GroceryListEntry in entries and runs unCheckItem method
     */
    public void clearAllCheckMarks() {
        for(GroceryListEntry e: mEntries) {
            //TODO: once GroceryListEntry is done -
            e.unCheckItem();
        }
    }

    /**
     * rename
     *
     * renames the list
     * @param newName new name of list
     */
    public void rename(String newName) {
        mName = newName;
    }

    public String getName() { return mName; }
    public int getId() { return mId; }
    public ArrayList<GroceryListEntry> getEntries() { return mEntries; }
}
