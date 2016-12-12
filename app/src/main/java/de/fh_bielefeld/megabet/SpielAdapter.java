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
import android.widget.TextView;
import android.widget.ListView;

import java.sql.SQLDataException;
import java.util.ArrayList;

/**
 * Created by Sari on 03.12.16.
 */


public class SpielAdapter {
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "spiele";
    private static final int DATABASE_VERSION = 2;
    public static final String KEY_HEIM = "heim";
    public static final String KEY_GAST = "gast";
    public static final String DATUM = "datum";
    public static final String UHRZEIT = "uhrzeit";
    public static final String KEY_ROWID = "_id";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    //private static final String DATABASE_CREATE =
    //      "create table " + DATABASE_TABLE +
    //      "("+ KEY_ROWID  + " INTEGER PRIMRAY KEY AUTOINCREMENT, "
    //         + KEY_HEIM + " TEXT NOT NULL, "
    //      + KEY_GAST + " TEXT NOT NULL);"

    private final Context mCtx;

    public SpielAdapter (Context ctx) {
        this.mCtx = ctx;
    }

    public SpielAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createSpiel(Spiel spiel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_HEIM, spiel.getHeim());
        initialValues.put(KEY_GAST, spiel.getGast());
        initialValues.put(DATUM, spiel.getDatum());
        initialValues.put(UHRZEIT, spiel.getUhrzeit());
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
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_HEIM,
                KEY_GAST, DATUM, UHRZEIT}, null, null, null, null, null);
    }

    public Cursor fetchSpiel(long rowId) throws SQLException {
        // hier ohne distinct
        Cursor mCursor;
        mCursor = mDb.query(false, DATABASE_TABLE, new String[] {KEY_ROWID,
                        KEY_HEIM, KEY_GAST, DATUM, UHRZEIT}, " = " + rowId, null,
                null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public boolean updateSpiel(Spiel spiel) {
        ContentValues args = new ContentValues();
        args.put(KEY_HEIM, spiel.getHeim());
        args.put(KEY_GAST, spiel.getGast());
        args.put(DATUM, spiel.getDatum());
        args.put(UHRZEIT, spiel.getUhrzeit());
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + spiel.getDbIndex(), null) > 0;
    }

    public class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

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

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + SpielAdapter.DATABASE_TABLE);
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

        public void insert(SQLiteDatabase db, String heim, String gast, String datum, String uhrzeit, int toreHeim, int toreGast, int ergebnis){
            String sql = "insert into "+ DATABASE_TABLE + " values (" + heim + ", " + gast + ", " + datum + ", " + uhrzeit + ", " + toreHeim + ", " + toreGast + ", " + ergebnis + ")";
            db.execSQL(sql);
        }
    }
}
