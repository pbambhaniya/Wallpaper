package com.dragonwareapps.wallpaper.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dragonwareapps.wallpaper.Activity.ImageShowActivity;
import com.dragonwareapps.wallpaper.Fragment.MainImageFragment;
import com.dragonwareapps.wallpaper.Model.WallpaperModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 24-02-2018.
 */

public class Pager extends FragmentStatePagerAdapter {

    // tab titles
    List<WallpaperModel> list = new ArrayList<>();

    public Pager(FragmentManager fm, List<WallpaperModel> list) {
        super(fm);
        this.list = list;
    }

    // overriding getPageTitle()
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return new MainImageFragment(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }
}




