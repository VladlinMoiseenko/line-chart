package ru.vladlin.linechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class LineChart extends View {

    private static final String DEFAULT_lINE_COLOR = "#9C27B0";
    private static final String DEFAULT_CURSOR_COLOR = "#D56C7581";
    private static final int DEFAULT_CURSOR_WIDTH = 3;
    private static final int DEFAULT_lINE_WIDTH = 5;
    private static final int DEFAULT_CIRCLE_RADIUS = 10;

    private String lc_lineColor;
    private int lc_lineWidth;
    private String lc_cursorColor;
    private int lc_cursorWidth;
    private int lc_circleRadius;

    private Paint paint = new Paint();
    private float[] pointsdata = new float[] {};

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);

        lc_lineColor = typedArray.getString(R.styleable.LineChartView_lc_lineСolor);
        if (lc_lineColor == null) lc_lineColor = DEFAULT_lINE_COLOR;

        lc_cursorColor = typedArray.getString(R.styleable.LineChartView_lc_cursorColor);
        if (lc_cursorColor == null) lc_cursorColor = DEFAULT_CURSOR_COLOR; //

        lc_lineWidth = typedArray.getInt(R.styleable.LineChartView_lc_lineWidth, DEFAULT_lINE_WIDTH);

        lc_cursorWidth = typedArray.getInt(R.styleable.LineChartView_lc_cursorWidth, DEFAULT_CURSOR_WIDTH);

        lc_circleRadius = typedArray.getInt(R.styleable.LineChartView_lc_circleRadius, DEFAULT_CIRCLE_RADIUS);

        typedArray.recycle();
    }

    public void setDataChart(float[] pointsdata) {
        this.pointsdata = pointsdata.clone();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        renderCursor(canvas);
        renderChart(canvas);
    }

    private void renderCursor(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor(lc_cursorColor));
        paint.setStrokeWidth(lc_cursorWidth);
        float delta = getHeight()/20;
        canvas.drawLine(delta/2, (getHeight()-delta), (getWidth()-delta/2), (getHeight()-delta), paint);
        canvas.drawLine(delta, delta/2, delta, (getHeight()-delta/2), paint);
    }

    private void renderChart(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getX(0), getY(pointsdata[0]));

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lc_lineWidth);
        paint.setColor(Color.parseColor(lc_lineColor));

        for (int i = 0; i < pointsdata.length; i++) {
            canvas.drawCircle(getX(i), getY(pointsdata[i]), lc_circleRadius, paint);
            path.lineTo(getX(i), getY(pointsdata[i]));
        }
        canvas.drawPath(path, paint);
    }

    private float getMax(float[] array) {
        float max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    private float getY(float value) {
        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        float maxValue = getMax(pointsdata);
        value = (value / maxValue) * height;
        value = height - value;
        value += getPaddingTop();
        return value;
    }

    private float getX(float value) {
        float width = getWidth() - getPaddingLeft() - getPaddingRight();
        float maxValue = pointsdata.length - 1;
        value = (value / maxValue) * width;
        value += getPaddingLeft();
        return value;
    }

}
