package com.example.nessihazan.myapplication;

/**
 * Created by nessihazan on 05/12/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nessihazan.myapplication.model.DataItem;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    public   static final  String ITEM_ID_KEY = "item_id_key";
    public static final String ITEM_KEY ="item_key" ;
    private List<DataItem> mItems;
    private Context mContext;

    public DataItemAdapter(Context context, List<DataItem> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean grid = settings.getBoolean(mContext.getString(R.string.pref_display_in_grid),false);
        int layoutId = grid?R.layout.grid_item:R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final DataItemAdapter.ViewHolder holder, int position) {
        final DataItem item = mItems.get(position);

        try {
            holder.tvName.setText(item.getItemName());
            String imageFile = item.getImage();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
holder.mView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        Toast.makeText(mContext,"you selected " + item.getItemName(),Toast.LENGTH_SHORT).show();

// String itemId = item.getItemId();
        Intent intent = new Intent(mContext,DetailActivity.class);
            intent.putExtra(ITEM_KEY,item);
        mContext.startActivity(intent);
    }
});


   holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
       @Override
       public boolean onLongClick(View v) {

           Toast.makeText(mContext,"you long clicked " + item.getItemName(),Toast.LENGTH_SHORT).show();

           return false;
       }
   });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView tvName;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.itemNameText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            mView= itemView;
        }
    }}