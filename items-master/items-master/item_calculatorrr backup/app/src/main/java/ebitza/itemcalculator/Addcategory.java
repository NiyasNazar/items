package ebitza.itemcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ebitza.itemcalculator.Db_Helper.DBManager;

public class Addcategory extends AppCompatActivity implements View.OnClickListener {

    private Button addTodoBtn;
    private EditText categoryEditText;
    private EditText descEditText;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");

        setContentView(R.layout.addcat);

        categoryEditText = (EditText) findViewById(R.id.category_edittext);


        addTodoBtn = (Button) findViewById(R.id.add_record);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:

                final String name = categoryEditText.getText().toString();


                dbManager.addcategory(name);
                categoryEditText.setText("");

                /*Intent main = new Intent(Addcategory.this, MainActivity.class);


                startActivity(main);
                finish();*/
                break;
        }
    }
    @Override
    public void onBackPressed() {

        Intent is=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(is);
        finish();
    }

}