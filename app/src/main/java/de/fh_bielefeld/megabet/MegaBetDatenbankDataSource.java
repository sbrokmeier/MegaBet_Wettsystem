package de.fh_bielefeld.megabet;
// DataSource ist verantwortlich für alle Datenbankzugriffe und schreibt die Datensätze in die Datenbank

/**
 * Created by Jessi on 29.11.2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MegaBetDatenbankDataSource {
    private static final String LOG_TAG = MegaBetDatenbankDataSource.class.getSimpleName();
    private SQLiteDatabase database;
    private MegaBetDatabaseHelper dbHelper;

    public MegaBetDatenbankDataSource(Context context){
        Log.d(LOG_TAG, "Unser Datasource erzeugt jetzt den dbHelper.");
        dbHelper = new MegaBetDatabaseHelper(context);
    }
}
