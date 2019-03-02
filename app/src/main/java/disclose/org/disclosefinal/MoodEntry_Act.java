package disclose.org.disclosefinal;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.AbstractSequentialList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import disclose.org.disclose69.R;

public class MoodEntry_Act extends AppCompatActivity {
    private Button button;
    private EditText editText;
    EntryDatabase myDB;
    private AnalysisTask analysisTask;
    private static final String TAG = "meat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: 1");
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: 2");
        setContentView(R.layout.moodentry);
        Log.d(TAG, "onCreate: MoodEntry_Act");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        myDB = new EntryDatabase(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entry = editText.getText().toString();

                if (editText.length() != 0) {
                    addEntry(entry);
                }
                Intent tie = new Intent(MoodEntry_Act.this, EntryResult_Act.class);
                finish();
                startActivity(tie);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


            }
        });

    }

    public void addEntry(String entry){
        HashMap<String, Double> map = new HashMap<>();
        System.out.println(entry);
        analysisTask = new AnalysisTask();
        analysisTask.execute(entry);

        try {
            map = analysisTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String mood = "Neutral";
        Double score = 0.5;
        Set< Map.Entry< String, Double> > st = map.entrySet();
        for (Map.Entry< String,Double> me:st)
        {
            mood = me.getKey();
            score = me.getValue();
            System.out.print(me.getKey()+":");
            System.out.println(me.getValue());
        }

        //myDB.addData(entry, "testiness", 0.0);
        myDB.addData(entry, mood, score);

    }
}
