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

    Context context;
    int layoutResourceId;
    List<Exercise> exercises;

    public ExerciseItemAdapter(Context context, int layoutResourceId, List<Exercise> exercises) {
        super(context, layoutResourceId);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.exercises = exercises;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder = null;
        if(row==null)
        {
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            row = layoutInflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)row.getTag();
        }
        int resID = context.getResources().getIdentifier(exercises.get(position).getImageName() , "drawable", context.getPackageName());
        viewHolder.exerciseImageView.setImageResource(resID);
        viewHolder.exerciseNameTV.setText(exercises.get(position).getText());
        viewHolder.exerciseDescriptionTV.setText(exercises.get(position).getExerciseDescription());
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
