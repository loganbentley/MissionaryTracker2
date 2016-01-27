package missionarytracker.bent.com.missionarytracker;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import missionarytracker.bent.com.missionarytracker.adapters.MissionaryRowAdapter;
import missionarytracker.bent.com.missionarytracker.models.MissionaryModel;
import missionarytracker.bent.com.missionarytracker.utils.ParseConstants;


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
                }
            }
        });
    }

    private void toggleDisplay(ArrayList<MissionaryModel> missionaryList) {
        LinearLayout emptyState = (LinearLayout) findViewById(R.id.empty_state_container);
        FrameLayout nonEmptyState = (FrameLayout) findViewById(R.id.non_empty_state_container);
        ListView missionaryListView = (ListView) findViewById(R.id.missionary_list);

        emptyState.setVisibility(View.GONE);
        nonEmptyState.setVisibility(View.VISIBLE);

        MissionaryRowAdapter rowAdapter = new MissionaryRowAdapter(this, R.layout.row_missionary, missionaryList);
        missionaryListView.setAdapter(rowAdapter);
    }
}
