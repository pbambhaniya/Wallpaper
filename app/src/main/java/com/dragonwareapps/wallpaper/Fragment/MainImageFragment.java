package com.dragonwareapps.wallpaper.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dragonwareapps.wallpaper.Activity.ImageShowActivity;
import com.dragonwareapps.wallpaper.Model.WallpaperModel;
import com.dragonwareapps.wallpaper.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MainImageFragment extends Fragment {

    ImageView img;
    Context context;
    int currentImage = 0;
    WallpaperModel data;


    @SuppressLint("ValidFragment")
    public MainImageFragment(WallpaperModel data) {
        // Required empty public constructor
        this.data = data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_image, container, false);
        context = getActivity();
         img = (ImageView) view.findViewById(R.id.img);

        Picasso.with(context).load(data.getImage()).into(img);

        return view;

    }

}
