package edu.gatech.seclass.glm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.glm.GLMContract.*;

/**
 * This class creates and interfaces with the SQLite DB, exposing all necessary queries as methods,
 *  thus acting as an abstraction layer between the DB and the rest of the code.
 *
 * @author Travis Meares
 */

public class DbHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "GroceryListManager.db";

    /**
     * Constructor for DbHandler, will initialize SQLite DB
     *
     * @param   context application context
     */
    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param   db the database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DbItemType.QUERY_CREATE_TABLE);
        db.execSQL(DbItem.QUERY_CREATE_TABLE);
        db.execSQL(DbGroceryList.QUERY_CREATE_TABLE);
        db.execSQL(DbGroceryListEntry.QUERY_CREATE_TABLE);

        ItemType bread = initItemType(db, "bread");
        ItemType dairy = initItemType(db, "dairy");
        ItemType frozenFood = initItemType(db, "frozen food");
        ItemType produce = initItemType(db, "produce");
        ItemType meat = initItemType(db, "meat");

        initItem(db, "sandwich loaf", bread);
        initItem(db, "dinner rolls", bread);
        initItem(db, "bagels", bread);

        initItem(db, "cheese", dairy);
        initItem(db, "milk", dairy);
        initItem(db, "yogurt", dairy);
        initItem(db, "butter", dairy);

        initItem(db, "waffles", frozenFood);
        initItem(db, "ice cream", frozenFood);
        initItem(db, "toaster strudel", frozenFood);

        initItem(db, "apple", produce);
        initItem(db, "banana", produce);
        initItem(db, "carambola", produce);
        initItem(db, "parsnip", produce);

        initItem(db, "lunch meat", meat);
        initItem(db, "chicken", meat);
        initItem(db, "pork", meat);
        initItem(db, "beef", meat);

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * @param   db         the database
     * @param   oldVersion the old database version
     * @param   newVersion the new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DbItemType.QUERY_DROP_TABLE);
        db.execSQL(DbItem.QUERY_DROP_TABLE);
        db.execSQL(DbGroceryList.QUERY_DROP_TABLE);
        db.execSQL(DbGroceryListEntry.QUERY_DROP_TABLE);

        onCreate(db);

    }

    /**
     * Called when the database is opened.
     *
     * @param   db the database
     */
    @Override
    public void onOpen(SQLiteDatabase db) {

        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");

    }



    /********************************************************************************************
     *********************************** "grocery_list" table ***********************************
     ********************************************************************************************/

    /**
     * Adds a GroceryList to the database and returns the GroceryList as an object.
     *
     * @param   name the name of the new GroceryList
     * @return  the newly persisted GroceryList
     */
    public GroceryList addGroceryList(String name) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbGroceryList.COLUMN_NAME_NAME, name);

        int id = (int) db.insert(DbGroceryList.TABLE_NAME, null, values);

        db.close();

        return id > 0 ? new GroceryList(id, name, new ArrayList<GroceryListEntry>()) : null;

    }

    /**
     * Gets a GroceryList from the database.
     *
     * @param   id the id of the desired GroceryList
     * @return  the GroceryList for the given id
     */
    public GroceryList getGroceryList(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT *"
                + " FROM " + DbGroceryList.TABLE_NAME
                + " WHERE " + DbGroceryList._ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if(!c.moveToFirst()) {
            c.close(); db.close();
            return null;
        }

        GroceryList groceryList = new GroceryList(
                id,
                c.getString(c.getColumnIndex(DbGroceryList.COLUMN_NAME_NAME)),
                new ArrayList<>(getAllGroceryListEntries(id))
        );

        c.close(); db.close();

        return groceryList;

    }

    /**
     * Gets a GroceryList from the database.
     *
     * @param   name the name of the desired GroceryList
     * @return  the GroceryList with the given name
     */
    public GroceryList getGroceryList(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT *"
                + " FROM " + DbGroceryList.TABLE_NAME
                + " WHERE " + DbGroceryList.COLUMN_NAME_NAME + " = '" + name + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if(!c.moveToFirst()) {
            c.close(); db.close();
            return null;
        }

        GroceryList groceryList = new GroceryList(
                c.getInt(c.getColumnIndex(DbGroceryList._ID)),
                name,
                new ArrayList<>(getAllGroceryListEntries(c.getInt(c.getColumnIndex(DbGroceryList._ID))))
        );

        c.close(); db.close();

        return groceryList;

    }

    /**
     * Gets a list of all the GroceryLists in the database.
     *
     * @return  a list of all persisted GroceryLists
     */
    public List<GroceryList> getAllGroceryLists() {

        List<GroceryList> groceryLists = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DbGroceryList.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                groceryLists.add(new GroceryList(
                        c.getInt(c.getColumnIndex(DbGroceryList._ID)),
                        c.getString(c.getColumnIndex(DbGroceryList.COLUMN_NAME_NAME)),
                        new ArrayList<>(getAllGroceryListEntries(c.getInt(c.getColumnIndex(DbGroceryList._ID))))
                ));
            } while (c.moveToNext());
        }

        c.close(); db.close();

        return groceryLists;

    }

    /**
     * Gets a count of the number of GroceryLists in the database.
     *
     * @return  the number of persisted GroceryLists
     */
    public int getGroceryListsCount() {

        String selectQuery = "SELECT * FROM " + DbGroceryList.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        int count = c.getCount();

        c.close(); db.close();

        return count;

    }

    /**
     * Updates a GroceryList in the database.
     *
     * @param   groceryList the GroceryList to be updated
     * @return  the number of rows updated
     */
    public int updateGroceryList(GroceryList groceryList) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbGroceryList.COLUMN_NAME_NAME, groceryList.getName());

        int numUpdatedRows = db.update(
                DbGroceryList.TABLE_NAME,
                values,
                DbGroceryList._ID + " = ?",
                new String[]{String.valueOf(groceryList.getId())}
        );

        db.close();

        return numUpdatedRows;

    }

    /**
     * Deletes a GroceryList from the database.
     *
     * @param   groceryList the GroceryList to be deleted
     */
    public void deleteGroceryList(GroceryList groceryList) {

        deleteGroceryList(groceryList.getId());

    }

    /**
     * Deletes a GroceryList from the database.
     *
     * @param   id the id of the GroceryList to be deleted
     */
    public void deleteGroceryList(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                DbGroceryList.TABLE_NAME,
                DbGroceryList._ID + " = ?",
                new String[]{ String.valueOf(id) }
        );

        db.close();

    }



    /********************************************************************************************
     ******************************** "grocery_list_entry" table ********************************
     ********************************************************************************************/

    /**
     * Adds a GroceryListEntry to the database.
     *
     * @param   groceryList the GroceryList that contains the entry
     * @param   item the item of the entry
     * @param   quantity the quantity of the item needed
     * @param   unit the unit for the quantity
     * @return  the newly created GroceryListEntry
     */
    public GroceryListEntry addGroceryListEntry(GroceryList groceryList, Item item, double quantity, String unit) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbGroceryListEntry.COLUMN_NAME_GROCERY_LIST_ID, groceryList.getId());
        values.put(DbGroceryListEntry.COLUMN_NAME_ITEM_ID, item.getId());
        values.put(DbGroceryListEntry.COLUMN_NAME_QUANTITY, quantity);
        values.put(DbGroceryListEntry.COLUMN_NAME_UNIT, unit);
        values.put(DbGroceryListEntry.COLUMN_NAME_IS_CHECKED, 0);

        int id = (int) db.insert(DbGroceryListEntry.TABLE_NAME, null, values);

        db.close();

        return id > 0 ? new GroceryListEntry(id, item, quantity, unit) : null;

    }

    /**
     * Gets a GroceryListEntry from the database.
     *
     * @param   id the id of the desired GroceryListEntry
     * @return  the persisted GroceryListEntry for the given id
     */
    public GroceryListEntry getGroceryListEntry(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT *"
                + " FROM " + DbGroceryListEntry.TABLE_NAME
                + " WHERE " + DbGroceryListEntry._ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if(!c.moveToFirst()) {
            c.close(); db.close();
            return null;
        }

        GroceryListEntry groceryList = new GroceryListEntry(
                id,
                getItem(c.getInt(c.getColumnIndex(DbGroceryListEntry.COLUMN_NAME_ITEM_ID))),
                c.getDouble(c.getColumnIndex(DbGroceryListEntry.COLUMN_NAME_QUANTITY)),
                c.getString(c.getColumnIndex(DbGroceryListEntry.COLUMN_NAME_UNIT)),
                (c.getInt(c.getColumnIndex(DbGroceryListEntry.COLUMN_NAME_IS_CHECKED)) != 0)
        );

        c.close(); db.close();

        return groceryList;


    }

    /**
     * Gets a list of all of the GroceryListEntries for a given GroceryList in the database
     *
     * @return  a list of all persisted GroceryListEntries for a specific GroceryList
     */
    public List<GroceryListEntry> getAllGroceryListEntries(GroceryList groceryList) {

        return getAllGroceryListEntries(groceryList.getId());

    }

    /**
     * Gets a list of all of the GroceryListEntries for a given GroceryList's id in the database
     *
     * @return  a list of all persisted GroceryListEntries for a specific GroceryList
     */
    public List<GroceryListEntry> getAllGroceryListEntries(int groceryListId) {

        List<GroceryListEntry> groceryListEntries = new ArrayList<>();

        String selectQuery = "SELECT e.*, i." + DbItem.COLUMN_NAME_NAME + " AS item_name, t." + DbItemType.COLUMN_NAME_NAME + " AS item_type_name"
                + " FROM " + DbGroceryListEntry.TABLE_NAME + " e"
                + " INNER JOIN " + DbItem.TABLE_NAME + " i ON i." + DbItem._ID + " = e." + DbGroceryListEntry.COLUMN_NAME_ITEM_ID
                + " INNER JOIN " + DbItemType.TABLE_NAME + " t ON t." + DbItemType._ID + " = i." + DbItem.COLUMN_NAME_ITEM_TYPE_ID
                + " WHERE " + DbGroceryListEntry.COLUMN_NAME_GROCERY_LIST_ID + " = " + groceryListId
                + " ORDER BY t." + DbItemType.COLUMN_NAME_NAME + ", i." + DbItem.COLUMN_NAME_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                groceryListEntries.add(new GroceryListEntry(
                        c.getInt(c.getColumnIndex(DbGroceryListEntry._ID)),
                        getItem(c.getInt(c.getColumnIndex(DbGroceryListEntry.COLUMN_NAME_ITEM_ID))),
                        c.getDouble(c.getColumnIndex(DbGroceryListEntry.COLUMN_NAME_QUANTITY)),
                        c.getString(c.getColumnIndex(DbGroceryListEntry.COLUMN_NAME_UNIT)),
                        (c.getInt(c.getColumnIndex(DbGroceryListEntry.COLUMN_NAME_IS_CHECKED)) != 0)
                ));
            } while (c.moveToNext());
        }

        c.close(); db.close();

        return groceryListEntries;

    }

    /**
     * Gets a count of the number of GroceryListEntries for a given GroceryList in the database.
     *
     * @return  the number of persisted GroceryListEntries for a specific GroceryList
     */
    public int getGroceryListEntriesCount(GroceryList groceryList) {

        return getGroceryListEntriesCount(groceryList.getId());

    }

    /**
     * Gets a count of the number of GroceryListEntries for a given GroceryList's id in the
     * database.
     *
     * @return  the number of persisted GroceryListEntries for a specific GroceryList
     */
    public int getGroceryListEntriesCount(int groceryListId) {

        String selectQuery = "SELECT * "
                + " FROM " + DbGroceryListEntry.TABLE_NAME
                + " WHERE " + DbGroceryListEntry.COLUMN_NAME_GROCERY_LIST_ID + " = " + groceryListId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        int count = c.getCount();

        c.close(); db.close();

        return count;

    }

    /**
     * Updates a GroceryListEntry in the database.
     *
     * @param   groceryListEntry the GroceryListEntry to be updated
     * @return  the number of rows successfully updated
     */
    public int updateGroceryListEntry(GroceryListEntry groceryListEntry) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbGroceryListEntry.COLUMN_NAME_QUANTITY, groceryListEntry.getQuantity());
        values.put(DbGroceryListEntry.COLUMN_NAME_UNIT, groceryListEntry.getUnit());
        values.put(DbGroceryListEntry.COLUMN_NAME_IS_CHECKED, groceryListEntry.getChecked());

        int numUpdatedRows = db.update(
                DbGroceryListEntry.TABLE_NAME,
                values,
                DbGroceryListEntry._ID + " = ?",
                new String[]{ String.valueOf(groceryListEntry.getId()) }
        );

        db.close();

        return numUpdatedRows;

    }

    /**
     * Deletes a GroceryListEntry from the database.
     *
     * @param   groceryListEntry the groceryListEntry to be deleted
     */
    public void deleteGroceryListEntry(GroceryListEntry groceryListEntry) {

        deleteGroceryListEntry(groceryListEntry.getId());

    }

    /**
     * Deletes a GroceryListEntry from the database.
     *
     * @param   id the groceryListEntry to be deleted
     */
    public void deleteGroceryListEntry(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                DbGroceryListEntry.TABLE_NAME,
                DbGroceryListEntry._ID + " = ?",
                new String[]{ String.valueOf(id) }
        );

        db.close();

    }



    /********************************************************************************************
     *************************************** "item" table ***************************************
     ********************************************************************************************/

    /**
     * Adds an Item to the database.
     *
     * @param   name the name of the new item
     * @param   itemType the type of the item
     * @return  the newly created item
     */
    public Item addItem(String name, ItemType itemType) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbItem.COLUMN_NAME_NAME, name);
        values.put(DbItem.COLUMN_NAME_ITEM_TYPE_ID, itemType.getId());

        int id = (int) db.insert(DbItem.TABLE_NAME, null, values);

        db.close();

        return id > 0 ? new Item(id, name, itemType) : null;

    }

    /**
     * Gets an Item from the database.
     *
     * @param   id the id of the desired Item
     * @return  the persisted Item for the given id
     */
    public Item getItem(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT *"
                + " FROM " + DbItem.TABLE_NAME
                + " WHERE " + DbItem._ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if(!c.moveToFirst()) {
            c.close(); db.close();
            return null;
        }

        Item item = new Item(
                id,
                c.getString(c.getColumnIndex(DbItem.COLUMN_NAME_NAME)),
                getItemType(c.getInt(c.getColumnIndex(DbItem.COLUMN_NAME_ITEM_TYPE_ID)))
        );

        c.close(); db.close();

        return item;

    }

    /**
     * Gets an Item from the database.
     *
     * @param   name the name of the desired Item
     * @return  the persisted Item with the given name
     */
    public Item getItem(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT *"
                + " FROM " + DbItem.TABLE_NAME
                + " WHERE " + DbItem.COLUMN_NAME_NAME + " = '" + name + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if(!c.moveToFirst()) {
            c.close(); db.close();
            return null;
        }

        Item item = new Item(
                c.getInt(c.getColumnIndex(DbItem._ID)),
                name,
                getItemType(c.getInt(c.getColumnIndex(DbItem.COLUMN_NAME_ITEM_TYPE_ID)))
        );

        c.close(); db.close();

        return item;

    }

    /**
     * Gets an Item from the database.
     *
     * @param   name the name of the desired Item
     * @return  the persisted Item with the given name
     */
    public List<Item> getItemsLike(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT *"
                + " FROM " + DbItem.TABLE_NAME
                + " WHERE " + DbItem.COLUMN_NAME_NAME + " LIKE '%" + name + "%'"
                + " ORDER BY " + DbItem.COLUMN_NAME_ITEM_TYPE_ID + ", " +DbItem.COLUMN_NAME_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        List<Item> itemsLike = new ArrayList<>();

        if(!c.moveToFirst()) {
            c.close(); db.close();
            return itemsLike;
        }

        do {
            itemsLike.add(new Item(c.getInt(c.getColumnIndex(DbItem._ID)),
                    c.getString(c.getColumnIndex(DbItem.COLUMN_NAME_NAME)),
                    getItemType(c.getInt(c.getColumnIndex(DbItem.COLUMN_NAME_ITEM_TYPE_ID)))));
        } while (c.moveToNext());

        c.close(); db.close();

        return itemsLike;

    }

    /**
     * Gets a list of all Items from the database.
     *
     * @return  a list of all persisted Items
     */
    public List<Item> getAllItems() {

        List<Item> items = new ArrayList<>();

        String selectQuery = "SELECT * "
                + " FROM " + DbItem.TABLE_NAME
                + " ORDER BY " + DbItem.COLUMN_NAME_NAME + " ASC";;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                items.add(new Item(
                        c.getInt(c.getColumnIndex(DbItem._ID)),
                        c.getString(c.getColumnIndex(DbItem.COLUMN_NAME_NAME)),
                        getItemType(c.getInt(c.getColumnIndex(DbItem.COLUMN_NAME_ITEM_TYPE_ID)))
                ));
            } while (c.moveToNext());
        }

        c.close(); db.close();

        return items;

    }

    /**
     * Gets a list of all Items of a given ItemType from the database.
     *
     * @param   itemType the type of the desired item list
     * @return  a list of all persisted Items of a given ItemType
     */
    public List<Item> getAllItems(ItemType itemType) {

        List<Item> items = new ArrayList<>();

        String selectQuery = "SELECT * "
                + " FROM " + DbItem.TABLE_NAME
                + " WHERE " + DbItem.COLUMN_NAME_ITEM_TYPE_ID + " = " + itemType.getId()
                + " ORDER BY " + DbItem.COLUMN_NAME_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                items.add(new Item(
                        c.getInt(c.getColumnIndex(DbItem._ID)),
                        c.getString(c.getColumnIndex(DbItem.COLUMN_NAME_NAME)),
                        itemType
                ));
            } while (c.moveToNext());
        }

        c.close(); db.close();

        return items;

    }


    /**
     * Gets a list of all Items of a given ItemType from the database.
     *
     * @param   itemTypeId the id for the type of the desired items
     * @return  a list of all persisted Items of a given ItemType's id
     */
    public List<Item> getAllItems(int itemTypeId) {

        return getAllItems(getItemType(itemTypeId));

    }

    /**
     * Gets a count of the number of Items in the database.
     *
     * @return  the number of persisted Items
     */
    public int getItemsCount() {

        String selectQuery = "SELECT * FROM " + DbItem.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        int count = c.getCount();

        c.close(); db.close();

        return count;

    }

    /**
     * Gets a count of the number of Items of a given ItemType in the database.
     *
     * @param   itemType the type of the desired item count
     * @return  the number of persisted Items of a given ItemType
     */
    public int getItemsCount(ItemType itemType) {

        return getItemsCount(itemType.getId());

    }

    /**
     * Gets a count of the number of Items of a given ItemType in the database.
     *
     * @param   itemTypeId the id of the type of the desired item count
     * @return  the number of persisted Items of a given ItemType
     */
    public int getItemsCount(int itemTypeId) {

        String selectQuery = "SELECT * "
                + " FROM " + DbItem.TABLE_NAME
                + " WHERE " + DbItem.COLUMN_NAME_ITEM_TYPE_ID + " = " + itemTypeId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        int count = c.getCount();

        c.close(); db.close();

        return count;

    }

    /**
     * Updates an Item in the database.
     *
     * @param   item the Item to be updated
     * @return  the number of rows updated
     */
    public int updateItem(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbItem.COLUMN_NAME_NAME, item.getItemName());

        int numUpdatedRows = db.update(
                DbItem.TABLE_NAME,
                values,
                DbItem._ID + " = ?",
                new String[]{String.valueOf(item.getId())}
        );

        db.close();

        return numUpdatedRows;

    }

    /**
     * Deletes an Item from the database.
     *
     * @param   item the Item to be deleted
     */
    public void deleteItem(Item item) {

        deleteItem(item.getId());

    }

    /**
     * Deletes an Item from the database.
     *
     * @param   id the id of the Item to be deleted
     */
    public void deleteItem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                DbItem.TABLE_NAME,
                DbItem._ID + " = ?",
                new String[]{ String.valueOf(id) }
        );

        db.close();

    }


    /********************************************************************************************
     ************************************* "item_type" table ************************************
     ********************************************************************************************/

    /**
     * Adds an ItemType to the database.
     *
     * @param   name the name of the new ItemType
     */
    public ItemType addItemType(String name) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbItemType.COLUMN_NAME_NAME, name);

        int id = (int) db.insert(DbItemType.TABLE_NAME, null, values);

        db.close();

        return id > 0 ? new ItemType(id, name) : null;

    }

    /**
     * Gets an ItemType from the database.
     *
     * @param   id the id of the desired ItemType
     * @return  the ItemType for the given id
     */
    public ItemType getItemType(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT *"
                + " FROM " + DbItemType.TABLE_NAME
                + " WHERE " + DbItemType._ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if(!c.moveToFirst()) {
            c.close(); db.close();
            return null;
        }

        ItemType itemType = new ItemType(
                id,
                c.getString(c.getColumnIndex(DbItemType.COLUMN_NAME_NAME))
        );

        c.close(); db.close();

        return itemType;

    }

    /**
     * Gets an ItemType from the database.
     *
     * @param   name the name of the desired ItemType
     * @return  the ItemType with the given name
     */
    public ItemType getItemType(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT *"
                + " FROM " + DbItemType.TABLE_NAME
                + " WHERE " + DbItemType.COLUMN_NAME_NAME + " = '" + name + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if(!c.moveToFirst()) {
            db.close();
            return null;
        }

        ItemType itemType = new ItemType(
                c.getInt(c.getColumnIndex(DbItemType._ID)),
                name
        );

        c.close(); db.close();

        return itemType;

    }

    /**
     * Gets a list of all ItemTypes in the database.
     *
     * @return  a list of all persisted ItemTypes
     */
    public List<ItemType> getAllItemTypes() {

        List<ItemType> itemTypes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DbItemType.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                itemTypes.add(new ItemType(
                        c.getInt(c.getColumnIndex(DbItemType._ID)),
                        c.getString(c.getColumnIndex(DbItemType.COLUMN_NAME_NAME))
                ));
            } while (c.moveToNext());
        }

        c.close(); db.close();

        return itemTypes;

    }

    /**
     * Gets a count of the number of ItemTypes in the database.
     *
     * @return  the number of persisted ItemTypes
     */
    public int getItemTypesCount() {

        String selectQuery = "SELECT * FROM " + DbItemType.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        int count = c.getCount();

        c.close(); db.close();

        return count;

    }



    /********************************************************************************************
     **************************************** privates ******************************************
     ********************************************************************************************/

    @Nullable
    private ItemType initItemType(SQLiteDatabase db, String name) {

        ContentValues values = new ContentValues();
        values.put(DbItemType.COLUMN_NAME_NAME, name);

        int id = (int) db.insert(DbItemType.TABLE_NAME, null, values);

        return id > 0 ? new ItemType(id, name) : null;

    }


    @Nullable
    private Item initItem(SQLiteDatabase db, String name, ItemType itemType) {

        ContentValues values = new ContentValues();
        values.put(DbItem.COLUMN_NAME_NAME, name);
        values.put(DbItem.COLUMN_NAME_ITEM_TYPE_ID, itemType.getId());

        int id = (int) db.insert(DbItem.TABLE_NAME, null, values);

        return id > 0 ? new Item(id, name, itemType) : null;

    }

}
