package com.bent.MissionaryTracker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bent.MissionaryTracker.adapters.MissionaryRowAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import com.bent.MissionaryTracker.models.MissionaryModel;
import com.bent.MissionaryTracker.utils.ParseConstants;


public class MyMissionariesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_missionaries);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button addMissionaryButton = (Button) findViewById(R.id.add_missionary_btn);
        addMissionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyMissionariesActivity.this, AddMissionaryActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton addMissionaryFab = (FloatingActionButton) findViewById(R.id.new_missionary_fab);
        addMissionaryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyMissionariesActivity.this, AddMissionaryActivity.class);
                startActivity(intent);
            }
        });

        getMissionaries();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_my_missionaries, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getMissionaries() {
        ParseQuery<ParseObject> missionaryQuery = ParseQuery.getQuery(ParseConstants.MISSIONARY_TABLE);
        missionaryQuery.whereEqualTo(ParseConstants.MISSIONARY_USER, ParseUser.getCurrentUser());
        missionaryQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> missionaries, ParseException e) {
                if (e == null) {
                    int size = missionaries.size();
                    ArrayList<MissionaryModel> missionaryList = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        ParseObject missionaryParse = missionaries.get(i);
                        MissionaryModel missionary = new MissionaryModel(missionaryParse);
                        missionaryList.add(missionary);
                        toggleDisplay(missionaryList);
                    }
                    if (size == 0) {
                        LinearLayout emptyState = (LinearLayout) findViewById(R.id.empty_state_container);
                        emptyState.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void toggleDisplay(final ArrayList<MissionaryModel> missionaryList) {
        FrameLayout nonEmptyState = (FrameLayout) findViewById(R.id.non_empty_state_container);
        ListView missionaryListView = (ListView) findViewById(R.id.missionary_list);

        nonEmptyState.setVisibility(View.VISIBLE);

        MissionaryRowAdapter rowAdapter = new MissionaryRowAdapter(this, R.layout.row_missionary, missionaryList);
        missionaryListView.setAdapter(rowAdapter);

        missionaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyMissionariesActivity.this, MissionaryDetailsActivity.class);
                Bundle extras = new Bundle();
                extras.putParcelable("missionary", missionaryList.get(position));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }
}
