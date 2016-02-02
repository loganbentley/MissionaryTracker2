package missionarytracker.bent.com.missionarytracker;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import missionarytracker.bent.com.missionarytracker.models.MissionaryModel;
import missionarytracker.bent.com.missionarytracker.utils.RoundedImageView;

public class MissionaryDetailsActivity extends AppCompatActivity {

    private RoundedImageView mMissionaryProfile;
    private TextView mMissionaryNameTV, mMissionaryMissionTV, mMissionaryTimeLeftTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionary_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMissionaryProfile = (RoundedImageView) findViewById(R.id.missionary_image);
        mMissionaryNameTV = (TextView) findViewById(R.id.missionary_name);
        mMissionaryMissionTV = (TextView) findViewById(R.id.missionary_mission);
        mMissionaryTimeLeftTV = (TextView) findViewById(R.id.mission_time_left);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

}
