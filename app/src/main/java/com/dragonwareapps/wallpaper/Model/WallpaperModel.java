package com.dragonwareapps.wallpaper.Model;

/**
 * Created by RADHEKRISHNA on 2/18/2018.
 */

public class WallpaperModel {

    String wp_wallpaper_id;
    String wp_category_id;
    String name;
    String title;
    String sub_title;
    String image;
    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getIs_rating() {
        return is_rating;
    }

    public void setIs_rating(String is_rating) {
        this.is_rating = is_rating;
    }

    public String getIs_popular() {
        return is_popular;
    }

    public void setIs_popular(String is_popular) {
        this.is_popular = is_popular;
    }

    public String getIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(String is_favourite) {
        this.is_favourite = is_favourite;
    }

    String is_new;
    String is_rating;
    String is_popular;
    String is_favourite;


    public String getWp_wallpaper_id() {
        return wp_wallpaper_id;
    }

    public void setWp_wallpaper_id(String wp_wallpaper_id) {
        this.wp_wallpaper_id = wp_wallpaper_id;
    }

    public String getWp_category_id() {
        return wp_category_id;
    }

    public void setWp_category_id(String wp_category_id) {
        this.wp_category_id = wp_category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

}
