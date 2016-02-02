package missionarytracker.bent.com.missionarytracker.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import missionarytracker.bent.com.missionarytracker.utils.ParseConstants;

/**
 * Created by Logan on 1/26/2016.
 */
public class MissionaryModel implements Parcelable {

    private String name, timeLeft, mission;
    private Uri fileUri;
    private int pDay;

    public MissionaryModel(ParseObject missionaryParseObject) {
        name = missionaryParseObject.getString(ParseConstants.MISSIONARY_NAME);
        timeLeft = calculateTimeLeft(missionaryParseObject);
        pDay = missionaryParseObject.getInt(ParseConstants.MISSIONARY_P_DAY);
        mission = missionaryParseObject.getString(ParseConstants.MISSIONARY_MISSION);

        ParseFile file = missionaryParseObject.getParseFile(ParseConstants.MISSIONARY_PICTURE);
        fileUri = file != null ? Uri.parse(file.getUrl()) : null;
    }

    protected MissionaryModel(Parcel in) {
        name = in.readString();
        timeLeft = in.readString();
        fileUri = in.readParcelable(Uri.class.getClassLoader());
        pDay = in.readInt();
        mission = in.readString();
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
        Date date1 = missionaryParseObject.getDate(ParseConstants.MISSIONARY_DEPARTURE_DATE);
        Date date2 = missionaryParseObject.getDate(ParseConstants.MISSIONARY_ARRIVAL_DATE);
        long diff = date2.getTime() - date1.getTime();
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

    public void setFileUri(Uri missionaryUri) {
        fileUri = missionaryUri;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public int getPDay() {
        return pDay;
    }

    public void setMission(String missionaryMission) { mission = missionaryMission; }

    public String getMission() { return mission; }

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
    }
}
