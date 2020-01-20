package com.melissarinch.constantiumv1.data;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class WeekLineChart extends ValueFormatter {
    private final String[] days = {"Mo", "Tu", "Wed", "Th", "Fr", "Sa", "Su"};

    @Override
    public String getFormattedValue(float value) {
        return days[(int) value];
    }
}
