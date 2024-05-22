package com.example.a91p;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.location.LocationManager;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.Status;

import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CreateAdvert extends AppCompatActivity {

    private static final String TAG = "running";
    public double latitude;
    public double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        CheckBox lostCheckBox = findViewById(R.id.LostCheckBox);
        CheckBox foundCheckBox = findViewById(R.id.FoundCheckBox);

        Places.initialize(getApplicationContext(), "AIzaSyBTQd7XcMsTy2n3_QQvLUKAOtJQCiyS2mo"); //this code initialises the Google Place API for the android application
        PlacesClient placesClient = Places.createClient(this); //this creates an instance called PlacesClient for making requests to the Google Places API in your application

        //this initialises AutocompleteSupportFragment through finding the ID in the layout. The fragment is used to provide the Google Places autocomplete functionality a UI.
        AutocompleteSupportFragment autoCF = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        //configures the autocompletesupportfragment to specify the types of data to return when a place is selected by the user
        autoCF.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG));

        //sets up an OnPlaceSelectedListener for the autocompletesupportfragment to handle the event when a user selects a place from the autocomplete suggestions
        autoCF.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                latitude = place.getLatLng().latitude; //the latitude of a place is stored in a variable called latitude
                longitude = place.getLatLng().longitude; //the longitude of a place is stored in a variable called longitude
                Toast.makeText(CreateAdvert.this, "Place: " + place.getName(), Toast.LENGTH_SHORT).show(); //show a message saying the name of the place
            }

            //shows that there is an error if choosing a place is not working.
            @Override //method overrides the onError method from the placeselectionlistener. Below this is the status of the error is passed as a parameter and logs the error
            public void onError(@NonNull Status status) {
                Log.i(TAG, "Error: " + status);
            }
        });

        EditText advertName = findViewById(R.id.AdvertName);
        EditText advertPhone = findViewById(R.id.AdvertPhone);
        EditText advertDescription = findViewById(R.id.AdvertDesc);
        EditText advertDate = findViewById(R.id.AdvertDate);

        Button saveAdvert = findViewById(R.id.SaveAdvertButton);

        Button backButton = findViewById(R.id.BackButton);

        Database db = new Database(this); //creates new database object within another context called this


        //create the functionality of the save button through using OnClickListener to perform different actions depending on the outcome
        saveAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name = advertName.getText().toString(); //gets name of advertName and converts it into a string
                Integer phone = Integer.parseInt(advertPhone.getText().toString()); //gets phone of advertPhone and converts it into an integer
                String description = advertDescription.getText().toString(); //gets description of advertDescription and converts it into a string
                String date = advertDate.getText().toString(); //gets date of advertDate and converts it into a string

                //checks if both checkboxes are checked or not and if thats the case it will show a toast message
                if(lostCheckBox.isChecked() && foundCheckBox.isChecked() || !foundCheckBox.isChecked() && !lostCheckBox.isChecked())
                {
                    Toast.makeText(CreateAdvert.this, "Please select one of the options", Toast.LENGTH_SHORT).show();
                }
                else if (lostCheckBox.isChecked()) //if lost checkbox is checked then show toast message
                {
                    Item item = new Item(name,phone,description,date, latitude, longitude, true); //creates new item from the inputs and sets lost to true
                    long result = db.insertItem(item); //inserts the item into the database
                    if(result > 0)
                    {
                        Toast.makeText(CreateAdvert.this, "Your item has been added", Toast.LENGTH_SHORT).show();
                    }
                }// if found checkbox is checked then it checks to see if the item is found in the database and if it is, it will show a toast message and remove the item, if it isnt, it will show a toast message
                else if (foundCheckBox.isChecked())
                {
                    Boolean result = db.foundItem(name);
                    if(result)
                    {
                        Toast.makeText(CreateAdvert.this, "Item found", Toast.LENGTH_SHORT).show();
                    }
                    else if (!result)
                    {
                        Toast.makeText(CreateAdvert.this, "Item not here", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });




        //set up the functionality of the back button using OnClickListener to go back to the mainactivity class
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(CreateAdvert.this, MainActivity.class);
                startActivity(intent);
            }
        });



    //this method handles a result returned by another activity that was started for a result
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
