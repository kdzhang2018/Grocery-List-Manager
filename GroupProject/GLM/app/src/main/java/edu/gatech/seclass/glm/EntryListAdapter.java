package edu.gatech.seclass.glm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.button;

/**
 * @Author Kaidi Zhang and Robert Ehlers
 */

public class EntryListAdapter extends ArrayAdapter<GroceryListEntry> {

    private CompoundButton.OnCheckedChangeListener checkOnCheckChangeListener;
    private View.OnClickListener editQuantityOnClickListener;
    private View.OnClickListener editUnitOnClickListener;
    private View.OnClickListener deleteOnClickListener;
    private DbHandler dbHandler;

    public EntryListAdapter(Context context, ArrayList<GroceryListEntry> objects) {
        super(context, 0, objects);

        dbHandler = new DbHandler(this.getContext().getApplicationContext());
        checkOnCheckChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int position = (int) compoundButton.getTag();
                final GroceryListEntry entry = getItem(position);
                if (compoundButton.isChecked()) {  // checkbox is checked
                    entry.checkItem();
                } else {
                    entry.unCheckItem();
                }
                dbHandler.updateGroceryListEntry(entry);
            }
        };

        editQuantityOnClickListener = new View.OnClickListener() {
            public void onClick(View v) {

                final TextView quantityTextView = (TextView) ((View) v.getParent()).findViewById(R.id.quantity);
                int position = (int) v.getTag();
                final GroceryListEntry entry = getItem(position);

                //build and display editQuantity dialog
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.entry_edit_quantity);
                dialog.setTitle("Edit Quantity");
                dialog.setCancelable(true);

                //set old quantity
                TextView dialogQuantityTextView = (TextView) dialog.findViewById(R.id.old_quantity);
                final double oldQuantity = entry.getQuantity();
                dialogQuantityTextView.setText(String.valueOf(oldQuantity));

                //configure edit button
                Button button = (Button) dialog.findViewById(R.id.edit_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = (EditText) dialog.findViewById(R.id.new_quantity);
                        double newQuantity;
                        try {  // attempt to convert input to double, if not valid double, set to 0
                            newQuantity = Double.parseDouble(editText.getText().toString());
                        } catch (NumberFormatException e) {
                            newQuantity = 0;
                        }

                        // new quantity is <= 0
                        if (newQuantity <= 0) {
                            Toast.makeText(v.getContext(), "Provide New Quantity", Toast.LENGTH_SHORT).show();
                        } else {
                            entry.changeQuantity(newQuantity);
                            dbHandler.updateGroceryListEntry(entry);

                            // update quantity
                            quantityTextView.setText(String.valueOf(newQuantity));

                            Toast.makeText(v.getContext(), "edit " + oldQuantity + " to " + newQuantity, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
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

        editUnitOnClickListener = new View.OnClickListener() {
            public void onClick(View v) {

                final TextView unitTextView = (TextView) ((View) v.getParent()).findViewById(R.id.unit);
                int position = (int) v.getTag();
                final GroceryListEntry entry = getItem(position);

                //build and display editUnit dialog
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.entry_edit_unit);
                dialog.setTitle("Edit Unit");
                dialog.setCancelable(true);

                //set old unit
                TextView dialogUnitTextView = (TextView) dialog.findViewById(R.id.old_unit);
                final String oldUnit = entry.getUnit();
                dialogUnitTextView.setText(oldUnit);

                //configure edit button
                Button button = (Button) dialog.findViewById(R.id.edit_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = (EditText) dialog.findViewById(R.id.new_unit);
                        String newUnit = editText.getText().toString();

                        if (newUnit.equals("")) {
                            Toast.makeText(v.getContext(), "Provide New Unit", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            entry.changeUnit(newUnit);
                            dbHandler.updateGroceryListEntry(entry);

                            // update unit
                            unitTextView.setText(newUnit);

                            Toast.makeText(v.getContext(), "edit " + oldUnit + " to " + newUnit, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
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

        deleteOnClickListener = new View.OnClickListener()  {
            public void onClick(View v) {
                int position = (int) v.getTag();
                final GroceryListEntry entry = getItem(position);

                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(getContext());
                deleteBuilder.setTitle("Delete Entry");
                deleteBuilder.setMessage("Delete Entry " + entry.getItem().getItemName() + "?");
                deleteBuilder.setCancelable(true);

                deleteBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHandler.deleteGroceryListEntry(entry);
                        remove(entry);
                        Toast.makeText(getContext(), entry.getItem().getItemName() + " deleted.", Toast.LENGTH_SHORT).show();
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

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View entryListRow = convertView;
        if (entryListRow == null) {
            entryListRow = LayoutInflater.from(getContext()).inflate(R.layout.entry_list_row, parent, false);
        }

        GroceryListEntry entry = getItem(position);

        // set the checkMark checkbox
        CheckBox check = (CheckBox) entryListRow.findViewById(R.id.checkMark);
        check.setChecked(entry.getChecked());
        check.setTag(position);
        check.setOnCheckedChangeListener(checkOnCheckChangeListener);

        // set type heading to enabled if first item of its type
        String itemTypeName = entry.getItem().getItemType().getTypeName();
        String previousItemTypeName = "";  // default to none, unless set below
        if (position > 0) {  // not the first item, so there is a previous item in the list
            previousItemTypeName = getItem(position - 1).getItem().getItemType().getTypeName();
        }

        TextView typeHeading = (TextView) entryListRow.findViewById(R.id.type_heading);
        typeHeading.setText(entry.getItem().getItemType().getTypeName());
        if (!(itemTypeName.equals(previousItemTypeName))) {  //previous ItemType is different, so display the header
            typeHeading.setVisibility(View.VISIBLE);
        } else {
            typeHeading.setVisibility(View.GONE);
        }

        // set the name in view
        TextView entryName = (TextView) entryListRow.findViewById(R.id.name);
        entryName.setText(entry.getItem().getItemName());

        // set the quantity in view
        TextView quantity = (TextView) entryListRow.findViewById(R.id.quantity);
        quantity.setText(String.valueOf(entry.getQuantity()));

        // set the editQuantity button
        ImageView editQuantity = (ImageView) entryListRow.findViewById(R.id.edit_Quantity);
        editQuantity.setTag(position);
        editQuantity.setOnClickListener(editQuantityOnClickListener);

        // set the unit in view
        TextView unit = (TextView) entryListRow.findViewById(R.id.unit);
        unit.setText(entry.getUnit());

        // set the editUnit button
        ImageView editUnit = (ImageView) entryListRow.findViewById(R.id.edit_Unit);
        editUnit.setTag(position);
        editUnit.setOnClickListener(editUnitOnClickListener);

        // set the delete button
        ImageView button = (ImageView) entryListRow.findViewById(R.id.delete_button);
        button.setTag(position);
        button.setOnClickListener(deleteOnClickListener);

        return entryListRow;
    }
}
