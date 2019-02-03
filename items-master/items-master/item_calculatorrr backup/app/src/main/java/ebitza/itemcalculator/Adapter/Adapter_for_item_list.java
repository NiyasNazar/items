package ebitza.itemcalculator.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.Arrays;
import java.util.List;

import ebitza.itemcalculator.Db_Helper.DBManager;
import ebitza.itemcalculator.Db_Helper.DatabaseHelper;
import ebitza.itemcalculator.Models.Model_category_item;
import ebitza.itemcalculator.R;

public class Adapter_for_item_list extends RecyclerView.Adapter<Adapter_for_item_list.MyViewHolder> {
    private List<Model_category_item> itemList;
    private DBManager dbManager;
    private SimpleCursorAdapter adapter;
    SQLiteDatabase db;
     String a;
 TextView selected_quantity;
    DatabaseHelper databaseHelper;
    Context mcontext;
    public Adapter_for_item_list(List<Model_category_item> itemList, Context mcontext)
    {
        this.itemList = itemList;
        this.mcontext=mcontext;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, itemquantity, genre;
        RelativeLayout ll;
        ImageView imv;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.row_item_name);
            genre = (TextView) view.findViewById(R.id.row_item_price);
            itemquantity=(TextView)view.findViewById(R.id.row_item_quantity);
            imv=(ImageView)view.findViewById(R.id.imvv);
            ll=(RelativeLayout)view.findViewById(R.id.lllayouts);
            dbManager = new DBManager(mcontext);
            dbManager.open();
            databaseHelper=new DatabaseHelper(mcontext);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbManager.CreateDynamicTablesforbill();
                    int position=getAdapterPosition();

                    final String itemid=itemList.get(position).getItem_id();
                    final String itemname=itemList.get(position).getItemname();
                    final String itemprice=itemList.get(position).getItemprice();
                    final String itemquantity=itemList.get(position).getItemquantity();
                    byte imgs[]=itemList.get(position).get_img();
                  //  dbManager.additemsforbilling(itemname,itemprice,"0","50");
                    LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View alertLayout = inflater.inflate(R.layout.sample, null);
                 ImageView implus=alertLayout.findViewById(R.id.imvplus);
                   selected_quantity=alertLayout.findViewById(R.id.tvqua);

                    implus.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         int a= Integer.parseInt(selected_quantity.getText().toString());
                         a=a+1;
                         selected_quantity.setText(""+a);

                     }
                 });
                    ImageView imminus=alertLayout.findViewById(R.id.imvminus);
                    imminus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int a= Integer.parseInt(selected_quantity.getText().toString());
                            if (a>0) {
                                a = a - 1;
                                selected_quantity.setText("" + a);
                            }


                        }
                    });

                    final TextView tvItemname = alertLayout.findViewById(R.id.dialog_item_name);

                    final TextView quantity=alertLayout.findViewById(R.id.itemq);
                    quantity.setText(itemquantity);
                    final ImageView imv_item_image = alertLayout.findViewById(R.id.dialog_imv);

                    tvItemname.setText(itemname);

                    final TextView tvItemprice = alertLayout.findViewById(R.id.dialog_item_price);

tvItemprice.setText(itemprice);
if (imgs==null){

}else{

    Bitmap bmp = BitmapFactory.decodeByteArray(imgs, 0, imgs.length);

    imv_item_image.setImageBitmap(bmp);
}

                    Button alertdialogconfir=alertLayout.findViewById(R.id.btn_alert_dialog_confirm);
                    alertdialogconfir.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("testid",itemid);
                            Toast.makeText(mcontext,"id"+itemid,Toast.LENGTH_SHORT).show();

                            String pp=tvItemprice.getText().toString();
                            a=selected_quantity.getText().toString();

if (a.equals("0")){
    Toast.makeText(mcontext,"Quantity must be greater than zero",Toast.LENGTH_SHORT).show();
}else {
    int q=Integer.parseInt(a);
    int p=Integer.parseInt(pp);
    int tot=p*q;
    String total=String.valueOf(tot);
    dbManager.additemsforbilling(itemname,itemprice,a,total);
}

                         //   dbManager.updatebilltable(itemid,a,total,itemname,itemprice);

                        }
                    });
                    AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
                    alert.setTitle("");
                    alert.setView(alertLayout);
                    alert.setCancelable(true);
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            });

        }
    }



    boolean tableExists(SQLiteDatabase db, String tableName)
    {
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM JOURNALDEV_COUNTRIES WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Model_category_item movie = itemList.get(position);
        holder.title.setText(movie.getItemname());
        holder.genre.setText(movie.getItemprice());
        holder.itemquantity.setText(movie.getItemquantity());

        byte imgs[]=movie.get_img();
        if (imgs==null){

        }else{
            Bitmap bmp = BitmapFactory.decodeByteArray(imgs, 0, imgs.length);

            holder.imv.setImageBitmap(bmp);
            Log.i("checkimagssse", Arrays.toString(imgs));
            //   holder.imv.setImageBitmap(convertToBitmap(movie.get_img()));
        }


    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }
    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }
}