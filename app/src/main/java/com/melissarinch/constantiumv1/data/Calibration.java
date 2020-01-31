package com.melissarinch.constantiumv1.data;

import java.util.Date;

public class Calibration {
    private int mId;
    private Date mCreatedAt;
    private int mUserId;
    private int mExerciseId;
    private int mKeyCharId;

    public Exercise getmExercise() {
        return mExercise;
    }

    public void setmExercise(Exercise mExercise) {
        this.mExercise = mExercise;
    }

    public KeyChar getmKeyChar() {
        return mKeyChar;
    }

    public void setmKeyChar(KeyChar mKeyChar) {
        this.mKeyChar = mKeyChar;
    }

    public ConstantiamUser getmUser() {
        return mUser;
    }

    public void setmUser(ConstantiamUser mUser) {
        this.mUser = mUser;
    }

    private Exercise mExercise;
    private KeyChar mKeyChar;
    private ConstantiamUser mUser;

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
