package de.fh_bielefeld.megabet;

/**
 * Created by Sari on 10.11.16.


import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class MegaBetDatabaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "megabet"; //the name of our databse
    private static final int DB_VERSION = 1; // the version of the database

    MegaBetDatabaseHelper(Context content){
        super(context, DB_NAME, null, DB_VERSION); //constructor of the SQLiteOpenHelper superclass


    }

    @Override
    public void onCreate(SQLiteDatabase db){
    db.execSQL("CREATE TABLE MEGABET (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "HEIM TEXT, " + "GAST TEXT" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}


*/