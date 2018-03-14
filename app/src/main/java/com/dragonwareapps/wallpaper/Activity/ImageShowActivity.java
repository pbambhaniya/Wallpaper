package com.dragonwareapps.wallpaper.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.dragonwareapps.wallpaper.Adapter.LoveWallpaperAdapter;
import com.dragonwareapps.wallpaper.Adapter.Pager;
import com.dragonwareapps.wallpaper.Fragment.AllImageFragment;
import com.dragonwareapps.wallpaper.Model.WallpaperModel;
import com.dragonwareapps.wallpaper.R;
import com.dragonwareapps.wallpaper.util.AppController;
import com.dragonwareapps.wallpaper.util.Config;
import com.dragonwareapps.wallpaper.util.DownloadTask;
import com.dragonwareapps.wallpaper.util.Shared;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageShowActivity extends AppCompatActivity {
    ImageView img_favourite;
    Context context;
    WallpaperModel model;
    List list = new ArrayList<>();
    int currentPosition = 0;
    Shared shared;
    private ViewPager viewPager;
    String param;
    ProgressDialog dialog;
    String clickd;
    LinearLayout lnr_set_wallpaper;
    String img, id;
    ProgressDialog mProgressDialog;
    RelativeLayout rel_root;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_show);
        context = this;
        shared = new Shared(context);
        viewPager = (ViewPager) findViewById(R.id.pager);
        lnr_set_wallpaper = (LinearLayout) findViewById(R.id.lnr_set_wallpaper);
        rel_root = (RelativeLayout) findViewById(R.id.rel_root);

        img_favourite = (ImageView) findViewById(R.id.img_favourite);
        clickd = getIntent().getStringExtra("click");
        getIntent().getStringExtra("android_id");
        //shared

        img_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFavourite();

            }
        });
        currentPosition = getIntent().getIntExtra("id", 0);

//        txt_set_wallpaper.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bitmap result= null;
//                try {
//                    result = Picasso.with(context)
//                            .load(model.getImage())
//                            .get();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
//                try {
//                    wallpaperManager.setBitmap(result);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });

        try {
            JSONObject jsonArray = new JSONObject(shared.getString(Config.img, "[{}]"));

            if (clickd.contentEquals("all_image")) {
                JSONArray array = jsonArray.getJSONArray("is_new");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject wallpaper = array.getJSONObject(i);
                    model = new WallpaperModel();
                    model.setWp_wallpaper_id(wallpaper.getString("wp_wallpaper_id"));
                    model.setWp_category_id(wallpaper.getString("wp_category_id"));
                    model.setName(wallpaper.getString("name"));
                    model.setTitle(wallpaper.getString("title"));
                    model.setSub_title(wallpaper.getString("sub_title"));
                    model.setImage(wallpaper.getString("image"));
                    model.setDescription(wallpaper.getString("description"));
                    model.setIs_new(wallpaper.getString("is_new"));
                    model.setIs_rating(wallpaper.getString("is_rating"));
                    model.setIs_popular(wallpaper.getString("is_popular"));
                    model.setIs_favourite(wallpaper.getString("is_favourite"));
                    list.add(model);
                }

            } else if (clickd.contentEquals("rating_image")) {
                JSONArray array = jsonArray.getJSONArray("is_rating");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject wallpaper = array.getJSONObject(i);
                    model = new WallpaperModel();
                    model.setWp_wallpaper_id(wallpaper.getString("wp_wallpaper_id"));
                    model.setWp_category_id(wallpaper.getString("wp_category_id"));
                    model.setName(wallpaper.getString("name"));
                    model.setTitle(wallpaper.getString("title"));
                    model.setSub_title(wallpaper.getString("sub_title"));
                    model.setImage(wallpaper.getString("image"));
                    model.setDescription(wallpaper.getString("description"));
                    model.setIs_new(wallpaper.getString("is_new"));
                    model.setIs_rating(wallpaper.getString("is_rating"));
                    model.setIs_popular(wallpaper.getString("is_popular"));
                    model.setIs_favourite(wallpaper.getString("is_favourite"));
                    list.add(model);
                }

            } else if (clickd.contentEquals("popular_image")) {
                JSONArray array = jsonArray.getJSONArray("is_popular");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject wallpaper = array.getJSONObject(i);
                    model = new WallpaperModel();
                    model.setWp_wallpaper_id(wallpaper.getString("wp_wallpaper_id"));
                    model.setWp_category_id(wallpaper.getString("wp_category_id"));
                    model.setName(wallpaper.getString("name"));
                    model.setTitle(wallpaper.getString("title"));
                    model.setSub_title(wallpaper.getString("sub_title"));
                    model.setImage(wallpaper.getString("image"));
                    model.setDescription(wallpaper.getString("description"));
                    model.setIs_new(wallpaper.getString("is_new"));
                    model.setIs_rating(wallpaper.getString("is_rating"));
                    model.setIs_popular(wallpaper.getString("is_popular"));
                    model.setIs_favourite(wallpaper.getString("is_favourite"));
                    list.add(model);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        //shared


        final Pager adapter = new Pager(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);


        viewPager.setCurrentItem(currentPosition);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                WallpaperModel im = (WallpaperModel) list.get(position);
//                Log.d("img", "" + im.getImage());
                // String img = (String) list.get(position);
                id = im.getWp_wallpaper_id();
                img = im.getImage();


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        lnr_set_wallpaper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                WallpaperManager myWallpaperManager
//                        = WallpaperManager.getInstance(getApplicationContext());
//                try {
//                    myWallpaperManager.setBitmap(img);
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }


                if (checkPermission()) {
                    new DownloadTask(context, img);
                } else {
                    requestPermission();
                }

            }
        });


    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Snackbar.make(findViewById(R.id.rel_root), "Permission Denied, Please allow to proceed !", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }


    private void AddFavourite() {
        String tag_string_req = "string_req";
        String url = Config.BASE_URL;
        dialog = new ProgressDialog(ImageShowActivity.this);
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int status;
                String msg = "";
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getInt("status");
                    msg = jsonObject.getString("msg");
                    if (status == 1) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    JSONObject main = new JSONObject();
                    main.put("action", "favourite");
                    main.put("wp_wallpaper_id", id);
                    main.put("device_id", "123");
                    param = main.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                params.put("json", param);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}