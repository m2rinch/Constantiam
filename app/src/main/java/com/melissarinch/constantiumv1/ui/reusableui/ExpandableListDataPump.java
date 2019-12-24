package com.melissarinch.constantiumv1.ui.reusableui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();
        List<String> feedbackLevel = new ArrayList<String>();
        feedbackLevel.add("Select Feedback Amount:");
        List<String> about = new ArrayList<String>();
        about.add("Constantiam makes use of weight distribution and center of pressure to determine your level of stability. We have designed our device to provide customized and tailorable feeedback so that you can make the most of your workouts while staying safe and improving body awareness");
        expandableListDetail.put("Feedback Level", feedbackLevel);
        expandableListDetail.put("About", about);
        expandableListDetail.put("Tutorial", new ArrayList<String>());
        expandableListDetail.put("Logout", new ArrayList<String>());
        return expandableListDetail;
    }
}
