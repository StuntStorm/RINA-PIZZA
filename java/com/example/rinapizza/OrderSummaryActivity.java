package com.example.rinapizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OrderSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        Intent intent = getIntent();
        ArrayList<String> selectedToppings = intent.getStringArrayListExtra("selectedToppings");
        int quantity = intent.getIntExtra("quantity", 0);
        double totalPrice = intent.getDoubleExtra("totalPrice", 0.0);

        // Display the order summary
        TextView tvOrderSummary = findViewById(R.id.tvOrderSummary);
        String orderSummary = "Selected Toppings: " + selectedToppings.toString() + "\n"
                + "Quantity: " + quantity + "\n"
                + "Total Price: $" + totalPrice;
        tvOrderSummary.setText(orderSummary);

        // Set values for the TextViews
        TextView tvToppingsList = findViewById(R.id.tvToppingsList);
        tvToppingsList.setText(formatList(selectedToppings));

        TextView tvQuantityValue = findViewById(R.id.tvQuantityValue);
        tvQuantityValue.setText(String.valueOf(quantity));

        TextView tvTotalPriceValue = findViewById(R.id.tvTotalPriceValue);
        tvTotalPriceValue.setText(String.format("$%.2f", totalPrice));

        Button btnProceedToPayment = findViewById(R.id.btnProceedToPayment);
        btnProceedToPayment.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       Intent paymentIntent = new Intent(OrderSummaryActivity.this, PaymentActivity.class);
                                                       paymentIntent.putExtra("totalPrice", totalPrice);
                                                       startActivity(paymentIntent);
                                                   }});
    }

    private String formatList(ArrayList<String> list) {
        StringBuilder formattedList = new StringBuilder();
        for (String item : list) {
            formattedList.append("\u2022 ").append(item).append("\n"); // \u2022 is the bullet point character
        }
        return formattedList.toString();
    }

    // Called when the "Proceed to Payment" button is clicked
    public void proceedToPayment(View view) {
        Intent paymentIntent = new Intent(this, PaymentActivity.class);
        startActivity(paymentIntent);
    }
}
