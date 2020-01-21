package com.melissarinch.constantiumv1.data;

import java.lang.reflect.Array;

public class Exercise {

    @com.google.gson.annotations.SerializedName("exercise_name")
    private String mExerciseName;

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("exercise_description")
    private String mExerciseDescription;

    @com.google.gson.annotations.SerializedName("image_name")
    private String mImageName;

    @com.google.gson.annotations.SerializedName("sessions")
    private String mSessions;

    @com.google.gson.annotations.SerializedName("calibrations")
    private Array mCalibrations;

    public Exercise() {
    }

    @Override
    public String toString() {
        return getText();
    }

    public String getText() {
        return mExerciseName;
    }

    public String getExerciseDescription() {
        return mExerciseDescription;
    }

    public String getId() {
        return mId;
    }

    public String getImageName() {
        return mImageName;
    }

    public String getSessions() {
        return mSessions;
    }

    public Array getCalibrations() {
        return mCalibrations;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Exercise && ((Exercise) o).mId == mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setText(String text) {
        mExerciseName = text;
    }

    public void setDescription(String description) {
        mExerciseDescription = description;
    }

    public void setImageName(String imageName) {
        mImageName = imageName;
    }

    public void setSessions(String sessions) {
        mSessions = sessions;
    }

    public void setmCalibrations(Array calibrations) {
        mCalibrations = calibrations;
    }
}