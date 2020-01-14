package com.melissarinch.constantiumv1.data;

public class Exercise {

    @com.google.gson.annotations.SerializedName("exercisename")
    private String mExerciseName;

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("exercisedescription")
    private String mExerciseDescription;

    @com.google.gson.annotations.SerializedName("imagename")
    private String mImageName;

    @Override
    public String toString() {
        return getText();
    }

    public Exercise() {
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

    public String getmImageName() {
        return mImageName;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Exercise && ((Exercise) o).mId == mId;
    }

    // Setters
    public final void setId(String id) {
        mId = id;
    }

    public final void setText(String text) {
        mExerciseName = text;
    }

    public final void setDescription(String description) {
        mExerciseDescription = description;
    }

    public final void setImageName(String imageName) {
        mImageName = imageName;
    }
}