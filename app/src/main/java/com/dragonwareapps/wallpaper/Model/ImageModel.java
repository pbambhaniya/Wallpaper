package com.dragonwareapps.wallpaper.Model;

/**
 * Created by Admin on 08-12-2017.
 */

public class ImageModel {

    int  img;

    public ImageModel(int img) {

        this.img = img;

    }

    boolean ischeck;




    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }
}
