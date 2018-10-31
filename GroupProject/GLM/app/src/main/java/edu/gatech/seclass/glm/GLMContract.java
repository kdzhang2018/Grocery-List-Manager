package edu.gatech.seclass.glm;

import android.provider.BaseColumns;

/**
 * Contract class that explicitly specifies the layout of Grocery List Manager schema.
 *
 * A contract class is a container for constants that define names for URIs, tables, and columns.
 * The contract class allows you to use the same constants across all the other classes in the same
 * package.
 *
 * @author Travis Meares
 */

public class GLMContract {

    // private ctor to prevent instantiation
    private GLMContract() {}


    /*
     Inner classes to describe the structure of the database tables. These contain the constants
     to be referenced by other classes as needed.
     */
    public static class DbGroceryList implements BaseColumns {
        public static final String TABLE_NAME = "grocery_list";
        public static final String COLUMN_NAME_NAME = "name";


        public static final String QUERY_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_NAME + " TEXT NOT NULL UNIQUE"
                + ")";

        public static final String QUERY_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;


    }

    public static class DbGroceryListEntry implements BaseColumns {
        public static final String TABLE_NAME = "grocery_list_entry";
        public static final String COLUMN_NAME_GROCERY_LIST_ID = "grocery_list_id";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_UNIT = "unit";
        public static final String COLUMN_NAME_IS_CHECKED = "is_checked";

        public static final String QUERY_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_NAME_GROCERY_LIST_ID + " INTEGER NOT NULL, "
                        + COLUMN_NAME_ITEM_ID + " INTEGER NOT NULL, "
                        + COLUMN_NAME_QUANTITY + " REAL NOT NULL, "
                        + COLUMN_NAME_UNIT + " TEXT, "
                        + COLUMN_NAME_IS_CHECKED + " INTEGER NOT NULL, "
                        + "FOREIGN KEY(" + COLUMN_NAME_GROCERY_LIST_ID + ") REFERENCES " + DbGroceryList.TABLE_NAME + "(" + DbGroceryList._ID + ") ON DELETE CASCADE, "
                        + "FOREIGN KEY(" + COLUMN_NAME_ITEM_ID + ") REFERENCES " + DbItem.TABLE_NAME + "(" + DbItem._ID + "), "
                        + "UNIQUE(" + COLUMN_NAME_GROCERY_LIST_ID + "," + COLUMN_NAME_ITEM_ID + ")"
                        + ")";

        public static final String QUERY_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class DbItem implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ITEM_TYPE_ID = "item_type_id";

        public static final String QUERY_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_NAME_NAME + " TEXT NOT NULL UNIQUE, "
                        + COLUMN_NAME_ITEM_TYPE_ID + " INTEGER NOT NULL"
                        + ")";

        public static final String QUERY_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class DbItemType implements BaseColumns {
        public static final String TABLE_NAME = "item_type";
        public static final String COLUMN_NAME_NAME = "name";

        public static final String QUERY_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                        +COLUMN_NAME_NAME + " TEXT NOT NULL UNIQUE"
                        + ")";

        public static final String QUERY_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
