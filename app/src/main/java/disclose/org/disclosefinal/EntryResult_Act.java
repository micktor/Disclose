package disclose.org.disclosefinal;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import disclose.org.disclose69.R;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class EntryResult_Act extends AppCompatActivity {

    Double sad = 0.0;
    Double happy = 0.0;
    Double neutral = 0.0;
    Double analytical = 0.0;

    PieChartView pieChartView;

    EntryDatabase myDB;
    private TextView textView4;
    private Button button4;

    public void init(){
        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tie = new Intent(EntryResult_Act.this,Timeline_Act.class);
                startActivity(tie);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entryresult);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        textView4 = (TextView) findViewById(R.id.textView4);

        myDB = new EntryDatabase(this);

        showMessage();
        init();

        // PIE CHART
        pieChartView = findViewById(R.id.chart);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(happy.floatValue(), Color.GREEN).setLabel("JOY"));
        pieData.add(new SliceValue(neutral.floatValue(), Color.GRAY).setLabel("NEUTRAL"));
        pieData.add(new SliceValue(sad.floatValue(), Color.MAGENTA).setLabel("SADNESS"));
        pieData.add(new SliceValue(analytical.floatValue(), Color.BLUE).setLabel("ANALYTICAL"));


        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("Composite Mood").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#FFFFFF"));
        pieChartView.setPieChartData(pieChartData);
        // PIE CHART


    }



    private void showMessage() {
        Cursor data = myDB.getData();
        System.out.println(data);
        ArrayList<String> stringData = new ArrayList<>();
        ArrayList<Double> scoreData = new ArrayList<>();

        while (data.moveToNext()) {
            stringData.add(data.getString(2));
            scoreData.add(data.getDouble(3));
        }

        if(stringData.size()>0)textView4.append("Based on your journal entry, IBM Watson has detected your overall mood" +
                " as "+stringData.get(0) +" with a 0-1 strength of "+ scoreData.get(0)+
                " Click below to view your mood from the past!");

        System.out.println(stringData);
        System.out.println(scoreData);

        int i = 0;
        for (String mood: stringData){
            if (mood.equals("JOY")) happy = happy + scoreData.get(i);
            if (mood.equals("SADNESS")) sad = sad + scoreData.get(i);
            if (mood.equals("ANALYTICAL")) analytical = analytical + scoreData.get(i);
            if (mood.equals("NEUTRAL")) neutral = neutral + scoreData.get(i);
            i++;
        }

        Double sum = sad+happy+neutral+analytical;
        sad = (sad/sum);
        happy = (happy/sum);
        neutral = (neutral/sum);
        analytical = (analytical/sum);
    }
}
