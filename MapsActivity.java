package com.example.a91p;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.a91p.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private ActivityMapsBinding binding;

    //this overall method sets up an android activity that uses Google Maps
    @Override //this indicates that the onCreate method is overriding FragmentActivity that implements OnMapReadyCallback
    protected void onCreate(Bundle savedInstanceState) { //this is called when the activity is first created and the savedInstanceState parameter allows the activity to restore its previous state
        super.onCreate(savedInstanceState); //calls the superclass implementation which is onCreate that is used for the functionality of the activity

        binding = ActivityMapsBinding.inflate(getLayoutInflater()); //uses view binding to inflate the layout for the activity
        setContentView(binding.getRoot()); //sets content view of the activity to the root view of the inflated layout and returns the root view

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps); //retrieves SupportMapFragment from the layout using the ID which is maps and its a unique fragment that contains Google Maps
        mapFragment.getMapAsync(this); //Doing this sets up the asynchronous callback to obtain the GoogleMap object. This indicates that the current activity implements the OnMapReadyCallback interface, which handles the map when prepared.



    }

    //this method uses Google Maps to add markers based on the data retrieved from the database
    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap; //Assigns googleMap object to GoogleMap Variable
        Database dbHelp = new Database(this); //creates instance of the Database class

        Cursor response = dbHelp.getAllItemLocations(); //calls the getAllItemLocations method on the dbHelp object to retrieve all item locations from the database and returns a Cursor object which reads and writes the result set returned by a database query

        if(response.moveToFirst()) //moves the cursor to the first row of the result set
        {
            do { //iterates over the result set which is inside the do block for each row in the cursor.
                String itemName = response.getString(1); //retrieves the item name of the current row from columnindex 1
                double latitude = response.getDouble(5); //retrieves the latitude of the current row from columnindex 5
                double longitude = response.getDouble(6); //retrieves the longitude of the current row from columnindex 6
                googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(itemName)); //adds the market to the map at a specified latitude and longitude with the title set to the item name
            } while(response.moveToNext());
        }
        LatLng melbourne = new LatLng(-37.813, 144.9); //creates a LatLng object representing Melbourne and its latitude and longitude
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne, 12)); //moves the camera to Melbourne and sets the zoom level to 12
    }

}
