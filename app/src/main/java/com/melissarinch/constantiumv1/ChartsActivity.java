package com.melissarinch.constantiumv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.scichart.charting.model.dataSeries.HlDataSeries;
import com.scichart.charting.modifiers.ModifierGroup;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.annotations.HorizontalAnchorPoint;
import com.scichart.charting.visuals.annotations.TextAnnotation;
import com.scichart.charting.visuals.annotations.VerticalAnchorPoint;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.axes.NumericAxis;
import com.scichart.charting.visuals.pointmarkers.EllipsePointMarker;
import com.scichart.charting.visuals.renderableSeries.ErrorDirection;
import com.scichart.charting.visuals.renderableSeries.ErrorType;
import com.scichart.charting.visuals.renderableSeries.FastErrorBarsRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.core.model.DoubleValues;
import com.scichart.drawing.utility.ColorUtil;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.Collections;
import java.util.Random;

public class ChartsActivity extends AppCompatActivity {
   // Create SciChartSurface using Builders
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        try {
            SciChartSurface.setRuntimeLicenseKey("TtWOQ8BLbHWIoQJnIHccbtf3fpgkFG7dgF4cXmyeeNJ0/nPKMU0a5T9YjVL3N9Q+A1DPQ3z4z6fn0p8K5USMxu9pXXdxHBmpbeyjv6aetsM45LNHbGcXyEDW8CuhrWYAF+7eQGK4w+FP+o74AsckKZBO9V1K9sijr3+VX3CaKt17jAa2gjDM+mPLP/y2SMTxLJ4YMf2g3yN841d4JDjbvdc2JHQKvHYmG6X5wgbH3TRn7s4Q2xPlA+zXnQKmmP5jWCCG6oPwEzztKwtLKecZyes02wWnFv5JVD0KY/cNo3AlryN4xFOJYEvXgWZltYJTR4iO2V43mbolRygqaO/BSJ/0p/3B9B6s/XrruHJktnm02YZdoBO5RXaPXVeWx34ZmXNmAihYqsneMoh1j1FEb1ju6AC/GV5RNOBALuLhw6AA/Ps/z42zJAOw9ciHZc9J6fk9m4cB6HOdM5UdP7F9lreehSxuldoljHyO06RFx7nh4n9543KfEAybhGRv/mB2oyMci/Xt+sN9gJk7eSpCl+Kjk7GKkc9U3uEp1prkVBw4gkicxg9MPbJxgHmFyYthlnao/yoZgP3iK0bcI36S");
        } catch (Exception e) {
            Log.e("SciChart", "Error when setting the license", e);
        }
        // Create a SciChartSurface
        SciChartSurface surface = new SciChartSurface(this);
        // Get a layout declared in "activity_main.xml" by id
        LinearLayout chartLayout = (LinearLayout) findViewById(R.id.chart_layout);
        // Add the SciChartSurface to the layout
        chartLayout.addView(surface);
        // Initialize the SciChartBuilder
        SciChartBuilder.init(this);
        // Obtain the SciChartBuilder instance
        final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();
        // Create a numeric X axis
        final IAxis xAxis = sciChartBuilder.newNumericAxis()
                .withAxisTitle("X Axis Title")
                .withVisibleRange(-5, 15)
                .build();
        // Create a numeric Y axis
        final IAxis yAxis = sciChartBuilder.newNumericAxis()
                .withAxisTitle("Y Axis Title").withVisibleRange(0, 100).build();
        // Create a TextAnnotation and specify the inscription and position for it
        TextAnnotation textAnnotation = sciChartBuilder.newTextAnnotation()
                .withX1(5.0)
                .withY1(55.0)
                .withText("Hello World!")
                .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                .withVerticalAnchorPoint(VerticalAnchorPoint.Center)
                .withFontStyle(20, ColorUtil.White)
                .build();
        // Create interactivity modifiers
        ModifierGroup chartModifiers = sciChartBuilder.newModifierGroup()
                .withPinchZoomModifier().withReceiveHandledEvents(true).build()
                .withZoomPanModifier().withReceiveHandledEvents(true).build()
                .build();
        // Add the Y axis to the YAxes collection of the surface
        Collections.addAll(surface.getYAxes(), yAxis);
        // Add the X axis to the XAxes collection of the surface
        Collections.addAll(surface.getXAxes(), xAxis);
        // Add the annotation to the Annotations collection of the surface
        Collections.addAll(surface.getAnnotations(), textAnnotation);
        // Add the interactions to the ChartModifiers collection of the surface
        Collections.addAll(surface.getChartModifiers(), chartModifiers);
    }
}
