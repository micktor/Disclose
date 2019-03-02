package disclose.org.disclosefinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import disclose.org.disclose69.R;

public class ContactSelect_Act extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactselect);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.listView = (ListView) findViewById(R.id.listView);

        SMSDatabaseAccess SMSDatabaseAccess = disclose.org.disclosefinal.SMSDatabaseAccess.getInstance(this);
        SMSDatabaseAccess.open();
        List<String> threads = SMSDatabaseAccess.getThreads();
        final List<Integer> thids = SMSDatabaseAccess.getThId();
        SMSDatabaseAccess.close();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, threads);
        this.listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position, long id) {
                Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
                // Then you start a new Activity via Intent
                Intent intent = new Intent();
                intent.setClass(ContactSelect_Act.this, MessageDisplay_Act.class);
                intent.putExtra("position", position);

                intent.putExtra("Number", adapter.getItem((int) id));
                intent.putExtra("Thread", thids.get((int) id));


                // Or / And
                intent.putExtra("id", id);
                System.out.println(adapter.getItem((int) id));
                System.out.println(thids.get((int) id));
                startActivity(intent);
            }
        });

    }




}
