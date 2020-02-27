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

    @com.google.gson.annotations.SerializedName("cop_right_x")
    private String mCOPRightX;

    @com.google.gson.annotations.SerializedName("cop_left_x")
    private String mCOPLeftX;

    @com.google.gson.annotations.SerializedName("cop_overall_x")
    private String mCOPOverallX;

    @com.google.gson.annotations.SerializedName("cop_right_y")
    private String mCOPRightY;

    @com.google.gson.annotations.SerializedName("cop_left_y")
    private String mCOPLeftY;

    @com.google.gson.annotations.SerializedName("cop_overall_y")
    private String mCOPOverallY;

    @com.google.gson.annotations.SerializedName("variability_overall")
    private String variabilityOverall;

    @com.google.gson.annotations.SerializedName("variability_right")
    private String variabilityRight;

    @com.google.gson.annotations.SerializedName("variability_left")
    private String variabilityLeft;

    @com.google.gson.annotations.SerializedName("force_right")
    private String forceRight;

    @com.google.gson.annotations.SerializedName("force_left")
    private String forceLeft;

    @com.google.gson.annotations.SerializedName("si")
    private String mSI;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getmExerciseId() {
        return mExerciseId;
    }

    public void setmExerciseId(int mExerciseId) {
        this.mExerciseId = mExerciseId;
    }

    public double getmWeight() {
        return mWeight;
    }

    public void setmWeight(double mWeight) {
        this.mWeight = mWeight;
    }

    public double getmDuration() {
        return mDuration;
    }

    public void setmDuration(double mDuration) {
        this.mDuration = mDuration;
    }

    public String getmCreatedAt() {
        return mCreatedAt;
    }

    public void setmCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    public String getmCOPRightX() {
        return mCOPRightX;
    }

    public void setmCOPRightX(String mCOPRightX) {
        this.mCOPRightX = mCOPRightX;
    }

    public String getmCOPLeftX() {
        return mCOPLeftX;
    }

    public void setmCOPLeftX(String mCOPLeftX) {
        this.mCOPLeftX = mCOPLeftX;
    }

    public String getmCOPOverallX() {
        return mCOPOverallX;
    }

    public void setmCOPOverallX(String mCOPOverallX) {
        this.mCOPOverallX = mCOPOverallX;
    }

    public String getmCOPRightY() {
        return mCOPRightY;
    }

    public void setmCOPRightY(String mCOPRightY) {
        this.mCOPRightY = mCOPRightY;
    }

    public String getmCOPLeftY() {
        return mCOPLeftY;
    }

    public void setmCOPLeftY(String mCOPLeftY) {
        this.mCOPLeftY = mCOPLeftY;
    }

    public String getmCOPOverallY() {
        return mCOPOverallY;
    }

    public void setmCOPOverallY(String mCOPOverallY) {
        this.mCOPOverallY = mCOPOverallY;
    }

    public String getForceRight() {
        return forceRight;
    }

    public void setForceRight(String forceRight) {
        this.forceRight = forceRight;
    }

    public String getForceLeft() {
        return forceLeft;
    }

    public void setForceLeft(String forceLeft) {
        this.forceLeft = forceLeft;
    }

    public String getmSI() {
        return mSI;
    }

    public void setmSI(String mSI) {
        this.mSI = mSI;
    }

    public String getVariabilityOverall() {
        return variabilityOverall;
    }

    public void setVariabilityOverall(String variabilityOverall) {
        this.variabilityOverall = variabilityOverall;
    }

    public String getVariabilityRight() {
        return variabilityRight;
    }

    public void setVariabilityRight(String variabilityRight) {
        this.variabilityRight = variabilityRight;
    }

    public String getVariabilityLeft() {
        return variabilityLeft;
    }

    public void setVariabilityLeft(String variabilityLeft) {
        this.variabilityLeft = variabilityLeft;
    }
}
