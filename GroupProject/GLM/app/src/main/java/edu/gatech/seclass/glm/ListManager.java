package edu.gatech.seclass.glm;

/**
 * Created by rehlers3
 */

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.button;

public class ListManager extends AppCompatActivity {
    private ArrayList<GroceryList> groceryLists = new ArrayList<>();
    private ListView groceryListsListView;
    private GroceryListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_manager);

        // Get grocery lists from database
        final DbHandler dbHandler = new DbHandler(getApplicationContext());
        groceryLists = new ArrayList<>(dbHandler.getAllGroceryLists());

        // Setup the ListView for the grocery lists.
        groceryListsListView = (ListView) findViewById(R.id.grocery_lists);
        listAdapter = new GroceryListAdapter(this, groceryLists);
        groceryListsListView.setAdapter(listAdapter);


        // Add listener for on item click to launch EntryManager
        groceryListsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GroceryList groceryList = listAdapter.getItem(i);

                // open the new activity (EntryManager)
                Intent intent = new Intent(view.getContext(), EntryManager.class);
                intent.putExtra("name", groceryList.getName()); // set extra string data for intent call
                view.getContext().startActivity(intent);

            }
        });

        // setup listener for add new button
        Button button = (Button) findViewById(R.id.add_list_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //build and display rename dialog
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.grocery_list_add);
                dialog.setTitle("Create New List");
                dialog.setCancelable(true);
                final EditText editText = (EditText) dialog.findViewById(R.id.new_name);

                //configure add button
                Button button = (Button) dialog.findViewById(R.id.create_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get the name of this grocery list from the textView that had id of name
                        String new_name = editText.getText().toString();

                        if (new_name.equals("")) { // empty new name
                            Toast.makeText(v.getContext(), "Provide New Name" + new_name, Toast.LENGTH_SHORT).show();
                        } else {  // create new list with new name
                            GroceryList groceryList = dbHandler.addGroceryList(new_name);
                            if (groceryList == null) {  // already exists or some other error
                                Toast.makeText(v.getContext(), "List " + new_name + " already exists.", Toast.LENGTH_SHORT).show();
                            } else {
                                groceryLists.add(groceryList);  // add new grocery list to lists
                                listAdapter.notifyDataSetChanged();
                                Toast.makeText(v.getContext(), "Creating list " + new_name, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    }
                });

                //configure cancel button
                button = (Button) dialog.findViewById(R.id.cancel_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                //now that the dialog is set up, it's time to show it
                dialog.show();

            }
        });
    }

}
