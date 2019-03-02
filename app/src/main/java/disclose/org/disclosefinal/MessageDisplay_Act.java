package disclose.org.disclosefinal;

import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import disclose.org.disclose69.R;

public class MessageDisplay_Act extends AppCompatActivity {

    private ListView listView;
    private AnalysisTask analysisTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagedisplay);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Bundle b = getIntent().getExtras();
        int number = b.getInt("Thread");

        this.listView = (ListView) findViewById(R.id.listView);
        SMSDatabaseAccess SMSDatabaseAccess = disclose.org.disclosefinal.SMSDatabaseAccess.getInstance(this);
        SMSDatabaseAccess.open();
        List<String> messages = SMSDatabaseAccess.getMessages(number);
        SMSDatabaseAccess.close();

        System.out.println(messages);

        List<String> scores = new ArrayList<String>();

        int msgsize = messages.size();

        for (int i = 0; i < msgsize; i++) {
            System.out.println(messages.get(i));
            analysisTask = new AnalysisTask();
            analysisTask.execute(messages.get(i));

            String s = "";

            try {
                analysisTask.get();
                System.out.println(analysisTask.get()+"");
                s = analysisTask.get()+"";

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //messages.add(s);
            scores.add(s);

        }
        System.out.println(scores);

        for(int i = 0;i < scores.size();i++){
            messages.add(i+i, scores.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, messages);
        this.listView.setAdapter(adapter);

    }
}
