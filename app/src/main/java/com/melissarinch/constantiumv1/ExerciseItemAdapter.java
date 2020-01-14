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


public class ExerciseItemAdapter extends ArrayAdapter<Exercise> {

    Context context;
    int layoutResourceId;

    public ExerciseItemAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        final Exercise currentItem = getItem(position);

        ViewHolder viewHolder = null;
        if(row==null)
        {
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            row = layoutInflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder(row);
        }
        row.setTag(viewHolder);
        int resID = context.getResources().getIdentifier(currentItem.getmImageName() , "drawable", context.getPackageName());
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
