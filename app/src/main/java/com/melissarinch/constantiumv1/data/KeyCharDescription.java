package com.melissarinch.constantiumv1.data;

import java.util.List;

public class KeyCharDescription {
    @com.google.gson.annotations.SerializedName("id")
    private int mId;

    @com.google.gson.annotations.SerializedName("description_text")
    private String mDescription;

    @com.google.gson.annotations.SerializedName("suggestion_text")
    private String mSuggestion;

    @com.google.gson.annotations.SerializedName("key_char")
    private List<KeyCharObj> mKeyCharObj;

    public int getmId() {
        return mId;
    }

    public List<KeyCharObj> getmKeyCharObj() {
        return mKeyCharObj;
    }

    public void setmKeyCharObj(List<KeyCharObj> mKeyCharObj) {
        this.mKeyCharObj = mKeyCharObj;
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
