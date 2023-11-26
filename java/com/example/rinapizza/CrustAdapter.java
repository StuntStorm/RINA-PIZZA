package com.example.rinapizza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CrustAdapter extends ArrayAdapter<String> {

    public CrustAdapter(Context context, List<String> crustTypes) {
        super(context, 0, crustTypes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String crustType = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_crust, parent, false);
        }

        TextView textViewCrust = convertView.findViewById(R.id.textViewCrust);
        textViewCrust.setText(crustType);

        return convertView;
    }
}
