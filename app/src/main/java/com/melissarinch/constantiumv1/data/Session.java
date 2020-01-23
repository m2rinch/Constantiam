package com.melissarinch.constantiumv1.data;

import java.util.Date;

public class Session {
    private int mId;

    private Date mCreatedAt;

    private int mUserId;

    private int mExerciseId;

    private double mWeight;

    private double mDuration;

    private int mKeyCharId;

    private String mSuggestions;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date mCreatedAt) {
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
