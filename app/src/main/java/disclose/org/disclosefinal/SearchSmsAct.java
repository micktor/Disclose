package disclose.org.disclosefinal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

import disclose.org.disclose69.R;

public class SearchSmsAct extends AppCompatActivity {
    EditText etSelectContact;
//    EditText etStartDate;
//    EditText etEndDate;
    Button btnAnalyzeSms;
//    DatePicker dpStartDate;
    RadioButton rbTheir;
    RadioButton rbYour;
    RadioButton rbBoth;

    static String phoneNumber;
//    static String startDate;
//    static String endDate;
    static int rbResult;

    final static int YOUR = 0;
    final static int THEIR = 1;
    final static int BOTH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_sms_act);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        dpStartDate = (DatePicker) findViewById(R.id.datePickerStartDate);
//        dpStartDate.setSpinnersShown(false);
//        etStartDate = (EditText) findViewById(R.id.editTextStartDate);
//        etEndDate = (EditText) findViewById(R.id.editTextEndDate);

        rbBoth = (RadioButton) findViewById(R.id.radioButtonBoth);
        rbTheir= (RadioButton) findViewById(R.id.radioButtonTheir);
        rbYour = (RadioButton) findViewById(R.id.radioButtonYour);
        etSelectContact = (EditText) findViewById(R.id.editTextSelectContact);
        btnAnalyzeSms = (Button) findViewById(R.id.buttonAnalyzeSms);
    }

    public void onClickSearchSms(View view) {
        Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContact, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri contactData = data.getData();
        Cursor c = getContentResolver().query(contactData, null, null, null, null);
        if (c.moveToFirst()) {
            int phoneIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String num = c.getString(phoneIndex);
//            Toast.makeText(SearchSmsAct.this, "Number=" + num, Toast.LENGTH_LONG).show();
            etSelectContact.setText(num);
            phoneNumber = num;
        }
    }


    public void onClickAnalyzeSms(View view) {
        if (rbBoth.isChecked()){
            rbResult = BOTH;
        }else if (rbYour.isChecked()) {
            rbResult = YOUR;
        }else if (rbTheir.isChecked()){
            rbResult = THEIR;
        }else {
            rbResult = -1;
        }

        Intent tie = new Intent(SearchSmsAct.this, ReadSMS.class);
        startActivity(tie);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

