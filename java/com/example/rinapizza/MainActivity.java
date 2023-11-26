package com.example.rinapizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Button btnProceed;
    private Spinner spinnerQuantity;

    // Map to store the price of each topping
    private static final double TOPPING_PRICE = 1.5; // You can adjust the price as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        btnProceed = findViewById(R.id.btnProceed);
        spinnerQuantity = findViewById(R.id.spinnerQuantity);

        // Set up the spinner with a quantity array (e.g., 1, 2, 3,...)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuantity.setAdapter(adapter);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event, gather selected toppings and quantity, and proceed to OrderSummaryActivity
                ArrayList<String> selectedToppings = getSelectedToppings();
                int quantity = Integer.parseInt(spinnerQuantity.getSelectedItem().toString());

                if (!selectedToppings.isEmpty()) {
                    double totalPrice = calculateTotalPrice(selectedToppings, quantity);
                    Intent intent = new Intent(MainActivity.this, OrderSummaryActivity.class);
                    intent.putStringArrayListExtra("selectedToppings", selectedToppings);
                    intent.putExtra("quantity", quantity);
                    intent.putExtra("totalPrice", totalPrice);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Select at least one topping", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ArrayList<String> getSelectedToppings() {
        ArrayList<String> selectedToppings = new ArrayList<>();
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    selectedToppings.add(checkBox.getText().toString());
                }
            }
        }
        return selectedToppings;
    }

    private double calculateTotalPrice(ArrayList<String> selectedToppings, int quantity) {
        // Calculate the total price based on the quantity and the price per topping
        return quantity * selectedToppings.size() * TOPPING_PRICE;
    }
}
