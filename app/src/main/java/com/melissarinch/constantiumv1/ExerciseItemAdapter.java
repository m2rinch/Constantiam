package com.melissarinch.constantiumv1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.melissarinch.constantiumv1.data.Exercise;

import java.util.List;


public class ExerciseItemAdapter extends ArrayAdapter<Exercise> {

    Activity activity;
    int layoutResourceId;
    List<Exercise> exercises;

    public ExerciseItemAdapter(Activity activity, int layoutResourceId) {
        super(activity, layoutResourceId);
        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        final Exercise currentItem = getItem(position);

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
        int resID = activity.getResources().getIdentifier("squat" , "drawable", activity.getPackageName());
        viewHolder.exerciseImageView.setImageResource(resID);
        viewHolder.exerciseNameTV.setText(currentItem.getText());
        viewHolder.exerciseDescriptionTV.setText(currentItem.getExerciseDescription());
        return row;
    }

    public class ViewHolder
    {
        TextView exerciseNameTV;
        TextView exerciseDescriptionTV;
        ImageView exerciseImageView;

        ViewHolder(View exerciseListView)
        {
            exerciseNameTV = exerciseListView.findViewById(R.id.exerciseName);
            exerciseDescriptionTV = exerciseListView.findViewById(R.id.exerciseDescription);
            exerciseImageView = exerciseListView.findViewById(R.id.exerciseImage);
        }
    }

}
