package com.bent.com.missionarytracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import missionarytracker.bent.com.missionarytracker.R;

import com.bent.com.missionarytracker.models.MissionaryModel;
import com.bent.com.missionarytracker.utils.RoundedImageView;

public class MissionaryDetailsActivity extends AppCompatActivity {

    private RoundedImageView mMissionaryProfile;
    private TextView mMissionaryNameTV, mMissionaryMissionTV, mMissionaryTimeLeftTV;
    private String mEmail;
    private EditText areaET, hasFocusET;
    private RelativeLayout contentLayout;

    private ArrayList<EditText> areas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionary_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        areaET = (EditText) findViewById(R.id.area);
        mMissionaryProfile = (RoundedImageView) findViewById(R.id.missionary_image);
        mMissionaryNameTV = (TextView) findViewById(R.id.missionary_name);
        mMissionaryMissionTV = (TextView) findViewById(R.id.missionary_mission);
        mMissionaryTimeLeftTV = (TextView) findViewById(R.id.mission_time_left);
        contentLayout = (RelativeLayout) findViewById(R.id.content_layout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("message/rfc822");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {mEmail});
                    startActivity(emailIntent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            MissionaryModel missionary = extras.getParcelable("missionary");
            fillData(missionary);
        }
    }

    private void fillData(MissionaryModel missionary) {
        mEmail = missionary.getEmail();

        Uri profilePicture = missionary.getFileUri();
        if (profilePicture != null) {
            Picasso.with(this).load(profilePicture.toString()).into(mMissionaryProfile);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mMissionaryProfile.setImageDrawable(getDrawable(R.mipmap.ic_account_circle_white_24px));
            }
            else {
                mMissionaryProfile.setImageDrawable(getResources().getDrawable(R.mipmap.ic_account_circle_white_24px));
            }
        }

        mMissionaryNameTV.setText(missionary.getName());
        mMissionaryMissionTV.setText(missionary.getMission());
        mMissionaryTimeLeftTV.setText(missionary.getTimeLeft());
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (missionary.getPDay() == dayOfWeek) {
            Snackbar.make(findViewById(android.R.id.content), "P-day is tomorrow!", Snackbar.LENGTH_LONG)
                    .setAction("Write", null).setActionTextColor(getResources().getColor(R.color.accent)).show();
        }

    }

    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.add:
                EditText newEditText = new EditText(this);

                newEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            hasFocusET = (EditText) findViewById(v.getId());
                        }
                    }
                });

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                params.addRule(RelativeLayout.BELOW, areas.get(areas.size() - 1).getId());
                newEditText.setLayoutParams(params);
                newEditText.setHint(getString(R.string.add_area));
                newEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                newEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                newEditText.setHorizontallyScrolling(false);
                newEditText.setSingleLine(false);
                newEditText.setId(areas.size());
                contentLayout.addView(newEditText);
                newEditText.requestFocus();
                areas.add(newEditText);
                break;
            case R.id.remove:
                if (areas.size() > 1) {
                    final EditText editTextToRemove = (EditText) findViewById(areas.size() - 1);

                    if (editTextToRemove.getText().toString() != "") {
                        new AlertDialog.Builder(this)
                                .setMessage("Are you sure you want to delete the previous dictation?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        contentLayout.removeView(editTextToRemove);
                                        areas.remove(areas.size() - 1);
                                        if (areas.size() > 0) {
                                            EditText requestFocusEditText;
                                            if (areas.size() == 1) {
                                                areaET.requestFocus();
                                            }
                                            else {
                                                requestFocusEditText = (EditText) findViewById(areas.size() - 1);
                                                requestFocusEditText.requestFocus();
                                            }
                                        }
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                }
                break;
            default:
                break;
        }
    }

}
