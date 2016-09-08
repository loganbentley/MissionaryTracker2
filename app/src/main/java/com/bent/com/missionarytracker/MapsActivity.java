package com.bent.com.missionarytracker;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import missionarytracker.bent.com.missionarytracker.R;

import com.bent.com.missionarytracker.models.MissionaryModel;
import com.bent.com.missionarytracker.utils.ParseConstants;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ParseQuery<ParseObject> missionaryQuery = ParseQuery.getQuery(ParseConstants.MISSIONARY_TABLE);
        missionaryQuery.whereEqualTo(ParseConstants.MISSIONARY_USER, ParseUser.getCurrentUser());
        missionaryQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> missionaries, ParseException e) {
                if (e == null) {
                    LatLng latLng = null;
                    for (int i = 0; i < missionaries.size(); i++) {
                        ParseObject missionaryParse = missionaries.get(i);
                        MissionaryModel missionary = new MissionaryModel(missionaryParse);
                        latLng = getLocationFromAddress(missionary.getMissionMap());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(missionary.getName()));
                    }
                    if (latLng != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    }

                }
            }
        });

    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}
