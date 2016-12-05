package de.fh_bielefeld.megabet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import static de.fh_bielefeld.megabet.R.id.editTextDate;
import static de.fh_bielefeld.megabet.R.id.editTextTime;
import static de.fh_bielefeld.megabet.R.string.datum;
import static de.fh_bielefeld.megabet.R.string.uhrzeit;


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
        final Button ButtonAbbrechen = (Button) this.findViewById(R.id.ButtonAbbrechen);
        final EditText editTextHeim = (EditText) this.findViewById(R.id.editTextHeim);
        final EditText editTextGast = (EditText) this.findViewById(R.id.editTextGast);

        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextTime = (EditText) findViewById(R.id.editTextTime);

        editTextDate.setOnClickListener(this);
        editTextTime.setOnClickListener(this);

        // Zunächst aus dem Bundle auslesen, ob ein neuer Datensatz angelegt werden soll
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        newEntry = bundle.getBoolean(AdminActivity.EXTRA_NEW_ENTRY);
        arrayIndex = -1;

        // zu editierende Werte aus dem Bundle laden
        if(!newEntry)
        {
            heim = bundle.getString(AdminActivity.EXTRA_HEIM);
            gast = bundle.getString(AdminActivity.EXTRA_GAST);
            datum = bundle.getString(AdminActivity.EXTRA_DATUM);
            uhrzeit = bundle.getString(AdminActivity.EXTRA_UHRZEIT);
            arrayIndex = bundle.getInt(AdminActivity.EXTRA_ARRAY_INDEX);
        }

        // Werte in Widgets eintragen
        editTextHeim.setText(heim);
        editTextGast.setText(gast);
        editTextDate.setText(datum);
        editTextTime.setText(uhrzeit);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDbHelper.open();
    }

    public void onClick(View v) {

        if (v == editTextDate) {

            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    // Display Selected date in textbox
                    editTextDate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                }
            }, mYear, mMonth, mDay);
            dpd.show();
        }
        if (v == editTextTime) {

            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    // Display Selected time in textbox
                    String minuteString = "";
                    if (minute < 10) {
                        minuteString += "0" + minute;
                    } else {
                        minuteString = "" + minute;
                    }
                    editTextTime.setText(hourOfDay + ":" + minuteString);
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

        heim = editTextHeim.getText().toString();
        gast = editTextGast.getText().toString();
        datum = editTextDate.getText().toString();
        uhrzeit = editTextTime.getText().toString();

        Intent intent = new Intent();
        Bundle rueckgabe = new Bundle();

        rueckgabe.putBoolean(AdminActivity.EXTRA_DATA_COMMITTED, true);
        rueckgabe.putBoolean(AdminActivity.EXTRA_NEW_ENTRY, newEntry);
        rueckgabe.putString(AdminActivity.EXTRA_HEIM, heim);
        rueckgabe.putString(AdminActivity.EXTRA_GAST, gast);
        rueckgabe.putString(AdminActivity.EXTRA_DATUM, datum);
        rueckgabe.putString(AdminActivity.EXTRA_UHRZEIT, uhrzeit);
        rueckgabe.putInt(AdminActivity.EXTRA_ARRAY_INDEX, arrayIndex);



        intent.putExtras(rueckgabe);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent();
        Bundle rueckgabe = new Bundle();

        rueckgabe.putBoolean(AdminActivity.EXTRA_DATA_COMMITTED, false);
        rueckgabe.putString(AdminActivity.EXTRA_HEIM, "");
        rueckgabe.putString(AdminActivity.EXTRA_GAST, "");
        rueckgabe.putString(AdminActivity.EXTRA_DATUM, "");
        rueckgabe.putString(AdminActivity.EXTRA_UHRZEIT, "");

        intent.putExtras(rueckgabe);
        setResult(RESULT_OK, intent);
        finish();
    }


}


