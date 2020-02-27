package com.melissarinch.constantiumv1.data;

public class KeyCharDescription {
    @com.google.gson.annotations.SerializedName("id")
    private int mId;

    @com.google.gson.annotations.SerializedName("description_text")
    private String mDescription;

    @com.google.gson.annotations.SerializedName("suggestion_text")
    private String mSuggestion;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmSuggestion() {
        return mSuggestion;
    }

    public void setmSuggestion(String mSuggestion) {
        this.mSuggestion = mSuggestion;
    }
}
