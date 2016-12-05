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

import java.util.ArrayList;
import java.util.Collections;

import static de.fh_bielefeld.megabet.R.string.gast;
import static de.fh_bielefeld.megabet.R.string.heim;


public class AdminActivity extends AppCompatActivity {

    // Für Referenzen auf die aktuelle Activity
    final Context context = this;

    // Um die Tabelle aus der ganzen Klasse heraus updaten zu können
    ArrayAdapter<Spiel> adapter;

    // Tabelle der Personen
    ArrayList<Spiel> spiele = new ArrayList<Spiel>();

    // Datenbank-Adapter
    private SpielAdapter mDbHelper;

    // Neuer Eintrag oder bearbeiten
    public final static String EXTRA_NEW_ENTRY = "new_entry";

    // Detaildialog mit OK beendet?
    public final static String EXTRA_DATA_COMMITTED = "data_committed";

    // Index des bearbeiteten Eintrags (im Array = in Tabelle)
    public final static String EXTRA_ARRAY_INDEX = "array_index";

    public final static String EXTRA_HEIM = "heim";
    public final static String EXTRA_GAST= "gast";
    public final static String EXTRA_DATUM  = "datum";
    public final static String EXTRA_UHRZEIT= "uhrzeit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // öffne Datenbank und lese Daten aus
        mDbHelper = new SpielAdapter(this);
        fillData();

        //loadTestData();
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
            String heim = cursor.getString(cursor.getColumnIndexOrThrow(SpielAdapter.KEY_HEIM));
            String gast = cursor.getString(cursor.getColumnIndexOrThrow(SpielAdapter.KEY_GAST));
            Log.e("DATABASE", "Eintrag ID " + itemId + " mit Heim = " + heim + " und Gast = " + gast + " gelesen.");
            spiele.add(new Spiel(heim, gast, itemId));
            cursor.moveToNext();
        }
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

    private void loadTestData() {
        spiele.add(new Spiel("S04", "BVB"));
        spiele.add(new Spiel("BCS", "FCB"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            Bundle bundle = data.getExtras();

            boolean dataCommitted = bundle.getBoolean(EXTRA_DATA_COMMITTED);
            boolean newEntry = bundle.getBoolean(EXTRA_NEW_ENTRY);


            if(dataCommitted)
            {
                String heim = bundle.getString(EXTRA_HEIM);
                String gast = bundle.getString(EXTRA_GAST);

                if(newEntry) {
                    /* Neuen Eintrag anlegen direkt in ArrayList

                    personen.add(new Person(name, alter));
                    Collections.sort(personen); */

                    // Neuen Eintrag anlegen in Datenbank
                    mDbHelper.open();
                    Spiel newSpiel = new Spiel(heim, gast);

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

        final ListView spieleListe = (ListView) findViewById(R.id.spieleListView);
        adapter = new ArrayAdapter<Spiel>(this, android.R.layout.simple_expandable_list_item_1, spiele);
        spieleListe.setAdapter(adapter);

        spieleListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), "Click ListItem Number " + position, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, AdminActivity.class);

                // Übergabe der Werte für die Bearbeiten-Funktion
                Bundle bundle = new Bundle();
                bundle.putBoolean(EXTRA_NEW_ENTRY, false);
                bundle.putString(EXTRA_HEIM, spiele.get(position).getHeim());
                bundle.putString(EXTRA_GAST, spiele.get(position).getGast());
                bundle.putInt(EXTRA_ARRAY_INDEX, position);
                intent.putExtras(bundle);

                startActivityForResult(intent, 0);
            }
        });
    }








/*

    // Button SPIEL HINZUFÜGEN
    public void onClickSpielHinzufuegen(View view) {
        Intent intent = new Intent(this, SpielBearbeitenActivity.class);

        // Übergabe der Werte für die Bearbeiten-Funktion
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_NEW_ENTRY, true);
        intent.putExtras(bundle);

        startActivity(intent);

    }

*/

}




