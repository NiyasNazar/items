package ebitza.itemcalculator;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

import ebitza.itemcalculator.Adapter.Adapter_for_item_list;
import ebitza.itemcalculator.Db_Helper.DBManager;
import ebitza.itemcalculator.Db_Helper.DatabaseHelper;
import ebitza.itemcalculator.Models.Model_category_item;

public class Fragment2 extends Fragment {
    private DBManager dbManager;
    private SimpleCursorAdapter adapter;
    SQLiteDatabase db;
    DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        final String strtext=getArguments().getString("message");

        View view=inflater.inflate(R.layout.fragment2, container, false);
        RecyclerView RCVcaterory_item=(RecyclerView)view.findViewById(R.id.recyclerview_category);
        RCVcaterory_item.setLayoutManager(new GridLayoutManager(getActivity(),2));
        dbManager = new DBManager(getActivity());
        dbManager.open();
        databaseHelper=new DatabaseHelper(getActivity());
        db=databaseHelper.getReadableDatabase();
        List<Model_category_item> datalist=dbManager.getAllitems(strtext);
        Adapter_for_item_list adapter=new Adapter_for_item_list(datalist,getActivity());
        RCVcaterory_item.setAdapter(adapter);








        FloatingActionButton fabs=(FloatingActionButton)view.findViewById(R.id.fab);
        fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  dbManager.CreateDynamicTables(strtext,"items","price");
                Intent intent=new Intent(getActivity(),Add_item_to_categories.class);
                intent.putExtra("strtext",strtext);
                startActivity(intent);

            }
        });

        TextView tv=(TextView)view.findViewById(R.id.fragment2text);
        tv.setText(strtext);
        return view;
    }


}