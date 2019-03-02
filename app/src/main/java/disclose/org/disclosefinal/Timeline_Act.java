package disclose.org.disclosefinal;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

import disclose.org.disclose69.R;

public class Timeline_Act extends AppCompatActivity {
    EntryDatabase myDB;
    LineChartView chart;
    String[] axisData;
    float[] yAxisData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        myDB = new EntryDatabase(this);

        timeView();

        chart = findViewById(R.id.chart);

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();


        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);


        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        yAxis.setName("Happiness");
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        chart.setLineChartData(data);
        Viewport viewport = new Viewport(chart.getMaximumViewport());
        viewport.top = 1;
        viewport.bottom = -1;
        chart.setMaximumViewport(viewport);
        chart.setCurrentViewport(viewport);
    }

    private void timeView() {
        // Clear Database of Inputted values.
        //myDB.delete();
        Cursor data = myDB.getData();
        System.out.println(data);
        ArrayList<String> stringData = new ArrayList<>();
        ArrayList<Double> scoreData = new ArrayList<>();
        ArrayList<String> timeData = new ArrayList<>();

        while (data.moveToNext()) {
            stringData.add(data.getString(2));
            scoreData.add(data.getDouble(3));
            timeData.add(data.getString(4));
        }

        System.out.println(stringData);
        System.out.println(scoreData);
        System.out.println(timeData);

        axisData = new String[stringData.size()];
        yAxisData = new float[scoreData.size()];
        yAxisData[0] = .5f;

        int j = 0;
        for (Double d : scoreData) {
            float entry = d.floatValue();
            if (stringData.get(j).equals("SADNESS")) entry *= -1;
            if (stringData.get(j).equals("NEUTRAL")) entry = 0;
            if (stringData.get(j).equals("ANALYTICAL")) entry = 0.69f;
            if (stringData.get(j).equals("TENTATIVE")) entry = -0.69f;

            yAxisData[j] = entry;
            axisData[j] = j+"";
            j++;
        }
        ArrayUtils.reverse(yAxisData);
    }
}
