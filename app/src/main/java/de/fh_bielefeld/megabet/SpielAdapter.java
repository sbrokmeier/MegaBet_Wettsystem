package de.fh_bielefeld.megabet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ListView;

import java.sql.SQLDataException;
import java.util.ArrayList;

/**
 * Created by Sari on 03.12.16.
 */


public class SpielAdapter {
    private static final String DATABASE_NAME = "data";      //Name der Datenbank
    private static final String DATABASE_TABLE = "spiele";   //Name der Tabelle
    private static final int DATABASE_VERSION = 2;           //Version der Datenbank

    public static final String HEIM = "heim";
    public static final String GAST = "gast";
    public static final String DATUM = "datum";
    public static final String UHRZEIT = "uhrzeit";
    //public static final Boolean BEENDET = "beendet";
    public static final String TORE_HEIM = "toreHeim";
    public static final String TORE_GAST = "toreGast";
    public static final String KEY_ROWID = "_id";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    //TABLE CREATE STATEMENT
    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " integer primary key autoincrement, "
                    + HEIM + " text not null, "
                    + GAST + " text not null, "
                    + DATUM + " text not null, "
                    + UHRZEIT + " text not null, "
                    + TORE_HEIM + " integer, "
                    + TORE_GAST + " integer);";

    private final Context mCtx;

    public SpielAdapter (Context ctx) {
        this.mCtx = ctx;
    }

    public SpielAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    //Datenbank-Adapter schließen
    public void close() {
        mDbHelper.close();
    }

    public long createSpiel(Spiel spiel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(HEIM, spiel.getHeim());
        initialValues.put(GAST, spiel.getGast());
        initialValues.put(DATUM, spiel.getDatum());
        initialValues.put(UHRZEIT, spiel.getUhrzeit());
        initialValues.put(TORE_HEIM, spiel.getToreHeim());
        initialValues.put(TORE_GAST, spiel.getToreGast());
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteSpiel(long rowId) {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //public long deleteSpiel(Spiel spiel) {
    //    return mDb.delete(SpielAdapter.DATABASE_TABLE, SpielAdapter.KEY_ROWID + " = ?",
    //            new String[]{String.valueOf(spiel.getDbIndex())});
    //}


    public Cursor fetchAllSpiele() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, HEIM,
                GAST, DATUM, UHRZEIT, TORE_HEIM, TORE_GAST}, null, null, null, null, null);
    }


    /*
    cursor = mDb.query("spiele", new String[]{"_id", "datum", "uhrzeit", "heim", "gast"}, null null, null, null, null);


    CursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1),
    cursor,
    new String[]{"datum", "uhrzeit", "heim", "gast"},
    new int[]{android.R.id.offeneSpieleListView}


    public Cursor cursorBeendeteSpiele(){{
            Cursor cCursor;
            cCursor = mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID,
                            HEIM, GAST, DATUM, UHRZEIT, TORE_HEIM, TORE_GAST}, null,
                    null, null, null, "DATUM DESC, UHRZEIT");
            if (cCursor != null) {
                cCursor.moveToFirst();
            }
            return cCursor;
        }

    }
    */


    public Cursor fetchSpiel(long rowId) throws SQLException {
        // hier ohne distinct
        Cursor mCursor;
        mCursor = mDb.query(false, DATABASE_TABLE, new String[] {KEY_ROWID,
                        HEIM, GAST, DATUM, UHRZEIT, TORE_HEIM, TORE_GAST}, " = " + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateSpiel(Spiel spiel) {
        ContentValues args = new ContentValues();
        args.put(HEIM, spiel.getHeim());
        args.put(GAST, spiel.getGast());
        args.put(DATUM, spiel.getDatum());
        args.put(UHRZEIT, spiel.getUhrzeit());
        args.put(TORE_HEIM, spiel.getToreHeim());
        args.put(TORE_GAST, spiel.getToreGast());
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + spiel.getDbIndex(), null) > 0;
    }

    public class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /*
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table spiele " +
                    "(" + KEY_ROWID + " INTEGER PRIMRAY KEY, " +
                    "heim TEXT NOT NULL, " +
                    "gast TEXT NOT NULL, " +
                    "datum text not null, " +
                    "uhrzeit text not null, " +
                    "toreHeim integer," +
                    "toreGast integer" +
                    "ergebinis integer)");
        }
        */

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            //on upgrade drop older table
            db.execSQL("DROP TABLE IF EXISTS " + SpielAdapter.DATABASE_TABLE);
            //create new table
            db.execSQL(DATABASE_CREATE);
        }

        //public void insert(SQLiteDatabase db, String heim, String gast, String datum, String uhrzeit, int toreHeim, int toreGast, int ergebnis){
        //    String sql = "insert into "+ DATABASE_TABLE + " values (" + heim + ", " + gast + ", " + datum + ", " + uhrzeit + ", " + toreHeim + ", " + toreGast + ", " + ergebnis + ")";
        //    db.execSQL(sql);
        //}
    }
}
