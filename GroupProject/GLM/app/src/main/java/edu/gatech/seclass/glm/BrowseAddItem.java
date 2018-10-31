package edu.gatech.seclass.glm;
/**
 * Created by rehlers3
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrowseAddItem extends AppCompatActivity {

    private ArrayList<String> itemTypes = new ArrayList<String>();
    private ArrayList<Object> items = new ArrayList<Object>();
    private String groceryListName;
    private GroceryList groceryList;
    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_add_item);

        groceryListName = getIntent().getExtras().getString("name");
        dbHandler = new DbHandler(getApplicationContext());
        groceryList = dbHandler.getGroceryList(groceryListName);

        buildLists();  // get types/items from database
        ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.browse_items_list_view);
        expandableList.setAdapter(new BrowseListAdapter(itemTypes, items));

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                ArrayList<String> itemList = (ArrayList<String>) items.get(i);  // get the list of items for group i
                final String itemName = itemList.get(i1);  // get specific item for item i1

                Item item = dbHandler.getItem(itemName);
                if (item == null) {
                    Toast.makeText(view.getContext(), "Error adding " + itemName + ".", Toast.LENGTH_SHORT).show();
                } else {
                    GroceryListEntry groceryListEntry = dbHandler.addGroceryListEntry(groceryList, item, 1, "count");
                    if (groceryListEntry == null) {
                        Toast.makeText(view.getContext(), "Item " + itemName + " already in list.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Item " + itemName + " added.", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        Button button = (Button) findViewById(R.id.return_to_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Builds type and item lists from database
     */
    private void buildLists() {

        ArrayList<Item> itemArrayList;
        ArrayList<String> itemList;

        // get all item types from database
        ArrayList<ItemType> itemTypeArrayList = new ArrayList<>(dbHandler.getAllItemTypes());
        // got through all item types from database
        for (ItemType itemType : itemTypeArrayList) {
            itemTypes.add(itemType.getTypeName());  // add item type name to itemTypes list
            itemArrayList = (ArrayList<Item>) dbHandler.getAllItems(itemType);  // find all items for this type
            itemList = new ArrayList<String>();  // new item array list of strings to store for items of this type
            for (Item item : itemArrayList) {
                itemList.add(item.getItemName());  // add item name to array list
            }
            items.add(itemList);  // add list of item names to list of item lists
        }
    }
}
