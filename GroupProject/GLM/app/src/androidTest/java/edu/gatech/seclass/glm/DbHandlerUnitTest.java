package edu.gatech.seclass.glm;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * DB initialized with the following:
 * ItemTypes:
 *      1. bread, 2. dairy, 3. frozen food, 4. produce, 5. meat
 * Items:
 *      bread       - 1. sandwich loaf, 2. dinner rolls, 3. bagels
 *      dairy       - 1. cheese, 2. milk, 3. yogurt, 4. butter
 *      frozen food - 1. waffles, 2. ice cream, 3. toaster strudel
 *      produce     - 1. apple, 2. banana, 3. carambola, 4. parsnip
 *      meat        - 1. lunch meat, 2. chicken, 3. pork, 4. beef
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *
 * JUnit tests are named following the naming convention introduced by Roy Osherove.
 * @see <a href="https://www.petrikainulainen.net/programming/testing/writing-clean-tests-naming-matters/">JUnit Naming Conventions</a>
 */
@RunWith(AndroidJUnit4.class)
public class DbHandlerUnitTest {
    private DbHandler dbHandler;
    private Context appContext;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();

        dbHandler = new DbHandler(appContext);

    }

    @After
    public void tearDown() throws Exception {

        // sanity check in case test db entries are not deleted

        Item item = dbHandler.getItem("TestItem");
        if (item != null) {
            dbHandler.deleteItem(item);
        }

        GroceryList groceryList = dbHandler.getGroceryList("TestGroceryList");
        if (groceryList != null) {
            dbHandler.deleteGroceryList(groceryList);
        }

        dbHandler.close();

    }


    /*
    Misc Unit Tests
     */

    @Test
    public void useAppContext() throws Exception {
        assertEquals("edu.gatech.seclass.glm", appContext.getPackageName());
    }

    @Test
    public void databaseCreated() throws Exception {
        File dbFile = appContext.getDatabasePath(DbHandler.DATABASE_NAME);
        assertTrue(dbFile.exists());
    }


    /*
    ItemType Unit Tests
     */

    @Test
    public void getItemType_ItemTypeExists_RetrievesItemType() throws Exception {
        ItemType meat = dbHandler.getItemType("meat");
        assertEquals("meat", meat.getTypeName());
    }

    @Test
    public void getItemType_ItemTypeDoesntExist_ReturnsNull() throws Exception {
        ItemType foo = dbHandler.getItemType("foo");
        assertEquals(null, foo);
    }


    /*
    Item Unit Tests
     */

    @Test
    public void addItem_ItemDoesntExist_CreatesAndReturnsNewItem() throws Exception {
        Item croissant = dbHandler.addItem("TestItem", dbHandler.getItemType("bread"));
        assertEquals("TestItem", croissant.getItemName());

        // reset changes
        dbHandler.deleteItem(croissant);
    }

    @Test
    public void addItem_ItemAlreadyExists_ReturnsNull() throws Exception {
        Item bagels = dbHandler.addItem("bagels", dbHandler.getItemType("bread"));
        assertEquals(null, bagels);
    }

    @Test
    public void getItem_ItemExists_RetrievesItem() throws Exception {
        Item bagels = dbHandler.getItem("bagels");
        assertEquals("bagels", bagels.getItemName());
    }

    @Test
    public void getItem_ItemDoesntExist_ReturnsNull() throws Exception {
        Item foo = dbHandler.getItem("foo");
        assertEquals(null, foo);
    }

    @Test
    public void getItemsLike_ItemsLikeStringExists_ReturnsListWithItems() throws Exception {
        ArrayList<Item> items = new ArrayList<>(dbHandler.getItemsLike("bag"));
        assertTrue(!items.isEmpty());
    }

    @Test
    public void getItemsLike_ItemsLikeStringDontExist_ReturnsEmptyList() throws Exception {
        ArrayList<Item> items = new ArrayList<>(dbHandler.getItemsLike("No Items Match This String"));
        assertTrue(items.isEmpty());
    }

    @Test
    public void getAllItems_GivenItemTypeHasItems_ReturnsListWithItems() throws Exception {
        ArrayList<Item> items = new ArrayList<>(dbHandler.getAllItems(dbHandler.getItemType("bread")));
        assertTrue(!items.isEmpty());
    }

    @Test
    public void getItemsCount_GivenItemTypeExists_ReturnsPositiveInteger() throws Exception {
        int count = dbHandler.getItemsCount(dbHandler.getItemType("bread"));
        assertTrue(count > 0);
    }

    @Test
    public void updateItem_ItemExists_ReturnsOneAndItemIsUpdated() throws Exception {
        // get and modify item object
        Item bagels = dbHandler.getItem("bagels");
        bagels.setItemName("wheat bagels");

        // check update count
        int updateCount = dbHandler.updateItem(bagels);
        assertEquals(1, updateCount);

        // retrieve from DB again to check if updated
        Item updatedBagels = dbHandler.getItem(bagels.getId());
        assertEquals("wheat bagels", updatedBagels.getItemName());

        // reset changes
        bagels.setItemName("bagels");
        dbHandler.updateItem(bagels);
    }

    @Test
    public void deleteItem_ItemExists_ItemNoLongerExists() throws Exception {
        Item item = dbHandler.addItem("deletedItem", dbHandler.getItemType("bread"));

        dbHandler.deleteItem(item);
        Item deletedItem = dbHandler.getItem("deletedItem");
        assertEquals(null, deletedItem);
    }


    /*
    GroceryList Unit Tests
     */

    @Test
    public void addGroceryList_GroceryListDoesntExist_CreatesAndReturnsNewGroceryList() throws Exception {
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");
        assertEquals("TestGroceryList", groceryList.getName());

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

    @Test
    public void addGroceryList_GroceryListAlreadyExists_ReturnsNull() throws Exception {
        GroceryList holder = dbHandler.addGroceryList("TestGroceryList");

        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");
        assertEquals(null, groceryList);

        // reset changes
        dbHandler.deleteGroceryList(holder);
    }

    @Test
    public void getGroceryList_GroceryListExists_RetrievesGroceryList() throws Exception {
        dbHandler.addGroceryList("TestGroceryList");

        GroceryList groceryList = dbHandler.getGroceryList("TestGroceryList");
        assertEquals("TestGroceryList", groceryList.getName());

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

    @Test
    public void getGroceryList_GroceryListDoesntExist_ReturnsNull() throws Exception {
        GroceryList groceryList = dbHandler.getGroceryList("TestGroceryList");
        assertEquals(null, groceryList);
    }

    @Test
    public void getAllGroceryLists_GroceryListsExist_ReturnsListWithGroceryLists() throws Exception {
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");

        ArrayList<GroceryList> groceryLists = new ArrayList<>(dbHandler.getAllGroceryLists());
        assertTrue(!groceryLists.isEmpty());

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

    @Test
    public void getAllGroceryLists_NoGroceryListsExist_ReturnsEmptyList() throws Exception {
        ArrayList<GroceryList> groceryLists = new ArrayList<>(dbHandler.getAllGroceryLists());
        assertTrue(groceryLists.isEmpty());
    }

    @Test
    public void getGroceryListsCount_GroceryListsExist_ReturnsPositiveInteger() throws Exception {
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");

        int count = dbHandler.getGroceryListsCount();
        assertTrue(count > 0);

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

    @Test
    public void getGroceryListsCount_NoGroceryListsExist_ReturnsZero() throws Exception {
        int count = dbHandler.getGroceryListsCount();
        assertTrue(count == 0);
    }

    @Test
    public void updateGroceryList_GroceryListExists_ReturnsOneAndGroceryListIsUpdated() throws Exception {
        // get and modify GroceryList object
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");
        groceryList.rename("RenamedTestGroceryList");

        // check update count
        int updateCount = dbHandler.updateGroceryList(groceryList);
        assertEquals(1, updateCount);

        // retrieve from DB again to check if updated
        GroceryList updatedGroceryList = dbHandler.getGroceryList(groceryList.getId());
        assertEquals("RenamedTestGroceryList", updatedGroceryList.getName());

        // reset changes
        dbHandler.deleteGroceryList(updatedGroceryList);
    }

    @Test
    public void deleteGroceryList_GroceryListExists_GroceryListNoLongerExists() throws Exception {
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");

        dbHandler.deleteGroceryList(groceryList);
        GroceryList deletedGroceryList = dbHandler.getGroceryList(groceryList.getId());
        assertEquals(null, deletedGroceryList);
    }



    /*
    GroceryListEntry Unit Tests
     */

    @Test
    public void addGroceryListEntry_GroceryListEntryDoesntExist_CreatesAndReturnsNewGroceryListEntry() throws Exception {
        // setup
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");
        Item item = dbHandler.getItem("bagels");

        // test
        GroceryListEntry groceryListEntry = dbHandler.addGroceryListEntry(groceryList, item, 1.0, "lb");
        assertEquals(item, groceryListEntry.getItem());

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

    @Test
    public void addGroceryListEntry_GroceryListEntryExists_ReturnsNull() throws Exception {
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");
        Item item = dbHandler.getItem("bagels");
        dbHandler.addGroceryListEntry(groceryList, item, 1.0, "lb");

        GroceryListEntry groceryListEntry = dbHandler.addGroceryListEntry(groceryList, item, 1.0, "lb");
        assertEquals(null, groceryListEntry);

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

    @Test
    public void getAllGroceryListEntries_GroceryListEntriesExist_ReturnsListWithGroceryListEntries() throws Exception {
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");
        Item item = dbHandler.getItem("bagels");
        dbHandler.addGroceryListEntry(groceryList, item, 1.0, "lb");

        ArrayList<GroceryListEntry> groceryListEntries = new ArrayList<>(dbHandler.getAllGroceryListEntries(groceryList));
        assertTrue(!groceryListEntries.isEmpty());

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

    @Test
    public void getAllGroceryListEntries_NoGroceryListEntriesExist_ReturnsEmptyList() throws Exception {
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");

        ArrayList<GroceryListEntry> groceryListEntries = new ArrayList<>(dbHandler.getAllGroceryListEntries(groceryList));
        assertTrue(groceryListEntries.isEmpty());

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

    @Test
    public void getGroceryListEntriesCount_GroceryListEntriesExist_ReturnsPositiveInteger() throws Exception {
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");
        Item item = dbHandler.getItem("bagels");
        dbHandler.addGroceryListEntry(groceryList, item, 1.0, "lb");

        int count = dbHandler.getGroceryListEntriesCount(groceryList);
        assertTrue(count > 0);

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

    @Test
    public void getGroceryListEntriesCount_NoGroceryListEntriesExist_ReturnsZero() throws Exception {
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");

        int count = dbHandler.getGroceryListEntriesCount(groceryList);
        assertTrue(count == 0);

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

    @Test
    public void updateGroceryListEntry_GroceryListEntryExists_ReturnsOneAndGroceryListEntryIsUpdated() throws Exception {
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");
        Item item = dbHandler.getItem("bagels");
        GroceryListEntry groceryListEntry = dbHandler.addGroceryListEntry(groceryList, item, 1.0, "lb");

        groceryListEntry.changeQuantity(2.0);
        dbHandler.updateGroceryListEntry(groceryListEntry);
        assertEquals(2.0, groceryListEntry.getQuantity(), 0);

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

    @Test
    public void deleteGroceryListEntry_GroceryListEntryExists_GroceryListEntryIsDeleted() throws Exception {
        GroceryList groceryList = dbHandler.addGroceryList("TestGroceryList");
        Item item = dbHandler.getItem("bagels");
        GroceryListEntry groceryListEntry = dbHandler.addGroceryListEntry(groceryList, item, 1.0, "lb");

        dbHandler.deleteGroceryListEntry(groceryListEntry);
        GroceryListEntry deletedGroceryListEntry = dbHandler.getGroceryListEntry(groceryListEntry.getId());
        assertEquals(null, deletedGroceryListEntry);

        // reset changes
        dbHandler.deleteGroceryList(groceryList);
    }

}
