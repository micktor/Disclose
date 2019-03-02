package disclose.org.disclosefinal;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import disclose.org.disclose69.R;

//ReadSMS is working, we just need to attatch it to one of the buttons on the Home Screen.
public class ReadSMS extends AppCompatActivity {
    private static final String TAG = "rsms";
    ListView listView;
    private static final int PERMISSION_REQUEST_READ_CONTACTS = 100;
    ArrayList smsList;
    private AnalysisTask analysisTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_sms);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        listView = (ListView) findViewById(R.id.idList);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: 2");
            showContacts(SearchSmsAct.phoneNumber);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, PERMISSION_REQUEST_READ_CONTACTS);
        }

    }

    private void showContacts(String number) {
        Uri inboxURI = null;
        if (SearchSmsAct.rbResult == SearchSmsAct.THEIR) inboxURI = Uri.parse("content://sms/inbox");
        if (SearchSmsAct.rbResult == SearchSmsAct.YOUR) inboxURI = Uri.parse("content://sms/sent");
        if (SearchSmsAct.rbResult == SearchSmsAct.BOTH) inboxURI = Uri.parse("content://sms/");

        //Uri inboxURI = Uri.parse("content://sms");
        //Uri outboxURI = Uri.parse("content://sms/sent");
        smsList = new ArrayList();
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(inboxURI, null, null, null, "date asc");
        //Cursor o = cr.query(outboxURI, null, null, null, null);

//        while (o.moveToNext()) {
//            String Number = o.getString(o.getColumnIndexOrThrow("address")).toString();
//            Long threadID = o.getLong(1);
//            if (Number.equals(number.replaceAll("\\D+",""))){
//                String Body = o.getString(o.getColumnIndexOrThrow("body")).toString();
//                smsList.add("Number: " + Number + "\n" + "Body: " + Body + "\n" + threadID);
//                analysisTask = new AnalysisTask();
//                analysisTask.execute(Body);
//                String s = "";
//
//                try {
//                    analysisTask.get();
//                    System.out.println(analysisTask.get()+"");
//                    s = analysisTask.get()+"";
//
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                smsList.add(s);
//
//            }
//        }

        while (c.moveToNext()) {
            String Number = c.getString(c.getColumnIndexOrThrow("address")).toString();
            Long threadID = c.getLong(1);
            Long timestamp = c.getLong(4);
            if (Number.equals(number.replaceAll("\\D+",""))){
            String Body = c.getString(c.getColumnIndexOrThrow("body")).toString();
            //smsList.add("Number: " + Number + "\n" + "Body: " + Body + "\n" + "ThreadID: " +threadID + "\n" + "Timestamp: " + timestamp);
                analysisTask = new AnalysisTask();
                analysisTask.execute(Body);
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

                Date date = new Date(timestamp);
                SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd");
                jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
                String java_date = jdf.format(date);

                //smsList.add(s);
                //smsList.add("Number: " + Number + "\n" + "Body: " + Body + "\n" + "ThreadID: " +threadID + "\n" + "Timestamp: " + timestamp + "\n" + "ANALYSIS: " + s);
                smsList.add("Body: " + Body + "\n" + "Timestamp: " + java_date + "\n" + "ANALYSIS: " + s);
                smsList.add("");


            }®
        }

        c.close();
//        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.our_items, smsList);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, smsList);
        listView.setAdapter(adapter);
    }
}