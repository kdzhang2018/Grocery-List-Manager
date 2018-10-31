package edu.gatech.seclass.glm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class EntryManager extends AppCompatActivity {
    private GroceryList groceryList;
    private ArrayList<GroceryListEntry> entryLists;
    private ListView entryListsListView;
    private EntryListAdapter listAdapter;
    private DbHandler dbHandler;

    @Override
    protected void onResume() {
        super.onResume();
        loadEntries();  // load entries from database into entries list
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_manager);

        // Set up the ListView for grocery list entries
        entryListsListView = (ListView) findViewById(R.id.entry_lists);

        dbHandler = new DbHandler(getApplicationContext());
        loadEntries();  // load entries from database into entries list
    }

    private void loadEntries () {
        // get the data set with putExtra, having "name" as the identifier
        String name = getIntent().getExtras().getString("name");

        // set the TextView on the layout with the ID list_name to the name that was passed in
        TextView listNameTextView = (TextView) findViewById(R.id.list_name);
        listNameTextView.setText(name);

        groceryList = dbHandler.getGroceryList(name);
        entryLists = groceryList.getEntries();

        // refresh list of items
        listAdapter = new EntryListAdapter(this, entryLists);
        entryListsListView.setAdapter(listAdapter);
    }

    public void clearAllChecks(View view) {

        groceryList.clearAllCheckMarks();

        dbHandler.updateGroceryList(groceryList);

        // refresh the page
        listAdapter.notifyDataSetChanged();
    }

    public void loadSearchAddItem(View view) {
        // open the new activity (SearchAddItem)
        Intent intent = new Intent(this, SearchAddItem.class);
        intent.putExtra("name", groceryList.getName());
        this.startActivity(intent);
    }

    public void loadBrowseAddItem(View view) {
        // open the new activity (BrowseAddItem)
        Intent intent = new Intent(this, BrowseAddItem.class);
        intent.putExtra("name", groceryList.getName());
        this.startActivity(intent);
    }

    public void loadListManager(View view) {
        // go back to List Manager
        finish();
    }

}
