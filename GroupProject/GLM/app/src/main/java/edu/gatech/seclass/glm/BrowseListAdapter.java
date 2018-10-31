package edu.gatech.seclass.glm;
/**
 * Created by rehlers3
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BrowseListAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> itemTypes;
    private ArrayList<Object> items;
    private ArrayList<String> itemList;

    public BrowseListAdapter(ArrayList<String> itemTypes, ArrayList<Object> items) {
        this.itemTypes = itemTypes;
        this.items = items;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public int getGroupCount() {
        return itemTypes.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return ((ArrayList<String>) items.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup parent) {
        View itemTypeRow = convertView; // already created view to re-use
        if (itemTypeRow == null) {  // If no existing view to reuse, create a new one using the grocery_list_row layout
            itemTypeRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_list_type_row, parent, false);
        }

        CheckedTextView itemTypeView = (CheckedTextView) itemTypeRow;
        itemTypeView.setText(itemTypes.get(i));
        itemTypeView.setChecked(true);

        return itemTypeView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup parent) {
        View itemRow = convertView; // already created view to re-use
        if (itemRow == null) {  // If no existing view to reuse, create a new one using the grocery_list_row layout
            itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_list_item_row, parent, false);
        }

        itemList = (ArrayList<String>) items.get(i);  // get the list of items for group i
        final String itemName = itemList.get(i1);  // get specific item for item i1
        TextView itemNameTextView = (TextView) itemRow.findViewById(R.id.browse_item);
        itemNameTextView.setText(itemName);

        return itemRow;
    }
}
