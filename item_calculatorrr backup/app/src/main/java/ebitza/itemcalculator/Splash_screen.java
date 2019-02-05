package ebitza.itemcalculator;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ebitza.itemcalculator.Activation_page.Activation_activity;

public class Splash_screen extends AppCompatActivity {
    String TAG="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final SharedPreferences reader = getSharedPreferences("activation", Context.MODE_PRIVATE);
        final boolean activated = reader.getBoolean("is_first", false);
        
        
        
        boolean isFirstTime = MyPreferences.isFirst(Splash_screen.this);
        if (isFirstTime){
            if (Build.VERSION.SDK_INT >= 23) {

            isSMSPermissionGranted();
            }
            else{



                  Intent is=new Intent(getApplicationContext(), Activation_activity.class);
                  startActivity(is);
                  finish();






            }

        }else{
            if(activated) {
                Log.i("through","through");
                Intent is = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(is);
                finish();
            }else{
                Log.i("notactivated","notactivated");
                Intent is=new Intent(getApplicationContext(), Activation_activity.class);
                startActivity(is);
                finish();
            }

        }
    }

    private boolean isSMSPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted2");
                return true;
            } else {

                Log.v("TAG","Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 2);
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
                    Intent is=new Intent(getApplicationContext(), Activation_activity.class);
                    startActivity(is);
                    finish();
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





}