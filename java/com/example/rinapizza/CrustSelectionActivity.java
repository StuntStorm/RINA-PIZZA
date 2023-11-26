package com.example.rinapizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class CrustSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crust_selection);

        // Get the selected pizza details from the previous activity
        Pizza selectedPizza = (Pizza) getIntent().getSerializableExtra("selectedPizza");

        // Sample crust types
        List<String> crustTypes = Arrays.asList("Thin Crust", "Pan", "Stuffed Crust");

        CrustAdapter adapter = new CrustAdapter(this, crustTypes);
        ListView listView = findViewById(R.id.listViewCrust);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected crust type
                String selectedCrust = crustTypes.get(position);

                // Pass the selected pizza and crust details to the next activity
                Intent intent = new Intent(CrustSelectionActivity.this, OrderActivity.class);
                intent.putExtra("selectedPizza", selectedPizza);
                intent.putExtra("selectedCrust", selectedCrust);
                startActivity(intent);
            }
        });
    }
}
