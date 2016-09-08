package com.bent.com.missionarytracker.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.bent.com.missionarytracker.utils.ParseConstants;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Logan on 1/26/2016.
 */
public class MissionaryModel implements Parcelable {

    private String name, timeLeft, mission, email;
    private Uri fileUri;
    private int pDay;

    public MissionaryModel(ParseObject missionaryParseObject) {
        name = missionaryParseObject.getString(ParseConstants.MISSIONARY_NAME);
        timeLeft = calculateTimeLeft(missionaryParseObject);
        pDay = missionaryParseObject.getInt(ParseConstants.MISSIONARY_P_DAY);
        mission = missionaryParseObject.getString(ParseConstants.MISSIONARY_MISSION);
        email = missionaryParseObject.getString(ParseConstants.MISSIONARY_EMAIL);

        ParseFile file = missionaryParseObject.getParseFile(ParseConstants.MISSIONARY_PICTURE);
        fileUri = file != null ? Uri.parse(file.getUrl()) : null;
    }

    protected MissionaryModel(Parcel in) {
        name = in.readString();
        timeLeft = in.readString();
        fileUri = in.readParcelable(Uri.class.getClassLoader());
        pDay = in.readInt();
        mission = in.readString();
        email = in.readString();
    }

    public static final Creator<MissionaryModel> CREATOR = new Creator<MissionaryModel>() {
        @Override
        public MissionaryModel createFromParcel(Parcel in) {
            return new MissionaryModel(in);
        }

        @Override
        public MissionaryModel[] newArray(int size) {
            return new MissionaryModel[size];
        }
    };

    private String calculateTimeLeft(ParseObject missionaryParseObject) {
//        Date date1 = missionaryParseObject.getDate(ParseConstants.MISSIONARY_DEPARTURE_DATE);
        Long date1 = Calendar.getInstance().getTimeInMillis();
        Long date2 = missionaryParseObject.getDate(ParseConstants.MISSIONARY_ARRIVAL_DATE).getTime();
        long diff = date2 - date1;
        return String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }

    public void setName(String missionaryName) {
        name = missionaryName;
    }

    public String getName() {
        return name;
    }

    public String getTimeLeft() {
        return timeLeft + " days left";
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public int getPDay() {
        return pDay;
    }

    public String getMission() { return mission; }

    public String getMissionMap() {
        return mission.replace(" Mission", "");
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(timeLeft);
        dest.writeParcelable(fileUri, flags);
        dest.writeInt(pDay);
        dest.writeString(mission);
        dest.writeString(email);
    }
}
