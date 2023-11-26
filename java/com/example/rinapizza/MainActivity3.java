package com.example.rinapizza;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String phoneNumber = intent.getStringExtra("PhoneNumber");
        String email = intent.getStringExtra("Email");
        String city = intent.getStringExtra("City");

        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Hello " + name + ", welcome to RINA's PIZZA!");

        TextView userDetailsTextView = findViewById(R.id.userDetailsTextView);
        userDetailsTextView.setText("Phone Number: " + phoneNumber + "\nEmail: " + email + "\nCity: " + city);

        Button pizzaMenuButton = findViewById(R.id.pizzaMenuButton);
        pizzaMenuButton.setOnClickListener(view -> {
            // Navigate to PizzaMenuActivity
            Intent pizzaMenuIntent = new Intent(MainActivity3.this, MainActivity.class);
            startActivity(pizzaMenuIntent);
        });

        Button orderPizzaButton = findViewById(R.id.orderPizzaButton);
        orderPizzaButton.setOnClickListener(view -> {
            // Navigate to PizzaMenuActivity
            Intent orderPizzaIntent = new Intent(MainActivity3.this, PizzaTypeActivity.class);
            startActivity(orderPizzaIntent);
        });



        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(view -> {
            // Open the GitHub URL in a web browser
            String githubUrl = "https://github.com/StuntStorm/RINA-PIZZA";
            Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));
            startActivity(githubIntent);
        });




    }
}
