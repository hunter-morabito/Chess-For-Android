package com.example.huntermorabito.chessandroid69;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import chess.RecordedGame;

/**
 * Created by huntermorabito on 12/10/16.
 */

/**
 * Adapter that is used for the listview of Recorded Games
 */
public class RecordedGameAdapter extends ArrayAdapter<RecordedGame> {


    public RecordedGameAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public RecordedGameAdapter(Context context, int resource, ArrayList<RecordedGame> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.recorded_item, null);
        }

        RecordedGame game = getItem(position);

        if (game != null) {
            TextView nameDescription = (TextView) v.findViewById(R.id.namedescription);
            TextView dateDescription = (TextView) v.findViewById(R.id.datedescription);

            if (nameDescription != null) {
                nameDescription.setText(game.getName());
            }

            if (dateDescription != null) {
                dateDescription.setText(game.getDateString());
            }
        }

        return v;
    }
}
