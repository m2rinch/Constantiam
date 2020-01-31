package com.melissarinch.constantiumv1.data;

import java.util.Date;

public class Session {
    @com.google.gson.annotations.SerializedName("id")
    private int mId;

    @com.google.gson.annotations.SerializedName("user_id")
    private int mUserId;

    @com.google.gson.annotations.SerializedName("exercise_id")
    private int mExerciseId;

    @com.google.gson.annotations.SerializedName("weight")
    private double mWeight;

    @com.google.gson.annotations.SerializedName("duration")
    private double mDuration;

    @com.google.gson.annotations.SerializedName("created_at")
    private String mCreatedAt;

    @com.google.gson.annotations.SerializedName("keychar_id")
    private int mKeyCharId;

    @com.google.gson.annotations.SerializedName("suggestions")
    private String mSuggestions;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getExerciseId() {
        return mExerciseId;
    }

    public void setExerciseId(int mExerciseId) {
        this.mExerciseId = mExerciseId;
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double mWeight) {
        this.mWeight = mWeight;
    }

    public double getDuration() {
        return mDuration;
    }

    public void setDuration(double mDuration) {
        this.mDuration = mDuration;
    }

    public int getKeyCharId() {
        return mKeyCharId;
    }

    public void setKeyCharId(int mKeyCharId) {
        this.mKeyCharId = mKeyCharId;
    }

    public String getSuggestions() {
        return mSuggestions;
    }

    public void setSuggestions(String mSuggestions) {
        this.mSuggestions = mSuggestions;
    }
}
