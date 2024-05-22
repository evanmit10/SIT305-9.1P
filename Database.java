package com.example.a91p;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    //constructor to set up the class called Database to manage the database called items_database with the factory to null and the version of 4
    public Database(@Nullable Context context) {
        super(context, "items_database", null, 4);
    }

    //overrides the onCreate method of the SQLiteOpenHelper class that will be called when the database is created which would then create a table called ITEMS with customised columns
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ITEM_TABLE = "CREATE TABLE ITEMS(ITEMID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PHONE INTEGER, DESCRIPTION TEXT, DATE TEXT, LATITUDE DOUBLE, LONGITUDE DOUBLE, LOST BOOLEAN)";
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
    }

    //overrides the onupgrade method of the SQLiteOpenHelper class that will be called when database needs to upgrade. In doing so, it would rop the existing ITEMS table and recreate it using onCreate method
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String DROP_ITEMS_TABLE = "DROP TABLE IF EXISTS ITEMS";
        sqLiteDatabase.execSQL(DROP_ITEMS_TABLE);
        onCreate(sqLiteDatabase);
    }

    //this inserts into the ITEMS table of the database after extracting its attributes
    public long insertItem(Item item)
    {
        SQLiteDatabase db = this.getWritableDatabase(); //gets writable database to ensure that the database is writable
        ContentValues contentValues = new ContentValues(); //creates new ContentValues object that is used to store values for insertion into the database
        contentValues.put("NAME", item.getName()); //from this up to isLost, these put values from Item into ContentValues
        contentValues.put("PHONE", item.getPhone());
        contentValues.put("DESCRIPTION", item.getDescription());
        contentValues.put("DATE", item.getDate());
        contentValues.put("LATITUDE", item.getLocationLat());
        contentValues.put("LONGITUDE", item.getLocationLong());
        contentValues.put("LOST", item.isLost());
        long row = db.insert("ITEMS", null, contentValues); //this inserts row into the ITEMS table through the insert method of the SQLiteDatabase object.

        return row; //returns row

    }

    //this deletes the row from the ITEMS table based on the name value
    public boolean foundItem(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase(); //gets writable database to ensure that the database is writable

        return db.delete("ITEMS", "NAME" + "=?", new String[]{name}) > 0;
    }

    //gets all items from the ITEMS table and returns them as an array list of strings
    public ArrayList<String> fetchAllItems()
    {
        ArrayList<String> itemList = new ArrayList<>(); //creates a new array list called itemList
        SQLiteDatabase db = this.getReadableDatabase(); //gets readable database to ensure that the database is readable
        String SELECT_ALL_ITEMS = "SELECT * FROM ITEMS"; //defines a SQL query to select all columns from the ITEMS table
        Cursor cursor = db.rawQuery(SELECT_ALL_ITEMS, null); //executes the SQL query through using rawQuery and returns a Cursor object that has the result set of the query

        if (cursor.moveToFirst()) //checks if cursor has any rows
        { //iterates through each row of the result set through using moveToNext method
            do
            {
                String item = cursor.getString(1); //gets the value of the column at index 1 through getString
                itemList.add(item); //adds the item to the itemList
            }while (cursor.moveToNext());
        }
        return itemList; //returns itemList
    }

    //gets rows from ITEMS where the NAME column would match the name value
    public Cursor queryItem(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //gets readable database to ensure that the database is readable
        Cursor res = db.rawQuery("SELECT * FROM ITEMS WHERE NAME=?", new String[]{name}); //executes a query using rawQuery method

        return res; //return res which is the Cursor object that has the result set of the query
    }

    public Cursor getAllItemLocations() //this method retrieves all item locations from the database through executing a SQL query
    {
        SQLiteDatabase db = this.getReadableDatabase(); //gets readable database to ensure that the database is readable
        String SELECT_ALL_ITEMS = "SELECT * FROM ITEMS"; //defines a SQL query string to select all columns from the items table
        Cursor cursor = db.rawQuery(SELECT_ALL_ITEMS, null); //executes a SQL query and returns a cursor object and null part means there are no selection arguments for this query

        return cursor; //returns the Cursor object that has the results of the query
    }
}
