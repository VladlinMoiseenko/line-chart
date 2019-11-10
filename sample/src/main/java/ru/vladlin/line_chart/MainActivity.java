package ru.vladlin.line_chart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.vladlin.linechart.LineChart;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LineChart lineChart = (LineChart) findViewById(R.id.linechart);
        lineChart.setDataChart(new float[] { 9, 6, 15, 2, 12, 9, 14, 6, 12, 8, 13, 10 });

    }

}
