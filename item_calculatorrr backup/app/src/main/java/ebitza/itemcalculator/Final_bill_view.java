package ebitza.itemcalculator;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vipul.hp_hp.library.Layout_to_Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import ebitza.itemcalculator.Adapter.Bill_adapter;
import ebitza.itemcalculator.Db_Helper.DBManager;
import ebitza.itemcalculator.Db_Helper.DatabaseHelper;
import ebitza.itemcalculator.Models.Model_bill;

public class Final_bill_view extends AppCompatActivity {
    private DBManager dbManager;
    private SimpleCursorAdapter adapter;
    SQLiteDatabase db;
    RelativeLayout relativeLayout;
    Layout_to_Image layout_to_image;
    DatabaseHelper databaseHelper;
    Bitmap bitmap;
    LinearLayout linearLayout, eroorLayout;
    RelativeLayout mainLa;
    ImageView imss;
    int total = 0;
    Button clickbalance;
    EditText Enter_balance;
    TextView Viewbalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_bill_view);
        linearLayout = (LinearLayout) findViewById(R.id.lltbl);
        TextView tv = (TextView) findViewById(R.id.tvtotalprice);
        final ProgressDialog pd = new ProgressDialog(Final_bill_view.this);
        pd.setMessage("Processing ...");
        relativeLayout = (RelativeLayout) findViewById(R.id.containers);
        mainLa = (RelativeLayout) findViewById(R.id.mainlayout);
        eroorLayout = (LinearLayout) findViewById(R.id.error_layout);
        Button go_back = (Button) findViewById(R.id.btn_go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent is = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(is);
                finish();
            }
        });
        // layout_to_image=new Layout_to_Image(Final_bill_view.this,relativeLayout);


        Viewbalance = (TextView) findViewById(R.id.tv_balance);
        Enter_balance = (EditText) findViewById(R.id.ed_enter_balance);
        Enter_balance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int a = Integer.parseInt(s.toString());
                int bill = total - a;
                if (bill < 0) {


                    Viewbalance.setText("" + bill);
                } else {
                    Viewbalance.setText(" " + bill);

                }

            }
        });


        final ListView recordsView = (ListView) findViewById(R.id.records_view);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        dbManager = new DBManager(getApplicationContext());
        dbManager.open();
        List<Model_bill> datalist = dbManager.get_generate_bill();
        if (datalist.size() == 0) {
            eroorLayout.setVisibility(View.VISIBLE);
            mainLa.setVisibility(View.GONE);
        } else {
            mainLa.setVisibility(View.VISIBLE);
            eroorLayout.setVisibility(View.GONE);

        }
        Bill_adapter bill_adapter = new Bill_adapter(getApplicationContext(), datalist);
        for (int i = 0; i < datalist.size(); i++) {

            int totz = Integer.parseInt(datalist.get(i).getTotal_amount());
            total += totz;

        }
        recordsView.setAdapter(bill_adapter);
        TextView totals = (TextView) findViewById(R.id.totalz);

        tv.setText("" + total);
        totals.setText("" + total);


        Button sharebill = (Button) findViewById(R.id.btn_share_bill);
        sharebill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.show();
                linearLayout.setVisibility(View.GONE);
                bitmap = ScreenshotUtils.getScreenShot(relativeLayout);


                pd.dismiss();
                String pack = "com.whatsapp";
                dbManager.CreateDynamicTablesmysales();
                dbManager.deletetable();
                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
                Uri bitmapUri = Uri.parse(bitmapPath);
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("image/*");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //  whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
                whatsappIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                try {
                    startActivityForResult(whatsappIntent, 0);
                    //finish();

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "App not Installed" + ex, Toast.LENGTH_SHORT).show();

                }
                //  onClickApp(pack,bitmap);



     /*   Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, bitmap);
        whatsappIntent.setType("image/jpeg");

        try {
          startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(),"Whatsapp have not been installed.",Toast.LENGTH_SHORT).show();
        }*/

            }
        });
    }

    private void onClickApp(String pack, Bitmap bitmap) {
        PackageManager pm = getPackageManager();
        try {


            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);


       /* String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        Uri imageUri = Uri.parse(path);*/

            @SuppressWarnings("unused")
            PackageInfo info = pm.getPackageInfo(pack, PackageManager.GET_META_DATA);

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            waIntent.setPackage(pack);
            //  waIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            // waIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            //  waIntent.putExtra(android.content.Intent.EXTRA_STREAM, contentUri);
            waIntent.putExtra(Intent.EXTRA_TEXT, pack);
            waIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (Exception e) {
            Log.e("Error on sharing", e + " ");
            Toast.makeText(getApplicationContext(), "App not Installed" + e, Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            Intent is = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(is);
            finish();
        }


    }
}


