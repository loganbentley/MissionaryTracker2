package missionarytracker.bent.com.missionarytracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import missionarytracker.bent.com.missionarytracker.utils.FileHelper;
import missionarytracker.bent.com.missionarytracker.utils.ParseConstants;
import missionarytracker.bent.com.missionarytracker.utils.RoundedImageView;

public class AddMissionaryActivity extends AppCompatActivity {

    private EditText mNameET, mEmailET;
    private AutoCompleteTextView mMissionET;
    private FloatingActionButton mImageFAB, mSaveFAB;
    private Spinner mDepartureSpinner, mArrivalSpinner;
    private RoundedImageView mProfileImage;

    private int mDepartureYear, mDepartureMonth, mDepartureDay, mArrivalYear, mArrivalMonth, mArrivalDay;

    protected Uri mMediaUri;

    public static final int TAKE_PHOTO_REQUEST = 0;
    public static final int PICK_PHOTO_REQUEST = 1;
    public static final int MEDIA_TYPE_IMAGE = 2;
    public static final int MEDIA_TYPE_VIDEO = 3;

    private ProgressDialog mProgressDialog;

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
        mProfileImage = (RoundedImageView) findViewById(R.id.user_photo);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(getString(R.string.saving));

        String[] missions = getResources().getStringArray(R.array.missions);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, missions);
        mMissionET.setAdapter(adapter);

        mSaveFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddMissionaryActivity.this);
                builder.setItems(R.array.camera_choices, mDialogListener);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddMissionaryActivity.this);
                builder.setItems(R.array.camera_choices, mDialogListener);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        setSpinnerDates();

        mSaveFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog.show();
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
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(mDepartureYear, mDepartureMonth, mDepartureDay);
        Date departureDate = new Date(cal.getTimeInMillis());

        cal.set(mArrivalYear, mArrivalMonth, mArrivalYear);
        Date arrivalDate = new Date(cal.getTimeInMillis());

        ParseObject missionary = new ParseObject(ParseConstants.MISSIONARY_TABLE);
        missionary.put(ParseConstants.MISSIONARY_NAME, name);
        missionary.put(ParseConstants.MISSIONARY_EMAIL, email);
        missionary.put(ParseConstants.MISSIONARY_MISSION, mission);
        missionary.put(ParseConstants.MISSIONARY_DEPARTURE_DATE, departureDate);
        missionary.put(ParseConstants.MISSIONARY_ARRIVAL_DATE, arrivalDate);
        missionary.put(ParseConstants.MISSIONARY_USER, ParseUser.getCurrentUser());

        if(mMediaUri != null) {
            byte[] fileBytes = FileHelper.getByteArrayFromFile(AddMissionaryActivity.this, mMediaUri);
            if (fileBytes != null) {
                fileBytes = FileHelper.reduceImageForUpload(fileBytes);
                String fileName = FileHelper.getFileName(AddMissionaryActivity.this, mMediaUri, ParseConstants.TYPE_IMAGE);
                ParseFile file = new ParseFile(fileName, fileBytes);
                try {
                    file.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                missionary.put(ParseConstants.MISSIONARY_PICTURE, file);
            }
        }

        missionary.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                mProgressDialog.dismiss();
                if (e == null) {
                    Intent intent = new Intent(AddMissionaryActivity.this, MissionaryDetailsActivity.class);
                    startActivity(intent);
                }
                else {

                }
            }
        });
    }

    protected DialogInterface.OnClickListener mDialogListener;
    {
        mDialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case TAKE_PHOTO_REQUEST: //Take picture
                        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (mMediaUri == null) {
                            //Display error
                            Toast.makeText(AddMissionaryActivity.this, getString(R.string.error_external_storage), Toast.LENGTH_LONG).show();
                        } else {
                            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                            startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
                        }
                        break;
                    case PICK_PHOTO_REQUEST://Choose picture
                        Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        choosePhotoIntent.setType("image/*");
                        startActivityForResult(choosePhotoIntent, PICK_PHOTO_REQUEST);
                        break;
                }
            }
        };
    }

    private boolean isExternalStorageAvailable()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private Uri getOutputMediaFileUri(int mediaType)
    {
        if (isExternalStorageAvailable())
        {
            String appName = "Temp";
            //1. Get the external storage directory
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
            //2. Create our subdirectory
            if (!mediaStorageDir.exists())
            {
                if (!mediaStorageDir.mkdir())
                {
                    return null;
                }
            }
            //3. Create the file name
            //4. Create the file
            File mediaFile;
            Date now = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);
            String path = mediaStorageDir.getPath() + File.separator;
            if (mediaType == MEDIA_TYPE_IMAGE)
            {
                mediaFile = new File(path + "IMG_" + timeStamp + ".jpg");
            }
            else if (mediaType == MEDIA_TYPE_VIDEO)
            {
                mediaFile = new File(path + "VID" + timeStamp + ".mp4");
            }
            else
                return null;


            //5. Return the file's URI
            return Uri.fromFile(mediaFile);
        }

        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == PICK_PHOTO_REQUEST )
            {
                if (data == null)
                {
                    Toast.makeText(this, getString(R.string.general_error), Toast.LENGTH_LONG).show();
                }
                else
                {
                    mMediaUri = data.getData();
                }

                InputStream inputStream = null;
            }
            else
            {
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mMediaUri);
                sendBroadcast(mediaScanIntent);
            }

            if (mMediaUri != null)
            {
                Picasso.with(this).load(mMediaUri.toString()).resize(80, 80).into(mProfileImage);
                mImageFAB.setVisibility(View.INVISIBLE);
            }
            else {
                mProfileImage.setImageResource(R.mipmap.ic_account_circle_white_24px);
            }
        }
        else if (resultCode != RESULT_CANCELED)
        {
            Toast.makeText(this, getString(R.string.general_error), Toast.LENGTH_LONG).show();
        }
    }

}
