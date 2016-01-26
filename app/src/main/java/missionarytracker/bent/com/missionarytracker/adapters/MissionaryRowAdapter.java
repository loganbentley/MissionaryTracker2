package missionarytracker.bent.com.missionarytracker.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import missionarytracker.bent.com.missionarytracker.R;
import missionarytracker.bent.com.missionarytracker.models.MissionaryModel;
import missionarytracker.bent.com.missionarytracker.utils.ParseConstants;

/**
 * Created by Logan on 1/26/2016.
 */
public class MissionaryRowAdapter extends ArrayAdapter<MissionaryModel> {

    Context mContext;
    private List<MissionaryModel> mMissionaryList = new ArrayList<>();

    public MissionaryRowAdapter(Context context, int resource, List<MissionaryModel> missionaryList) {
        super(context, resource);
        mContext = context;
        mMissionaryList = missionaryList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MissionaryRowHolder holder = null;

        if (row == null){
            row = LayoutInflater.from(mContext).inflate(R.layout.row_missionary, parent, false);

            holder = new MissionaryRowHolder();
            holder.missionaryNameTV = (TextView) row.findViewById(R.id.missionary_name);
            holder.timeLeftTV = (TextView) row.findViewById(R.id.time_left);
            holder.missionaryThumbnail = (ImageView) row.findViewById(R.id.missionary_thumbnail);

            row.setTag(holder);
        }
        else {
            holder = (MissionaryRowHolder) row.getTag();
        }

        final MissionaryModel missionaryObject = mMissionaryList.get(position);

        holder.missionaryNameTV.setText(missionaryObject.getName());

//        Uri profilePicture = currentItem.getmImageUri();
//        if (profilePicture != null)
//        {
//            Picasso.with(mContext).load(profilePicture.toString()).into(holder.missionaryThumbnail);
//        }
//        else
//        {
//            holder.missionaryThumbnail.setImageResource(currentItem.getmIconId());
//        }

        return row;
    }


    static class MissionaryRowHolder {
        TextView missionaryNameTV, timeLeftTV;
        ImageView missionaryThumbnail;
    }

}
