package edu.gatech.seclass.glm;
/**
 * Created by rehlers3
 */
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GroceryListUnitTest {
    private GroceryList groceryList;

    @Before
    public void setUp() throws Exception {
        //setup GroceryListEntry list
        ArrayList<GroceryListEntry> entries = new ArrayList<>();
        ItemType type = new ItemType("fruit");
        Item item = new Item("apple", type);
        GroceryListEntry entry = new GroceryListEntry(item, 1.0, "lb");
        entries.add(entry);

        //setup groceryList
        groceryList = new GroceryList("Test List Name", entries);
    }

    @After
    public void tearDown() throws Exception {
        //empty
    }

    // test that renaming a groceryList works
    @Test
    public void groceryListRenameWorks() throws Exception {
        groceryList.rename("New List Name");
        assertEquals(groceryList.getName(), "New List Name");
    }

    // Test that adding an entry to grocery list works
    @Test
    public void groceryListAddEntryWorks() throws Exception {
        ItemType type = new ItemType("fruit");
        Item item = new Item("orange", type);
        GroceryListEntry groceryListEntry = new GroceryListEntry(item, 1.0, "lb");
        groceryList.addEntry(groceryListEntry);
        ArrayList<GroceryListEntry> entries = groceryList.getEntries();
        assertTrue(entries.contains(groceryListEntry));  // test that entry was added
    }

    // Test that delete an entry from grocery list works
    @Test
    public void groceryListDeleteEntryWorks() throws Exception {
        ItemType type = new ItemType("fruit");
        Item item = new Item("peach", type);
        GroceryListEntry groceryListEntry = new GroceryListEntry(item, 1.0, "lb");
        groceryList.addEntry(groceryListEntry);
        ArrayList<GroceryListEntry> entries = groceryList.getEntries();
        assertTrue(entries.contains(groceryListEntry));  // test that entry was added
        groceryList.deleteEntry(groceryListEntry);
        assertFalse(entries.contains(groceryListEntry));  // test that entry no longer exists
    }

}