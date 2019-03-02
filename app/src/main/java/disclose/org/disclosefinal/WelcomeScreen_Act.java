package disclose.org.disclosefinal;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import disclose.org.disclose69.R;

public class WelcomeScreen_Act extends AppCompatActivity {
    String TAG = "wsap"; //for logd debugging
    Button buttonAnalyzeMsg;
    Button buttonMoodEntry;
    Button button3;

    public void init(){
        buttonAnalyzeMsg = (Button)findViewById(R.id.buttonAnalyzeMsg);
        buttonAnalyzeMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent tie = new Intent(WelcomeScreen_Act.this,ContactSelect_Act.class);
                Intent tie = new Intent(WelcomeScreen_Act.this,SearchSmsAct.class);
                startActivity(tie);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
    }
    public void init2(){
        buttonMoodEntry = (Button)findViewById(R.id.button2);
        buttonMoodEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: wsap");
                Intent tie = new Intent(WelcomeScreen_Act.this,MoodEntry_Act.class);
                startActivity(tie);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
    }
    public void init3(){
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tie = new Intent(WelcomeScreen_Act.this,Timeline_Act.class);
                startActivity(tie);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomescreen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        init();
        init2();
        init3();
    }

}
