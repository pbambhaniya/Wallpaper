package com.dragonwareapps.wallpaper.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dragonwareapps.wallpaper.util.ItemClickListener;
import com.dragonwareapps.wallpaper.R;
import com.dragonwareapps.wallpaper.Model.ImageModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 08-12-2017.
 */

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.MyViewHolder> {
    private Context context;
    private List<ImageModel> list;
    private ItemClickListener clickListener;


    public WallpaperAdapter(List<ImageModel> expertsList, Context context) {
        this.list = expertsList;
        this.context = context;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtlawyername;
        public ImageView img_lawyer;
        public RelativeLayout rel_root, rel_news;

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
    public WallpaperAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lawyer_list, parent, false);

        return new WallpaperAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WallpaperAdapter.MyViewHolder holder, int position) {
        ImageModel model = list.get(position);

//        Glide.with(context).load(list.get(position).getUserImage()).into(holder.img_profile);
        Picasso.with(context).load(list.get(position).getImg()).into(holder.img_lawyer);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
