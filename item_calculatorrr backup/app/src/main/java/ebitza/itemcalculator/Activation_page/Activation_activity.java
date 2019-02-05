package ebitza.itemcalculator.Activation_page;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ebitza.itemcalculator.MainActivity;
import ebitza.itemcalculator.MyPreferences;
import ebitza.itemcalculator.R;
import ebitza.itemcalculator.Test;

public class Activation_activity extends AppCompatActivity {
    String finalDateString;
    String strbody;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_activity);
   progress = new ProgressDialog(Activation_activity.this){
        @Override
        public void onBackPressed() {
            //progress.dismiss();
            Activation_activity.this.finish();

        }};


        progress.setTitle("Item Calculator Activation");
        progress.setMessage("Waiting For Activation...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
// To dismiss the dialog
       // progress.dismiss();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        /*System.out.println(formatter.format(date));*/

        Log.i("times",formatter.format(date));

      String  current_time=formatter.format(date);




           Check_message(current_time);
        Log.i("currentdate",current_time);
       // Log.i("final",finalDateString);
        Toast.makeText(getApplicationContext(),"hcurrentdateai"+current_time,Toast.LENGTH_SHORT).show();
     Toast.makeText(getApplicationContext(),"final"+finalDateString,Toast.LENGTH_SHORT).show();


       if (current_time.equals(finalDateString)){
            Log.i("prefernce","loaddate");
            if (strbody.equals("ABC")) {
                Intent is = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(is);
                final SharedPreferences reader = getSharedPreferences("activation", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = reader.edit();
                editor.putBoolean("is_first", true);
                editor.apply();
                progress.dismiss();
            }else{
                Log.i("prefernce","loadp");
                final SharedPreferences reader = getSharedPreferences("activation", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = reader.edit();
                editor.putBoolean("is_first", false);
                editor.apply();
            }
        }else{
            Log.i("prefernce","loaddateelse");
            Toast.makeText(getApplicationContext(),"wrong data",Toast.LENGTH_SHORT).show();

        }





    }

    private void Check_message(String current_time) {
        StringBuilder smsBuilder = new StringBuilder();
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_ALL = "content://sms/";
        try {
            Uri uri = Uri.parse(SMS_URI_INBOX);
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
            Cursor cur = getContentResolver().query(uri, projection, "address='+917012297229'", null, "date asc");
            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");
                do {
                    String strAddress = cur.getString(index_Address);
                    int intPerson = cur.getInt(index_Person);
 strbody = cur.getString(index_Body);
                    long longDate = cur.getLong(index_Date);
                    int int_Type = cur.getInt(index_Type);
                    Log.i("str",strAddress);
                    Log.i("str",strbody);
                    //Log.i("str",strAddress);

                  //  Toast.makeText(getApplicationContext(),""+strAddress,Toast.LENGTH_SHORT).show();

                    smsBuilder.append("[ ");
                    smsBuilder.append(strAddress + ", ");
                    smsBuilder.append(intPerson + ", ");
                    smsBuilder.append(strbody + ", ");
                    smsBuilder.append(longDate + ", ");
                    smsBuilder.append(int_Type);
                    smsBuilder.append(" ]\n\n");

                    DateFormat formatters = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(longDate);
             finalDateString = formatters.format(calendar.getTime());
                  //  Toast.makeText(getApplicationContext(),smsBuilder.toString(),Toast.LENGTH_SHORT).show();


                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
                smsBuilder.append("no result!");
            } // end if

        } catch (SQLiteException ex) {
            Log.d("SQLiteException", ex.getMessage());
        }







    }

   /* @Override
    public void onBackPressed() {
        progress.setCancelable(true);
        if (progress.isShowing()) {
            progress.dismiss();
            Activation_activity.this.finish();

        }
    }*/

}
