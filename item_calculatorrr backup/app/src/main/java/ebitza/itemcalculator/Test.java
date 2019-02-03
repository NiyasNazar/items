package ebitza.itemcalculator;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test  extends AppCompatActivity {
    SharedPreferences prefs = null;
    Calendar alarmcalendar;
    String current_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("myAppName", MODE_PRIVATE);


        setTitle("Add Record");

        setContentView(R.layout.test);
        SimpleDateFormat formatter = new SimpleDateFormat(" yyyy-MM-dd");
        Date date = new Date();
        /*System.out.println(formatter.format(date));*/

        Log.i("times",formatter.format(date));



















        Button button = (Button) findViewById(R.id.click);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mp = MediaPlayer.create(Test.this, R.raw.testss);
                mp.start();

                AlertDialog.Builder b = new AlertDialog.Builder(Test.this);
                b.setMessage("test6");
                b.setCancelable(false);
                b.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                b.setTitle("hi");
                AlertDialog ad = b.create();
                ad.show();
            }

        });
//        android:digits="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"





        /*AlarmManager service = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, i,PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar cal = Calendar.getInstance();
        // Start 1 month after boot completed
        cal.add(Calendar.MONTH, 1);
        //
        // Fetch every 1 month
        // InexactRepeating allows Android to optimize the energy consumption
        service.setInexactRepeating(AlarmManager.RTC_WAKEUP ,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY , pending);
        */


        int a = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);















        /*final MediaPlayer mp = MediaPlayer.create(Test.this, R.raw.testss);
        mp.start();*/

    }


  /*  @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            Toast.makeText(getApplicationContext(), "firstrun", Toast.LENGTH_SHORT).show();

            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }


    }
    @Override
    protected void onStart() {
        super.onStart();




    }*/
}
