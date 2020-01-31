package com.melissarinch.constantiumv1.data;

import java.util.Date;

public class CalibrationData {

    @com.google.gson.annotations.SerializedName("exercise_id")
    private int mExerciseId;

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("user_id")
    private int mUserId;

    @com.google.gson.annotations.SerializedName("created_at")
    private Date mCreatedAt;

    @com.google.gson.annotations.SerializedName("sensor_data")
    private String mSensorData;

    public CalibrationData() {
    }

    //getters
    public int getmExerciseId() {
        return mExerciseId;
    }

    public void setmExerciseId(int mExerciseId) {
        this.mExerciseId = mExerciseId;
    }

    public String getmId() {
        return mId;
    }
    public void setmId(String mId) {
        this.mId = mId;
    }

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public Date getmCreatedAt() {
        return mCreatedAt;
    }

    public void setmCreatedAt(Date mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    public String getmSensorData() {
        return mSensorData;
    }

    public void setmSensorData(String mSensorData) {
        this.mSensorData = mSensorData;
    }

}
