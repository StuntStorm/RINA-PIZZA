package com.example.rinapizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Get the selected pizza and crust details from the previous activity
        Pizza selectedPizza = (Pizza) getIntent().getSerializableExtra("selectedPizza");
        String selectedCrust = getIntent().getStringExtra("selectedCrust");

        // Calculate the total price
        double totalPrice = calculateTotalPrice(selectedPizza, selectedCrust);

        // Display the selected pizza and crust details
        TextView pizzaDetailsTextView = findViewById(R.id.textViewPizzaDetails);
        String pizzaDetails = "Pizza: " + selectedPizza.getName() + "\n" +
                "Crust: " + selectedCrust + "\n\n" +
                "Price: $" + String.format("%.2f", totalPrice); // Format the price to two decimal places
        pizzaDetailsTextView.setText(pizzaDetails);

        // Set up the Pay Now button
        Button payButton = findViewById(R.id.buttonPay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the total price to PaymentActivity
                Intent paymentIntent = new Intent(OrderActivity.this, PaymentActivity.class);
                paymentIntent.putExtra("totalPrice", totalPrice);
                startActivity(paymentIntent);
            }
        });
    }

    // Example method to calculate total price
    private double calculateTotalPrice(Pizza selectedPizza, String selectedCrust) {
        double basePrice = 10.00; // Base price for a pizza
        double crustPrice = 0.00;

        // Adjust the price based on the selected crust
        switch (selectedCrust) {
            case "Thin Crust":
                crustPrice = 1.50;
                break;
            case "Pan":
                crustPrice = 2.00;
                break;
            case "Stuffed Crust":
                crustPrice = 3.00;
                break;
            // Add more cases for other crust types if needed
        }

        // Calculate the total price by adding the base price and crust price
        return basePrice + crustPrice;
    }

    // Rest of the OrderActivity code...
}



