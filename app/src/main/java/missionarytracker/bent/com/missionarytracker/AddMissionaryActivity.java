package missionarytracker.bent.com.missionarytracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddMissionaryActivity extends AppCompatActivity {

    private EditText mNameET, mEmailET;
    private AutoCompleteTextView mMissionET;
    private FloatingActionButton mImageFAB, mSaveFAB;
    private Spinner mDepartureSpinner, mArrivalSpinner;

    private int mDepartureYear, mDepartureMonth, mDepartureDay, mArrivalYear, mArrivalMonth, mArrivalDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missionary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNameET = (EditText) findViewById(R.id.name_edit_text);
        mEmailET = (EditText) findViewById(R.id.email_edit_text);
        mMissionET = (AutoCompleteTextView) findViewById(R.id.auto_missions);
        mImageFAB = (FloatingActionButton) findViewById(R.id.image_fab);
        mSaveFAB = (FloatingActionButton) findViewById(R.id.save_fab);
        mDepartureSpinner = (Spinner) findViewById(R.id.departure_spinner);
        mArrivalSpinner = (Spinner) findViewById(R.id.arrival_spinner);

        setSpinnerDates();

        mSaveFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setSpinnerDates() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.add(Calendar.DATE, 1);
        mDepartureYear = cal.get(Calendar.YEAR);
        mDepartureMonth = cal.get(Calendar.MONTH);
        mDepartureDay = cal.get(Calendar.DATE);
        Date date = cal.getTime();
        ArrayList<String> dateArray = new ArrayList<>();
        dateArray.add(0, dateFormat.format(date));
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dateArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDepartureSpinner.setAdapter(adapter2);

        mDepartureSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    showDateDialog(mDepartureYear, mDepartureMonth, mDepartureDay, true);
                    return true;
                }
                return true;
            }
        });

        mArrivalSpinner.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDateDialog(mArrivalYear, mArrivalMonth, mArrivalDay, false);
                    return true;
                }
                return true;
            }
        });
    }

    public void showDateDialog(int year, int month, int day, boolean isDeparture) {
        DatePickerDialog datePickerDialog;
        if (isDeparture) {
            datePickerDialog = new DatePickerDialog(this, departureDatePickerListener, year, month, day);
        }
        else {
            datePickerDialog = new DatePickerDialog(this, arrivalDatePickerListener, year, month, day);
        }
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener departureDatePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            mDepartureYear = year;
            mDepartureMonth = month;
            mDepartureDay = day;

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DATE, day);
            Date date = cal.getTime();
            ArrayList<String> dateArray = new ArrayList<>();
            dateArray.add(0, dateFormat.format(date));
            ArrayAdapter adapter2 = new ArrayAdapter(AddMissionaryActivity.this, android.R.layout.simple_spinner_item, dateArray);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mDepartureSpinner.setAdapter(adapter2);
        }
    };

    private DatePickerDialog.OnDateSetListener arrivalDatePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            mArrivalYear = year;
            mArrivalMonth = month;
            mArrivalDay = day;

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DATE, day);
            Date date = cal.getTime();
            ArrayList<String> dateArray = new ArrayList<>();
            dateArray.add(0, dateFormat.format(date));
            ArrayAdapter adapter2 = new ArrayAdapter(AddMissionaryActivity.this, android.R.layout.simple_spinner_item, dateArray);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mArrivalSpinner.setAdapter(adapter2);
        }
    };

    private void saveData() {
        String name = mNameET.getText().toString();
        String email = mEmailET.getText().toString();
        String mission = mMissionET.getText().toString();
    }

}
