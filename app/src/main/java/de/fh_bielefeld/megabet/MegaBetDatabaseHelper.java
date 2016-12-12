package de.fh_bielefeld.megabet;
// Erzeugen der Datenbank (MegaBetDatabaseHelper)

// DatabaseHelper contains all the methods to perform database operations like opening connection,
// closing connection, insert, update, read, delete and other things.
// As this class is helper class, place this under helper package.

// Create a Class named MegaBetDatabaseHelper.java and extends the class from SQLiteOpenHelper.
// public class MegaBetDatabaseHelper extends SQLiteOpenHelper{

// Add requires variables like database name, database version, column names.
// Executed TABLE CREATE statements in onCreate() method.

/**
 * Created by Jessi on 29.11.2016.
 */

//import info.androidhive.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MegaBetDatabaseHelper extends SQLiteOpenHelper {

    // LOGTAG MegaBetDatabaseHelper
    private static final String LOG_TAG = MegaBetDatabaseHelper.class.getName();
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MegaBet";

    //Table Names
    private static final String TABLE_SPIELER = "spieler";
    private static final String TABLE_SPIEL = "spiel";
    private static final String TABLE_WETTE = "wette";

    // Variables TABLE login / column names
    public static final String KEY_SPIELER_ID = "spieler_id";
    public static final String USERNAME = "username";
    public static final String PASSWORT = "passwort";
    public static final String AKTIV = "aktiv";
    public static final String TALER = "taler";
    public static final String ADMIN = " angemeldet";

    //TABLE CREATE STATEMENT ( Login )
    private static final String SQL_CREATE_LOGIN = "CREATE TABLE" + TABLE_SPIELER +
            "(" + KEY_SPIELER_ID + "Integer PRIMARY KEY AUTOINCREMENT, " +
            USERNAME + "Text Not Null," +
            PASSWORT + "Text Not Null, " +
            AKTIV + "Text Not Null, " +
            TALER + "Integer Not Null, " +
            ADMIN + "Text Not Null);";

    // Variables TABLE Spiel / column names
    //  private static final String TABLE_SPIEL = "spiel_id";
    public static final String KEY_SPIEL_ID = "spiel_id";

    public static final String HEIM = "heim";
    public static final String GAST = "gast";
    public static final String TORE_HEIM = "tore_heim";
    public static final String TORE_GAST = "tore_gast";
    public static final String DATUM = "datum";
    public static final String UHRZEIT = "uhrzeit";
    public static final String ERGEBNIS = "ergebnis";

    //TABLE CREATE STATEMENT ( Spiel )
    private static final String SQL_CREATE_SPIEL = "CREATE TABLE" + TABLE_SPIEL +
            "(" + KEY_SPIEL_ID + "Integer PRIMARY KEY AUTOINCREMENT, " +
            HEIM+ "Text Not Null," +
            GAST + "Text Not Null, " +
            TORE_HEIM + "INTEGER Not Null, " +
            TORE_GAST + "INTEGER Not Null, " +
            DATUM + "Double Not Null," +
            UHRZEIT + "Double Not Null, " +
            ERGEBNIS + "INTEGER Not Null);";

    // Variables TABLE Wette / column names
//    private static final String TABLE_WETTE = "wett_id";
    public static final String KEY_WETTE_ID = "wett_id";

 //   public static final String KEY_USERNAME = "username";
    public static final String TIPP = "tipp";
    public static final String EINSATZ = "einsatz";
    public static final String WETTGEWINN = "wettgewinn";

    //TABLE CREATE STATEMENT ( Wette )
    private static final String SQL_CREATE_WETTE = "CREATE TABLE" + TABLE_WETTE +
            "(" + KEY_WETTE_ID + "Integer PRIMARY KEY AUTOINCREMENT, " +
            KEY_SPIEL_ID + "Text Not Null, " +
            USERNAME + "Text Not Null, " +
            TIPP + "INTEGER Not Null, " +
            EINSATZ + "Double Not Null," +
            WETTGEWINN + "DOUBLE Not Null);";

    // Constructor
    public MegaBetDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOG_TAG, "DB-Helper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    // Create TABLE
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(TABLE_SPIELER);
        db.execSQL(TABLE_SPIEL);
        db.execSQL(TABLE_WETTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPIELER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPIEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WETTE);

        // create new tables
        onCreate(db);
        //   throw new UnsupportedOperationException();
    }
}

