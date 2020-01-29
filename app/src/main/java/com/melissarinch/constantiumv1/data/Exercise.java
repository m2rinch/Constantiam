package com.melissarinch.constantiumv1.data;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;

public class Exercise implements Serializable {

    @com.google.gson.annotations.SerializedName("exercise_name")
    private String mExerciseName;

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("exercise_description")
    private String mExerciseDescription;

    @com.google.gson.annotations.SerializedName("image_name")
    private String mImageName;

    @com.google.gson.annotations.SerializedName("sessions")
    private List<Session> mSessions;

    @com.google.gson.annotations.SerializedName("calibrations")
    private List<Calibration> mCalibrations;

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

    public List<Session> getSessions() {
        return mSessions;
    }

    public List<Calibration> getCalibrations() {
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

    public void setSessions(List<Session> sessions) {
        mSessions = sessions;
    }

    public void setmCalibrations(List<Calibration> calibrations) {
        mCalibrations = calibrations;
    }
}