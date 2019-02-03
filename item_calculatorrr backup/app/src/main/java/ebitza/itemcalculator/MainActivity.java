package ebitza.itemcalculator;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
String TAG="TAG";
    String table_name;
    SharedPreferences prefs = null;
    Calendar alarmcalendar;
    String current_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("myAppName", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        int a= Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat formatter = new SimpleDateFormat(" yyyy-MM-dd");
        Date date = new Date();
        /*System.out.println(formatter.format(date));*/

        Log.i("times",formatter.format(date));

        current_time=formatter.format(date);
        SimpleDateFormat formatter2 = new SimpleDateFormat(" yyyy-MM");
        Date date2 = new Date();
        /*System.out.println(formatter.format(date));*/

        Log.i("times",formatter.format(date2));
String setdate=formatter2.format(date2);
String notidate=setdate+"-"+a;

        Log.i("timessss",notidate);
        Toast.makeText(getApplicationContext(),"tt"+notidate,Toast.LENGTH_SHORT).show();


        String launchdate=prefs.getString("launch",null);
        // Log.i("timess",launchdate);
        if (current_time.equals(notidate)){
            final MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.testss);
            mp.start();
            prefs.edit().putString("launch",current_time).apply();
            Log.i("timesss",prefs.getString("launch",null));
            AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
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
            prefs.edit().putString("lauch",current_time).apply();
        }else{



        }
        Intent is=getIntent();
     /*   if (getIntent().getExtras()!=null) {
          table_name = is.getStringExtra("str");
            Toast.makeText(getApplicationContext(),"tbl"+table_name,Toast.LENGTH_SHORT).show();
            Bundle bundle=new Bundle();
            bundle.putString("message", table_name);
            myListFragment1.setArguments(bundle);

        }*/
        isWriteStoragePermissionGranted();
        isReadStoragePermissionGranted();
        final ImageView imageView=(ImageView)findViewById(R.id.imageview);
        MyListFragment1  myListFragment1=new MyListFragment1();
        if (getIntent().getExtras()!=null) {
            table_name = is.getStringExtra("str");
            Toast.makeText(getApplicationContext(),"tbl"+table_name,Toast.LENGTH_SHORT).show();
            Bundle bundle=new Bundle();
            bundle.putString("message", table_name);
            myListFragment1.setArguments(bundle);

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment1,myListFragment1).commit();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MainActivity.this, imageView);
                popup.getMenuInflater()
                        .inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.one:
                                Intent is=new Intent(getApplicationContext(),Remove_category.class);
                                startActivity(is);
                                finish();
                                break;
                            case R.id.two:
                                Intent is2=new Intent(getApplicationContext(),Remove_items.class);
                                startActivity(is2);
                                finish();
                                break;
                            case R.id.three:
                                Intent sales=new Intent(getApplicationContext(),Salesview_activity.class);
                                startActivity(sales);
                                break;

                        }

                        return true;
                    }

                });

                popup.show();


               /*
              */
            }
        });
        Button btn=(Button)findViewById(R.id.btn_final_bill);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is=new Intent(getApplicationContext(),Final_bill_view.class);
                startActivity(is);

            }
        });
    }

    @Override
    public void onClick(View view) {

        }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted1");
                return true;
            } else {

                Log.v("TAG","Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted2");
                return true;
            } else {

                Log.v("TAG","Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted2");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    //downloadPdfFile();
                }else{
                   // progress.dismiss();
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                   // SharePdfFile();
                }else{
                   // progress.dismiss();
                }
                break;
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }




}

