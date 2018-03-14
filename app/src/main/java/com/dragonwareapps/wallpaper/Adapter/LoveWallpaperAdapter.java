package com.dragonwareapps.wallpaper.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dragonwareapps.wallpaper.Model.WallpaperModel;
import com.dragonwareapps.wallpaper.R;
import com.dragonwareapps.wallpaper.util.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RADHEKRISHNA on 2/18/2018.
 */

public class LoveWallpaperAdapter extends RecyclerView.Adapter<LoveWallpaperAdapter.MyViewHolder> {
    private Context context;
    private List<WallpaperModel> list;
    private ItemClickListener clickListener;


    public LoveWallpaperAdapter(List<WallpaperModel> expertsList, Context context) {
        this.list = expertsList;
        this.context = context;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView img_lawyer;

        public MyViewHolder(View view) {
            super(view);

            img_lawyer = (ImageView) view.findViewById(R.id.img_lawyer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.itemClicked(view, getAdapterPosition());
            }
        }
    }


    @Override
    public LoveWallpaperAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lawyer_list, parent, false);
        return new LoveWallpaperAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LoveWallpaperAdapter.MyViewHolder holder, int position) {
        WallpaperModel model = list.get(position);
        Picasso.with(context).load(list.get(position).getImage()).into(holder.img_lawyer);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

