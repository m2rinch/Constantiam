package com.melissarinch.constantiumv1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class CustomListView  extends ArrayAdapter<String> {

    private String[] exerciseNames;
    private String[] exerciseDescriptions;
    private Integer[] exerciseImageID;
    private Activity context;

    public CustomListView(Activity context, String[] mExerciseNames, String[] mExerciseDescriptions, Integer[] mExerciseImageID) {
        super(context, R.layout.exercise_list, mExerciseNames);
        this.context = context;
        this.exerciseNames = mExerciseNames;
        this.exerciseDescriptions = mExerciseDescriptions;
        this.exerciseImageID = mExerciseImageID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r==null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.exercise_list, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.exerciseImageView.setImageResource(exerciseImageID[position]);
        viewHolder.exerciseNameTV.setText(exerciseNames[position]);
        viewHolder.exerciseDescriptionTV.setText(exerciseDescriptions[position]);
        return r;
    }

    class ViewHolder
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
