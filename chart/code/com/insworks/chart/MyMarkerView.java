package com.insworks.chart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.inswork.lib_cloudbase.R;

import java.text.DecimalFormat;

public class MyMarkerView extends MarkerView {
    private TextView tvContent;
    private IndexAxisValueFormatter xAxisValueFormatter;
    private DecimalFormat format;

    public MyMarkerView(Context context, IndexAxisValueFormatter xAxisValueFormatter) {
        super(context, R.layout.custom_marker_view_layout);
        this.xAxisValueFormatter = xAxisValueFormatter;
        tvContent = findViewById(R.id.tvContent);
        format = new DecimalFormat("###.000");
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("x：" + xAxisValueFormatter.getFormattedValue(e.getX(), null) + "，y：" + format.format(e.getY()));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        tvContent.setBackgroundResource(R.drawable.chart_popu);
        //pop靠右显示
        return new MPPointF(0, -getHeight());
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        Chart chart = getChartView();
        MPPointF mOffset2= super.getOffsetForDrawingAtPoint(posX, posY);
        float width = getWidth();
        //添加当点击右边往右显示
        if (posX + width + getOffset().x > chart.getWidth()) {
            mOffset2.x = getOffsetRight().x;
        }
        return mOffset2;
    }
    public MPPointF getOffsetRight() {
        tvContent.setBackgroundResource(R.drawable.chart_popu_right);
        return new MPPointF(-getWidth(), -getHeight());
    }

/*    @Override
    public MPPointF getOffset() {
        //pop居中显示
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }*/
}