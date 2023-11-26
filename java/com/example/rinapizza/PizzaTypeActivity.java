package com.example.rinapizza;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PizzaTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_type);

        // Sample pizza types with images and ingredients
        List<Pizza> pizzaList = new ArrayList<>();
        pizzaList.add(new Pizza("Margherita", R.drawable.margherita, Arrays.asList("Tomato Sauce", "Mozzarella", "Basil")));
        pizzaList.add(new Pizza("Pepperoni", R.drawable.pepperoni, Arrays.asList("Tomato Sauce", "Pepperoni", "Mozzarella")));
        pizzaList.add(new Pizza("Veggie", R.drawable.veggie, Arrays.asList("Tomato Sauce", "Mushrooms", "Bell Peppers", "Onions", "Olives", "Mozzarella")));

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView listView = findViewById(R.id.listViewPizza);

        PizzaAdapter adapter = new PizzaAdapter(this, pizzaList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pizza selectedPizza = pizzaList.get(position);

                // Pass the selected pizza details to the next activity
                Intent intent = new Intent(PizzaTypeActivity.this, CrustSelectionActivity.class);
                intent.putExtra("selectedPizza", selectedPizza);
                startActivity(intent);
            }
        });
    }
}
