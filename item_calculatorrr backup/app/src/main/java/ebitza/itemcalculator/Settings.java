package ebitza.itemcalculator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
SharedPreferences prefs1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        prefs1 = getSharedPreferences("warning", MODE_PRIVATE);

        final Switch simpleSwitch = (Switch) findViewById(R.id.switches);


        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (simpleSwitch.isChecked()){
                    String   stat = simpleSwitch.getTextOn().toString();
                    Toast.makeText(getApplicationContext(),stat.toString(),Toast.LENGTH_SHORT).show();
                    prefs1.edit().putString("status","Yes").apply();


                }else{
                    String   stat = simpleSwitch.getTextOff().toString();
                    prefs1.edit().putString("status","No").apply();

                    Toast.makeText(getApplicationContext(),stat.toString(),Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}
