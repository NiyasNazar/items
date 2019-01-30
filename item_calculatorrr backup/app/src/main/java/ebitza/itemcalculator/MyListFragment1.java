package ebitza.itemcalculator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ebitza.itemcalculator.Db_Helper.DBManager;
import ebitza.itemcalculator.Db_Helper.DatabaseHelper;
import ebitza.itemcalculator.Models.Model_category;

public class MyListFragment1 extends Fragment {
    private DBManager dbManager;
    private SimpleCursorAdapter adapter;
    SQLiteDatabase db;
    ListView lv;
    int defaultPositon = 0;
    int justIgnoreId = 0;
DatabaseHelper databaseHelper;
    List<Model_category> labels;
    final String[] from = new String[] {DatabaseHelper._ID,
            DatabaseHelper.SUBJECT, DatabaseHelper.DESC };
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String>();

    final int[] to = new int[] { R.id.title};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DBManager(getActivity());
        dbManager.open();
        databaseHelper=new DatabaseHelper(getActivity());
        db=databaseHelper.getReadableDatabase();
        Log.i("read","readingdbs");
        //ListView listView=(ListView)view.findViewById(R.id.mylist);

     // lv.setSelection(0);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listfragment1, container, false);
        lv=(ListView) view.findViewById(R.id.lista);
     labels = dbManager.getAllLabels();
        final CustomAdapter dataAdapter=new CustomAdapter(getActivity(),labels);
       /* final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),R.layout.listview_item_category, labels);
      lv.  setAdapter(dataAdapter);*/
       lv.setAdapter(dataAdapter);


if(!dataAdapter.isEmpty()) {
    String value = labels.get(0).getCategory_name();
    Loadfirstposition(value);
    lv.setItemChecked(defaultPositon, true);
}
  /*  lv.setItemChecked(defaultPositon, true);
        lv.performItemClick(lv.getSelectedView(), defaultPositon, justIgnoreId);*/



        ImageView add_category_btn = (ImageView) view.findViewById(R.id.addcat);
        add_category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent is = new Intent(getActivity(), Addcategory.class);
                startActivity(is);
                getActivity().finish();

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String data=labels.get(position).getCategory_name();
                Bundle bundle=new Bundle();
                bundle.putString("message", data);
                Fragment2  myListFragment1=new Fragment2();
                myListFragment1.setArguments(bundle);
                getActivity().  getSupportFragmentManager().beginTransaction().replace(R.id.fragment2,myListFragment1).commit();

            }
        });


        return view;
    }


    private void Loadfirstposition(String value) {
        lv.setItemChecked(defaultPositon, true);
        Bundle bundle=new Bundle();
        bundle.putString("message", value);
        Fragment2  myListFragment1=new Fragment2();
        myListFragment1.setArguments(bundle);
        getActivity().  getSupportFragmentManager().beginTransaction().replace(R.id.fragment2,myListFragment1).commit();




    }








}
