/*
package de.fh_bielefeld.megabet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;


public class SpielBearbeitenActivity extends Activity implements OnClickListener {

*/
/*

        //public static final String MESSAGE = "de.fh_bielefeld.megabet.MESSAGE";

        // Widget GUI
        EditText editTextHeim, editTextGast, editTextDate, editTextTime, editTextToreHeim, editTextToreGast;

        // Variable for storing current date and time
        private int mYear;
        private int mMonth;
        private int mDay;
        private int mHour;
        private int mMinute;

        *//*

*/
/**
         * Called when the activity is first created.
         *//*
*/
/*

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_spiel);

            editTextDate = (EditText) findViewById(R.id.editTextDate);
            editTextTime = (EditText) findViewById(R.id.editTextTime);

            editTextDate.setOnClickListener(this);
            editTextTime.setOnClickListener(this);
        }


        @Override
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


        *//*

*/
/**
         * Called when the user clicks the ABBRECHEN button
         *//*
*/
/*

        public void onClickCancel(View view) {

            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);

        }


        *//*

*/
/**
         * Called when the user clicks the OK button
         *//*
*/
/*

        public void onClickOK(View view) {

       *//*

*/
/*

        final EditText editTextHeim = (EditText) this.findViewById(R.id.editTextHeim);
        final EditText editTextGast = (EditText) this.findViewById(R.id.editTextGast);

        heim = editTextHeim.getText().toString();
        gast = editTextGast.getText().toString();


        Intent intent = new Intent();
        Bundle rueckgabe = new Bundle();


        // Do something in response to button
        editTextHeim = (EditText) findViewById(R.id.editTextHeim);
        editTextGast = (EditText) findViewById(R.id.editTextGast);
        editTextToreHeim = (EditText) findViewById(R.id.editTextToreHeim);
        editTextToreGast = (EditText) findViewById(R.id.editTextToreGast);

        // String vom Eingabefeld holen
        String textHeim = editTextHeim.getText().toString();
        String textGast = editTextGast.getText().toString();
        String toreHeim = editTextToreHeim.getText().toString();
        String toreGast = editTextToreGast.getText().toString();

        // Eingabe übergeben
        editTextHeim.setText(textHeim);
        editTextGast.setText(textGast);
        editTextToreHeim.setText(toreHeim);
        editTextToreGast.setText(toreGast);


        String input = editTextGast.getText().toString();


        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("message", input);
        startActivity(intent);
    }


*//*
*/
/*


        }
*//*


    }


*/
