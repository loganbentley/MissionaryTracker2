package missionarytracker.bent.com.missionarytracker.models;

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
public class MissionaryModel {

    private String name, timeLeft;

    public MissionaryModel(ParseObject missionaryParseObject) {
        name = missionaryParseObject.getString(ParseConstants.MISSIONARY_NAME);
        timeLeft = calculateTimeLeft(missionaryParseObject);
    }

    private String calculateTimeLeft(ParseObject missionaryParseObject) {
        Date date1 = missionaryParseObject.getDate(ParseConstants.MISSIONARY_ARRIVAL_DATE);
        Date date2 = missionaryParseObject.getDate(ParseConstants.MISSIONARY_DEPARTURE_DATE);
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

}
