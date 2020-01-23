package com.melissarinch.constantiumv1.data;

import java.util.Date;

public class Calibration {
    private int mId;
    private Date mCreatedAt;
    private int mUserId;
    private int mExerciseId;
    private int mKeyCharId;

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

    public int getKeyCharId() {
        return mKeyCharId;
    }

    public void setKeyCharId(int mKeyCharId) {
        this.mKeyCharId = mKeyCharId;
    }

}
