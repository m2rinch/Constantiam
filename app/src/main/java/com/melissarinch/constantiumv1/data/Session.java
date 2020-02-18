package com.melissarinch.constantiumv1.data;

import java.io.Serializable;

@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class Session implements Serializable {
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

    @com.google.gson.annotations.SerializedName("cop_right")
    private String mCOPRight;

    @com.google.gson.annotations.SerializedName("cop_left")
    private String mCOPLeft;

    @com.google.gson.annotations.SerializedName("cop_overall")
    private String mCOPOverall;

    @com.google.gson.annotations.SerializedName("si")
    private String mSI;

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

    public String getmCOPRight() {
        return mCOPRight;
    }

    public void setmCOPRight(String mCOPRight) {
        this.mCOPRight = mCOPRight;
    }

    public String getmCOPLeft() {
        return mCOPLeft;
    }

    public void setmCOPLeft(String mCOPLeft) {
        this.mCOPLeft = mCOPLeft;
    }

    public String getmCOPOverall() {
        return mCOPOverall;
    }

    public void setmCOPOverall(String mCOPOverall) {
        this.mCOPOverall = mCOPOverall;
    }

    public String getmSI() {
        return mSI;
    }

    public void setmSI(String mSI) {
        this.mSI = mSI;
    }
}
