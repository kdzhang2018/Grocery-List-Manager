package edu.gatech.seclass.glm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static edu.gatech.seclass.glm.R.id.quantity;
import static java.security.AccessController.getContext;

public class SearchAddItem extends AppCompatActivity {

    private String groceryListName;
    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_add_item);

        dbHandler = new DbHandler(getApplicationContext());
        this.groceryListName = getIntent().getExtras().getString("name");

        //Searching for Item in Database and Adding Results
        final SearchView searchView = (SearchView) findViewById(R.id.searchBar);
        searchView.setQueryHint("Search For Items");

        searchView.setIconified(false);
        searchView.setFocusable(true);
        searchView.requestFocus();
        searchView.setActivated(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                return doSearch(s);
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return doSearch(s);
            }

            private boolean doSearch(String s){
                final List<Item> searchResultItems = dbHandler.getItemsLike(s);
                if(!searchResultItems.isEmpty()){
                    ListView searchResults = (ListView) findViewById(R.id.searchResults);
                    ListAdapter searchResultsAdapter = new SearchResultListAdapter(getApplicationContext(), searchResultItems);
                    searchResults.setAdapter(searchResultsAdapter);

                    searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Item itemToAdd = searchResultItems.get(i);
                            GroceryList groceryList = dbHandler.getGroceryList(groceryListName);
                            GroceryListEntry groceryListEntry = dbHandler.addGroceryListEntry(groceryList, itemToAdd, 1, "count");
                            if (groceryListEntry!=null) {
                                Toast.makeText(getApplicationContext(), "Item Added: " + itemToAdd.getItemName(), Toast.LENGTH_SHORT).show();
                                //After Item has been added go back to previous Screen
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Item already exists in List: " + itemToAdd.getItemName(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    return false;
                }
                else {
                    Toast.makeText(getApplicationContext(),"No results for : "+s,Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        );

        //Adding Item to Database

        //Populating AutoComplete
        List<ItemType> itemTypes = dbHandler.getAllItemTypes();

        Spinner itemTypeSelector = (Spinner) findViewById(R.id.objectTypeSelector);
        final ArrayAdapter<ItemType> itemTypeSelectorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, itemTypes);
        itemTypeSelector.setAdapter(itemTypeSelectorAdapter);
        final Spinner typeSelected = ((Spinner) findViewById(R.id.objectTypeSelector));
        Button addItemToDbButton = (Button) findViewById(R.id.addItemToDbButton);

        addItemToDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Adding item to DB
                String itemName = searchView.getQuery().toString();
                if (itemName.equals("")) {
                    Toast.makeText(getApplicationContext(),"Provide Valid Name to Add.",Toast.LENGTH_SHORT).show();
                } else {
                    ItemType type = (ItemType) typeSelected.getSelectedItem();
                    GroceryList groceryList = dbHandler.getGroceryList(groceryListName);

                    Item itemAdded = dbHandler.addItem(itemName, type);

                    //Item doesnt already exist
                    if (itemAdded != null) {
                        GroceryListEntry groceryListEntryToBeAdded = dbHandler.addGroceryListEntry(groceryList, itemAdded, 1, "Count");
                        //If Item not in List
                        if (groceryListEntryToBeAdded != null) {
                            Toast.makeText(getApplicationContext(), "Item Added to DB and List: " + itemName, Toast.LENGTH_SHORT).show();
                            SearchAddItem.this.finish();
                        }
                    }
                    //Item in DB Already
                    else {
                        Toast.makeText(getApplicationContext(), "Item Already in Database: " + itemName, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        searchView.requestFocus();

        Button button = (Button) findViewById(R.id.return_to_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
