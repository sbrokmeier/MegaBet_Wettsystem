package de.fh_bielefeld.megabet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import java.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static de.fh_bielefeld.megabet.R.id.radioButton1;


/**
 * Created by Sari on 04.12.16.
 */

public class SpielActivity extends AppCompatActivity implements View.OnClickListener {

    boolean newEntry;
    int arrayIndex;
    SpielAdapter mDbHelper;

    String heim = "";
    String gast = "";
    String datum;
    String uhrzeit;
    String toreHeim = "";
    String toreGast = "";

    // Widget GUI
    EditText editTextHeim, editTextGast, editTextDate, editTextTime, editTextToreHeim, editTextToreGast;

    // Variable for storing current date and time
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new SpielAdapter(this);

        setContentView(R.layout.activity_spiel);

        final Button ButtonOK = (Button) this.findViewById(R.id.ButtonOK);
        final Button ButtonLoeschen = (Button) this.findViewById(R.id.ButtonLoeschen);
        final Button ButtonAbbrechen = (Button) this.findViewById(R.id.ButtonAbbrechen);
        final RadioButton radioButton1 = (RadioButton) this.findViewById(R.id.radioButton1);
        final RadioButton radioButton2 = (RadioButton) this.findViewById(R.id.radioButton2);
        final RadioButton radioButton3 = (RadioButton) this.findViewById(R.id.radioButton3);

        //final EditText
        editTextHeim = (EditText) this.findViewById(R.id.editTextHeim);
        //final EditText
        editTextGast = (EditText) this.findViewById(R.id.editTextGast);

        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextTime = (EditText) findViewById(R.id.editTextTime);

        editTextToreHeim = (EditText) findViewById(R.id.editTextToreHeim);
        editTextToreGast = (EditText) findViewById(R.id.editTextToreGast);

        //onClickLIstener für Date- und Time-Picker
        editTextDate.setOnClickListener(this);
        editTextTime.setOnClickListener(this);


        // aus dem Bundle auslesen, ob ein neuer Datensatz angelegt werden soll
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        newEntry = bundle.getBoolean(AdminActivity.EXTRA_NEW_ENTRY);
        //arrayIndex = -1;


        // vorhandene Werte aus dem Bundle laden
        if(!newEntry)
        {
            heim = bundle.getString(AdminActivity.EXTRA_HEIM);
            gast = bundle.getString(AdminActivity.EXTRA_GAST);
            datum = bundle.getString(AdminActivity.EXTRA_DATUM);
            uhrzeit = bundle.getString(AdminActivity.EXTRA_UHRZEIT);
            arrayIndex = bundle.getInt(AdminActivity.EXTRA_ARRAY_INDEX);
        }

        // Werte eintragen
        editTextHeim.setText(heim);
        editTextGast.setText(gast);
        editTextDate.setText(datum);
        editTextTime.setText(uhrzeit);
        editTextToreHeim.setText(toreHeim);
        editTextToreGast.setText(toreGast);


        // LÖSCHEN-Button nicht anzeigen, wenn ein neues Spiel hinzugefügt wird
        ButtonLoeschen.setVisibility(newEntry ? View.GONE : View.VISIBLE);

        //Ergebnis-Felder deaktivieren, wenn ein neues Spiel hinzugefügt wird
        editTextToreHeim.setEnabled(newEntry ? false : true);
        editTextToreGast.setEnabled(newEntry ? false : true);
        radioButton1.setEnabled(newEntry ? false : true);
        radioButton2.setEnabled(newEntry ? false : true);
        radioButton3.setEnabled(newEntry ? false : true);

        // Felder deaktivieren, wenn ein Spiel bearbeitet wird
        editTextHeim.setEnabled(newEntry ? true : false);
        editTextGast.setEnabled(newEntry ? true : false);
        editTextDate.setEnabled(newEntry ? true : false);
        editTextTime.setEnabled(newEntry ? true : false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mDbHelper.open();
    }

    public void onClick(View v) {

        // Date Picker Dialog
        if (v == editTextDate) {

            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            //mYear = c.get(Calendar.YEAR);
            //mMonth = c.get(Calendar.MONTH);
            //mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth ) {


                    /*
                    String dayString = "";
                    if (dayOfMonth < 10) {
                        dayString += "0" + dayOfMonth;
                    } else {
                        dayString = "" + dayOfMonth;
                    }

                    String monthString = "";
                    if (monthOfYear < 10) {
                        monthString += "0" + monthOfYear;
                    } else {
                        monthString = "" + monthOfYear;
                    }
*/

                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);

                    SimpleDateFormat datumFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
                    String stringDate = datumFormat.format(newDate.getTime());

                    // Display Selected date in textbox
                    editTextDate.setText(stringDate);
                    //editTextDate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dpd.show();
        }

        // Time Picker Dialog
        if (v == editTextTime) {

            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    String minuteString = "";
                    if (minute < 10) {
                        minuteString += "0" + minute;
                    } else {
                        minuteString = "" + minute;
                    }

                    String hourString = "";
                    if (hourOfDay < 10) {
                        hourString += "0" + hourOfDay;
                    } else {
                        hourString = "" + hourOfDay;
                    }

                    // Display Selected time in textbox
                    editTextTime.setText(hourString + ":" + minuteString);
                }
            }, mHour, mMinute, true);

            tpd.show();
        }
    }

    public void onClickOK(View view) {

        final EditText editTextHeim = (EditText) this.findViewById(R.id.editTextHeim);
        final EditText editTextGast = (EditText) this.findViewById(R.id.editTextGast);
        final EditText editTextDate = (EditText) this.findViewById(R.id.editTextDate);
        final EditText editTextTime = (EditText) this.findViewById(R.id.editTextTime);
        final EditText editTextToreHeim = (EditText) this.findViewById(R.id.editTextToreHeim);
        final EditText editTextToreGast= (EditText) this.findViewById(R.id.editTextToreGast);

        heim = editTextHeim.getText().toString();
        gast = editTextGast.getText().toString();
        datum = editTextDate.getText().toString();
        uhrzeit = editTextTime.getText().toString();
        toreHeim = editTextToreHeim.getText().toString();
        toreGast = editTextToreGast.getText().toString();

        Intent intent = new Intent();
        Bundle rueckgabe = new Bundle();

        rueckgabe.putBoolean(AdminActivity.EXTRA_DATA_COMMITTED, true);
        rueckgabe.putBoolean(AdminActivity.EXTRA_TO_BE_DELETED, false);
        rueckgabe.putBoolean(AdminActivity.EXTRA_NEW_ENTRY, newEntry);
        rueckgabe.putString(AdminActivity.EXTRA_HEIM, heim);
        rueckgabe.putString(AdminActivity.EXTRA_GAST, gast);
        rueckgabe.putString(AdminActivity.EXTRA_DATUM, datum);
        rueckgabe.putString(AdminActivity.EXTRA_UHRZEIT, uhrzeit);
        rueckgabe.putString(AdminActivity.EXTRA_TOREHEIM, toreHeim);
        rueckgabe.putString(AdminActivity.EXTRA_TOREGAST, toreGast);
        rueckgabe.putInt(AdminActivity.EXTRA_ARRAY_INDEX, arrayIndex);

        intent.putExtras(rueckgabe);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent();
        Bundle rueckgabe = new Bundle();

        rueckgabe.putBoolean(AdminActivity.EXTRA_DATA_COMMITTED, false);
        rueckgabe.putBoolean(AdminActivity.EXTRA_TO_BE_DELETED, false);
        rueckgabe.putString(AdminActivity.EXTRA_HEIM, "");
        rueckgabe.putString(AdminActivity.EXTRA_GAST, "");
        rueckgabe.putString(AdminActivity.EXTRA_DATUM, "");
        rueckgabe.putString(AdminActivity.EXTRA_UHRZEIT, "");

        intent.putExtras(rueckgabe);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void onClickDelete(View view) {
        Intent intent = new Intent();
        Bundle rueckgabe = new Bundle();

        rueckgabe.putBoolean(AdminActivity.EXTRA_DATA_COMMITTED, false);
        rueckgabe.putBoolean(AdminActivity.EXTRA_TO_BE_DELETED, true);
        rueckgabe.putInt(AdminActivity.EXTRA_ARRAY_INDEX, arrayIndex);
        rueckgabe.putString(AdminActivity.EXTRA_HEIM, "");
        rueckgabe.putString(AdminActivity.EXTRA_GAST, "");
        rueckgabe.putString(AdminActivity.EXTRA_DATUM, "");
        rueckgabe.putString(AdminActivity.EXTRA_UHRZEIT, "");

        intent.putExtras(rueckgabe);
        setResult(RESULT_OK, intent);
        finish();
    }

}


