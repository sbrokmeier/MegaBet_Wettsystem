package de.fh_bielefeld.megabet;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class AdminActivity extends AppCompatActivity {

    // Für Referenzen auf die aktuelle Activity
    final Context context = this;

    // Um die Tabelle aus der ganzen Klasse heraus updaten zu können
    ArrayAdapter<Spiel> adapter;

    // Tabelle der Spiele
    ArrayList<Spiel> spiele = new ArrayList<Spiel>();

    // Datenbank-Adapter
    private SpielAdapter mDbHelper;

    // neues Spiel hinzufügen oder vorhandenes Spiel bearbeiten?
    public final static String EXTRA_NEW_ENTRY = "new_entry";

    // SpielActivity mit OK beendet?
    public final static String EXTRA_DATA_COMMITTED = "data_committed";

    // SpielActivity mit Löschen beendet?
    public final static String EXTRA_TO_BE_DELETED = "to_be_deleted";

    // Index des bearbeiteten Eintrags (im Array = in Tabelle)
    public final static String EXTRA_ARRAY_INDEX = "array_index";

    public final static String EXTRA_HEIM = "heim";
    public final static String EXTRA_GAST= "gast";
    public final static String EXTRA_DATUM  = "datum";
    public final static String EXTRA_UHRZEIT= "uhrzeit";
    public final static String EXTRA_TOREHEIM  = "toreHeim";
    public final static String EXTRA_TOREGAST= "toreGast";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Datenbank-Adapter öffnen und Daten lesen
        mDbHelper = new SpielAdapter(this);
        fillData();

        createTableView();
    }

    private void fillData() {
        mDbHelper.open();

        // Array leeren
        spiele.removeAll(spiele);

        Cursor cursor = mDbHelper.fetchAllSpiele();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(SpielAdapter.KEY_ROWID));
            String heim = cursor.getString(cursor.getColumnIndexOrThrow(SpielAdapter.HEIM));
            String gast = cursor.getString(cursor.getColumnIndexOrThrow(SpielAdapter.GAST));
            String datum = cursor.getString(cursor.getColumnIndexOrThrow(SpielAdapter.DATUM));
            String uhrzeit = cursor.getString(cursor.getColumnIndexOrThrow(SpielAdapter.UHRZEIT));
            Log.e("DATABASE", "Eintrag ID " + itemId + " mit Heim = " + heim + " und Gast = " + gast + " am " + datum + " um " + uhrzeit + " gelesen.");
            spiele.add(new Spiel(datum, uhrzeit, heim, gast));
            cursor.moveToNext();
        }

        //Datenbank-Adapter schließen
        mDbHelper.close();

        Collections.sort(spiele);
    }

    //Button SPIEL HINZUFÜGEN
    public void onClickSpielHinzufuegen(View view) {
        Intent intent = new Intent(this, SpielActivity.class);

        // Übergabe der Werte für die Bearbeiten-Funktion
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_NEW_ENTRY, true);
        intent.putExtras(bundle);

        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            Bundle bundle = data.getExtras();

            boolean dataCommitted = bundle.getBoolean(EXTRA_DATA_COMMITTED);
            boolean newEntry = bundle.getBoolean(EXTRA_NEW_ENTRY);
            boolean toBeDeleted = bundle.getBoolean(EXTRA_TO_BE_DELETED);

            if(toBeDeleted)
            {
                int arrayIndex = bundle.getInt(EXTRA_ARRAY_INDEX);
                if( arrayIndex >= 0 && arrayIndex <= spiele.size()-1 )
                {

                    // Spiel direkt in ArrayList löschen
                    //spiele.remove(arrayIndex);

                    // Spiel in Datenbank löschen
                    long dbIndex = spiele.get(arrayIndex).getDbIndex();

                    mDbHelper.open();

                    boolean success = mDbHelper.deleteSpiel(dbIndex);
                    Log.e("DATABASE", "Datensatz mit dbIndex " + dbIndex + " wurde gelöscht");
                    if(success)
                        Log.e("DATABASE", "Löschen war erfolgreich");
                    else
                        Log.e("DATABASE", "Fehler beim Löschen");
                    fillData();
                    mDbHelper.close();
                }
                else
                    Log.e("DATABASE", " Delete: Index out of bounds: " + arrayIndex);

                adapter.notifyDataSetChanged();
            }

            if(!toBeDeleted && dataCommitted)
            {
                String datum = bundle.getString(EXTRA_DATUM);
                String uhrzeit = bundle.getString(EXTRA_UHRZEIT);
                String heim = bundle.getString(EXTRA_HEIM);
                String gast = bundle.getString(EXTRA_GAST);
                String toreHeim = bundle.getString(EXTRA_TOREHEIM);
                String toreGast = bundle.getString(EXTRA_TOREGAST);


                if(newEntry) {
                    // Neuen Eintrag anlegen direkt in ArrayList
                    //spiele.add(new Spiel(heim, gast, datum, uhrzeit, toreHeim, toreGast));
                    //Collections.sort(spiele);

                    // Neuen Eintrag anlegen in Datenbank
                    mDbHelper.open();
                    Spiel newSpiel = new Spiel(datum, uhrzeit, heim, gast);

                    long newRowId = mDbHelper.createSpiel(newSpiel);

                    Log.e("DATABASE", " DB entry created with index: " + newRowId);
                    mDbHelper.close();

                    fillData();
                    adapter.notifyDataSetChanged();
                }
                else {
                    // Eintrag bearbeiten
                    int arrayIndex = bundle.getInt(EXTRA_ARRAY_INDEX);
                    if( arrayIndex >= 0 && arrayIndex <= spiele.size()-1 ) {
                        Spiel spiel = spiele.get(arrayIndex);
                        spiel.setHeim(heim);
                        spiel.setGast(gast);
                        spiel.setDatum(datum);
                        spiel.setUhrzeit(uhrzeit);
                        spiel.setToreHeim(toreHeim);
                        spiel.setToreGast(toreGast);

                        // Update Database
                        mDbHelper.open();
                        mDbHelper.updateSpiel(spiel);
                        fillData();
                        mDbHelper.close();

                        Collections.sort(spiele);
                        adapter.notifyDataSetChanged();
                    }
                    else
                        Log.e("TAG", " Edit: Index out of bounds: " + arrayIndex);
                }
            }
        }
    }

    private void createTableView() {


        //ListView für beendete Spiele (mit Ergebnis)
        ListView beendeteSpieleListe = (ListView) findViewById(R.id.beendeteSpieleListView);
        adapter = new ArrayAdapter<Spiel>(this, android.R.layout.simple_expandable_list_item_1, spiele);
        beendeteSpieleListe.setAdapter(adapter);

        beendeteSpieleListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, SpielActivity.class);

                // Übergabe der Werte für die Bearbeiten-Funktion
                Bundle bundle = new Bundle();
                bundle.putBoolean(EXTRA_NEW_ENTRY, false);
                bundle.putString(EXTRA_DATUM, spiele.get(position).getDatum());
                bundle.putString(EXTRA_UHRZEIT, spiele.get(position).getUhrzeit());
                bundle.putString(EXTRA_HEIM, spiele.get(position).getHeim());
                bundle.putString(EXTRA_GAST, spiele.get(position).getGast());
                bundle.putString(EXTRA_TOREHEIM, spiele.get(position).getToreHeim());
                bundle.putString(EXTRA_TOREGAST, spiele.get(position).getToreGast());
                bundle.putInt(EXTRA_ARRAY_INDEX, position);
                intent.putExtras(bundle);

                startActivityForResult(intent, 0);
            }
        });

        //ListView für offene Spiele (ohne Ergebnis)
        ListView offeneSpieleListe = (ListView) findViewById(R.id.offeneSpieleListView);
        adapter = new ArrayAdapter<Spiel>(this, android.R.layout.simple_expandable_list_item_1, spiele);
        offeneSpieleListe.setAdapter(adapter);

        offeneSpieleListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, SpielActivity.class);

                // Übergabe der Werte für die Bearbeiten-Funktion
                Bundle bundle = new Bundle();
                bundle.putBoolean(EXTRA_NEW_ENTRY, false);
                bundle.putString(EXTRA_DATUM, spiele.get(position).getDatum());
                bundle.putString(EXTRA_UHRZEIT, spiele.get(position).getUhrzeit());
                bundle.putString(EXTRA_HEIM, spiele.get(position).getHeim());
                bundle.putString(EXTRA_GAST, spiele.get(position).getGast());

                bundle.putInt(EXTRA_ARRAY_INDEX, position);
                intent.putExtras(bundle);

                startActivityForResult(intent, 0);
            }
        });

    }

}




