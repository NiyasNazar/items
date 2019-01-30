package ebitza.itemcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView=(ImageView)findViewById(R.id.imageview);
        MyListFragment1  myListFragment1=new MyListFragment1();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment1,myListFragment1).commit();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent is=new Intent(getApplicationContext(),Remove_category.class);
                startActivity(is);
                finish();
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
    @Override
    public void onBackPressed() {


        finish();
    }

    }
