package edu.gatech.seclass.glm;

/**
 * Created by rehlers3
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Customer list adapter for listView in ListManager activity
 */

public class GroceryListAdapter extends ArrayAdapter<GroceryList> {

    private View.OnClickListener deleteOnClickListener;
    private View.OnClickListener renameOnClickListener;

    public GroceryListAdapter(Context context, ArrayList<GroceryList> objects) {
        super(context, 0, objects);

        final DbHandler dbHandler = new DbHandler(context);

        deleteOnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                int position = (int) v.getTag();  // get the position in the array adapter
                final GroceryList groceryList = getItem(position); // get the groceryList object

                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(getContext());
                deleteBuilder.setTitle("Delete List");
                deleteBuilder.setMessage("Delete List " + groceryList.getName() + "?");
                deleteBuilder.setCancelable(true);

                deleteBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dbHandler.deleteGroceryList(groceryList);
                                remove(groceryList);
                                notifyDataSetChanged();
                                Toast.makeText(getContext(), groceryList.getName() + " deleted.", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                deleteBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog deleteAlert = deleteBuilder.create();
                deleteAlert.show();

            }
        };

        renameOnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                // v is the Button view
                // so need to find parent first, which is the LinearLayout in grocery_list_row
                // then find TextView with id name inside that parent (the LinearLayout)
                final TextView nameTextView = (TextView) ((View) v.getParent()).findViewById(R.id.name);
                // get the name of this grocery list from the textView that had id of name
                final String name = nameTextView.getText().toString();

                int position = (int) v.getTag();  // get the position in the array adapter
                final GroceryList groceryList = getItem(position); // get the groceryList object

                //build and display rename dialog
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.grocery_list_rename);
                dialog.setTitle("Rename List");
                dialog.setCancelable(true);

                //set old name
                TextView dialogNameTextView = (TextView) dialog.findViewById(R.id.name);
                dialogNameTextView.setText(name);

                //give hint for new name
                final EditText newNameEditText = (EditText) dialog.findViewById(R.id.new_name);
                newNameEditText.setHint(name);

                //configure rename button
                Button button = (Button) dialog.findViewById(R.id.rename_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get the name of this grocery list from the textView that had id of name
                        String new_name = newNameEditText.getText().toString();

                        if (new_name.equals("")) { // empty new name
                            Toast.makeText(v.getContext(), "Provide New Name" + new_name, Toast.LENGTH_SHORT).show();
                        } else {
                            //check for existing list with new_name
                            GroceryList groceryList_existing = dbHandler.getGroceryList(new_name);
                            if (groceryList_existing != null) {
                                Toast.makeText(v.getContext(), "Name " + new_name + " already exists.", Toast.LENGTH_SHORT).show();
                            } else {  //name doesn't exist, go ahead and rename it.
                                groceryList.rename(new_name);  //update object name
                                dbHandler.updateGroceryList(groceryList);
                                notifyDataSetChanged();
                                Toast.makeText(v.getContext(), "rename " + name + " to " + new_name, Toast.LENGTH_SHORT).show();
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
        };

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View groceryListRow = convertView; // already created view to re-use
        if (groceryListRow == null) {  // If no existing view to reuse, create a new one using the grocery_list_row layout
            groceryListRow = LayoutInflater.from(getContext()).inflate(R.layout.grocery_list_row, parent, false);
        }

        // the grocery list entry being created
        GroceryList groceryList = getItem(position);

        // set the name in view
        TextView groceryListName = (TextView) groceryListRow.findViewById(R.id.name);
        groceryListName.setText(groceryList.getName());

        // set the delete button up
        ImageView button = (ImageView) groceryListRow.findViewById(R.id.delete_button);
        button.setTag(position);  // remember position
        button.setOnClickListener(deleteOnClickListener);

        // set the rename button up
        button = (ImageView) groceryListRow.findViewById(R.id.rename_button);
        button.setTag(position);  // remember position
        button.setOnClickListener(renameOnClickListener);

        return groceryListRow;
    }

}
