package com.example.rinapizza;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PizzaAdapter extends ArrayAdapter<Pizza> {

    public PizzaAdapter(Context context, List<Pizza> pizzas) {
        super(context, 0, pizzas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pizza pizza = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pizza, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageViewPizza);
        TextView textViewName = convertView.findViewById(R.id.textViewPizzaName);
        TextView textViewIngredients = convertView.findViewById(R.id.textViewIngredients);

        imageView.setImageResource(pizza.getImageResourceId());
        textViewName.setText(pizza.getName());
        textViewIngredients.setText("Ingredients: " + android.text.TextUtils.join(", ", pizza.getIngredients()));

        return convertView;
    }
}
