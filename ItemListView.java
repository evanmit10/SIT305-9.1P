package com.example.a91p;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_view);

        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");

        TextView NameItemView = findViewById(R.id.itemListName);
        TextView DateItemView = findViewById(R.id.itemListDate);
        TextView LocationItemView = findViewById(R.id.itemListLocation);
        TextView LocationItemView2 = findViewById(R.id.itemListLocation2);


        Button backButton = findViewById(R.id.BackButton);

        Database dbHelp = new Database(this); //creates new database object within another context called this
        NameItemView.setText(itemName); //set the text of NameItemView with the itemName

        Cursor response = dbHelp.queryItem(itemName); //executes a query on the database to get the name of the item and store it in the Cursor object called response

        response.moveToFirst(); //moves to the first row of the name returned by the query
        DateItemView.setText(response.getString(4)); //get the string of DateItemView from response and having the right column index to show the date and overall set the text
        LocationItemView.setText(response.getString(5)); //get the string of LocationItemView from response and having the right column index to show the location and overall set the text
        LocationItemView2.setText(response.getString(6)); //get the string of LocationItemView2 from response and having the right column index to show the location and overall set the text

        Button removeBtn = findViewById(R.id.itemListRemoveButton);

        //create the function of the button using onclicklistener to remove the item and then go back to the mainactivity class
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean res = dbHelp.foundItem(itemName);
                if(res)
                {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
        //create the function of the button using onclicklistener to go back to the mainactivity class

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ItemListView.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

}
