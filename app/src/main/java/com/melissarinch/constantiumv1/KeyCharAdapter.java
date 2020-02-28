package com.melissarinch.constantiumv1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.melissarinch.constantiumv1.data.Exercise;
import com.melissarinch.constantiumv1.data.KeyCharDescription;

import java.util.List;

public class KeyCharAdapter extends ArrayAdapter<KeyCharDescription> {

    Activity activity;
    int layoutResourceId;

    public KeyCharAdapter(Activity activity, int layoutResourceId) {
        super(activity, layoutResourceId);
        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        final KeyCharDescription currentItem = getItem(position);

        ViewHolder viewHolder = null;
        if(row==null)
        {
            LayoutInflater layoutInflater = (activity).getLayoutInflater();
            row = layoutInflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)row.getTag();
        }

        int resID = activity.getResources().getIdentifier( currentItem.getmImageName(), "drawable", activity.getPackageName());
        viewHolder.keyCharImage.setImageResource(resID);
        viewHolder.keyChar.setText(currentItem.getmDescription());
        viewHolder.suggestion.setText("  " + currentItem.getmSuggestion());
        return row;
    }

    public class ViewHolder
    {
        TextView keyChar;
        TextView suggestion;
        ImageView keyCharImage;

        ViewHolder(View view)
        {
            keyCharImage = view.findViewById(R.id.keycharImg);
            keyChar = view.findViewById(R.id.keychar);
            suggestion = view.findViewById(R.id.suggestion);
        }
    }

}
