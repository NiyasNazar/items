package ebitza.itemcalculator.Search_views;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ebitza.itemcalculator.Db_Helper.DBManager;
import ebitza.itemcalculator.R;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private List<SearchResult> mItemList;
   private Context mc;
    String a;
    private DBManager dbManager;
    private TextView selected_quantity;
    public SearchResultAdapter(List<SearchResult> mItemList,Context mc) {
        this.mItemList = mItemList;
        this.mc=mc;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_search_result, parent, false);
        return new SearchResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        SearchResult result = mItemList.get(position);
        holder.mTitleTextView.setText(result.getTitle());
        holder.mDescriptionTextView.setText(result.getItem_Quantity());
        holder.mprice.setText(result.getItem_price());
        Log.i("resultA",result.getTitle());


    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private TextView mprice;
        CardView cardView;
        public SearchResultViewHolder(View itemView) {
            super(itemView);
            dbManager = new DBManager(mc);
            dbManager.open();
            mTitleTextView = (TextView) itemView.findViewById(R.id.textview_title);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.textview_description);
            mprice = (TextView) itemView.findViewById(R.id.textview_price);
            cardView=(CardView)itemView.findViewById(R.id.cd_search);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();

                    final String itemid=mItemList.get(position).getItem_id();
                    final String itemname=mItemList.get(position).getTitle();
                    final String itemprice=mItemList.get(position).getItem_price();
                    final String itemquantity=mItemList.get(position).getItem_Quantity();
                    LayoutInflater inflater = (LayoutInflater) mc.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View alertLayout = inflater.inflate(R.layout.addproduct_from_search, null);
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
                    AlertDialog.Builder alert = new AlertDialog.Builder(mc);
                    alert.setTitle("Info");
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch


                    final AlertDialog dialog = alert.create();
                    dialog.show();
                    Button alertdialogconfir=alertLayout.findViewById(R.id.btn_alert_dialog_confirm);
                    alertdialogconfir.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("testid",itemid);
                            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                            Toast.makeText(mc,"id"+itemid,Toast.LENGTH_SHORT).show();

                            String pp=tvItemprice.getText().toString();
                            a=selected_quantity.getText().toString();

                            if (a.equals("0")){
                                Toast.makeText(mc,"Quantity must be greater than zero",Toast.LENGTH_SHORT).show();
                            }else {
                                int q=Integer.parseInt(a);
                                int p=Integer.parseInt(pp);
                                int tot=p*q;
                                String total=String.valueOf(tot);




                                dbManager.additemsforbilling(itemname,itemprice,a,total,date);
                                dialog.dismiss();
                            }

                            //   dbManager.updatebilltable(itemid,a,total,itemname,itemprice);


                        }
                    });








                }
            });

        }
    }



    public void addAll(Collection<SearchResult> items) {
        int currentItemCount = mItemList.size();
        mItemList.addAll(items);
        notifyItemRangeInserted(currentItemCount, items.size());
    }

    public void addAll(int position, Collection<SearchResult> items) {
        int currentItemCount = mItemList.size();
        if(position > currentItemCount)
            throw new IndexOutOfBoundsException();
        else
            mItemList.addAll(position, items);
        notifyItemRangeInserted(position, items.size());
    }

    public void replaceWith(Collection<SearchResult> items) {
        replaceWith(items, false);
    }

    public void clear() {
        int itemCount = mItemList.size();
        mItemList.clear();
        notifyItemRangeRemoved(0, itemCount);
    }


    public void replaceWith(Collection<SearchResult> items, boolean cleanToReplace) {
        if(cleanToReplace) {
            clear();
            addAll(items);
        } else {
            int oldCount = mItemList.size();
            int newCount = items.size();
            int delCount = oldCount - newCount;
            mItemList.clear();
            mItemList.addAll(items);
            if(delCount > 0) {
                notifyItemRangeChanged(0, newCount);
                notifyItemRangeRemoved(newCount, delCount);
            } else if(delCount < 0) {
                notifyItemRangeChanged(0, oldCount);
                notifyItemRangeInserted(oldCount, - delCount);
            } else {
                notifyItemRangeChanged(0, newCount);
            }
        }
    }
}