package edu.gatech.seclass.glm;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Customer list adapter for listView in ListManager activity
 */

public class SearchResultListAdapter extends ArrayAdapter<Item> {

    public SearchResultListAdapter(Context context, List<Item> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View searchResultRow, final ViewGroup parent) {

        if (searchResultRow == null) {
            searchResultRow = LayoutInflater.from(getContext()).inflate(R.layout.search_list_item, parent, false);
        }

        // the searchResult entry being created
        final Item searchResult = getItem(position);

        // set the name in view
        TextView itemNameView = (TextView) searchResultRow.findViewById(R.id.showItemName);
        itemNameView.setText(searchResult.getItemName());

        TextView itemTypeView = (TextView) searchResultRow.findViewById(R.id.showItemType);
        itemTypeView.setText(searchResult.getItemType().getTypeName());

        return searchResultRow;
    }

}
