package com.example.a91p;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_show);

        ListView listViewItems = findViewById(R.id.itemListView);
        Button backButton = findViewById(R.id.BackButton);

        Database db = new Database(this); //creates new database object within another context called this

        ArrayList<String> itemList = db.fetchAllItems(); //Declare variable itemList of the type ArrayList as a string and call the method to fetch every item on the db object in order to get all of the items from the database and assign them to itemList

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList); //creates an arrayadapter called adapter to adapt data from itemList and the current context and show the list in that UI format
        listViewItems.setAdapter(adapter); //setAdapter to listViewItems which is a ListView to display all the items

        //to set up an adapter view of the list of lost items that just has the name of it
        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = (itemList.get(i)); //get the string name of the lost item
                Intent intent = new Intent(getApplicationContext(), ItemListView.class); //setting up the intent to go to ItemListView class if theres a lost item in the ShowItems class
                intent.putExtra("itemName", name); //show the name of the lost item

                Database db = new Database(getApplicationContext()); //creates new database object within getting the application context

                startActivity(intent); //start the intent activity when clicked
            }
        });

        //button to go back to the main activity class
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ShowItems.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
