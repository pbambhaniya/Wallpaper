package com.dragonwareapps.wallpaper.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dragonwareapps.wallpaper.Adapter.WallpaperAdapter;
import com.dragonwareapps.wallpaper.Model.ImageModel;
import com.dragonwareapps.wallpaper.util.ItemClickListener;
import com.dragonwareapps.wallpaper.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeshBoardFragment extends Fragment implements ItemClickListener {
    RecyclerView rv_img;
    WallpaperAdapter adapter;
    Context context;
    private List<ImageModel> list;
    private ImageModel searchImageModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_desh_board, container, false);


        context = getActivity();
        rv_img = (RecyclerView) view.findViewById(R.id.rv_img);

        list = new ArrayList<>();
        BookMarkData();

        adapter = new WallpaperAdapter(list, context);
        GridLayoutManager mLayoutManagers = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
        rv_img.setLayoutManager(mLayoutManagers);
        rv_img.setItemAnimator(new DefaultItemAnimator());
        rv_img.setAdapter(adapter);
        adapter.setClickListener(this);

        return view;
    }

    private void BookMarkData() {
        list.add(new ImageModel(R.drawable.img1));
        list.add(new ImageModel(R.drawable.img1));
        list.add(new ImageModel(R.drawable.img1));
        list.add(new ImageModel(R.drawable.img1));
        list.add(new ImageModel(R.drawable.img1));
        list.add(new ImageModel(R.drawable.img1));
        list.add(new ImageModel(R.drawable.img1));
        list.add(new ImageModel(R.drawable.img1));
        list.add(new ImageModel(R.drawable.img1));
        list.add(new ImageModel(R.drawable.img1));
        list.add(new ImageModel(R.drawable.img1));
        list.add(new ImageModel(R.drawable.img1));
        //   adapter.notifyDataSetChanged();
    }

    @Override
    public void itemClicked(View View, int position) {
//        ImageModel searchImageModel=list.get(position);
        Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
    }
}
