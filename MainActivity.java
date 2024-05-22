package com.example.a91p;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button createAdvertButton;
    Button showAllItemsButton;
    Button showMapButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        createAdvertButton = findViewById(R.id.createAdvert);
        showAllItemsButton = findViewById(R.id.showAllItems);
        showMapButton = findViewById(R.id.showMap);

//button to go to the CreateAdvert class through using OnClickListener and Intent
        createAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAdvert.class);
                startActivity(intent);
            }
        });

//button to go to the ShowItems class through using OnClickListener and Intent
        showAllItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowItems.class);
                startActivity(intent);            }
        });

        //button to go to the MapsActivity class through using OnClickListener and Intent
        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);            }
        });



    }
}