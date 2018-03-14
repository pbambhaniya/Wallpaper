package com.dragonwareapps.wallpaper.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.dragonwareapps.wallpaper.Activity.ImageShowActivity;
import com.dragonwareapps.wallpaper.Adapter.LoveWallpaperAdapter;
import com.dragonwareapps.wallpaper.Model.WallpaperModel;
import com.dragonwareapps.wallpaper.R;
import com.dragonwareapps.wallpaper.util.AppController;
import com.dragonwareapps.wallpaper.util.Config;
import com.dragonwareapps.wallpaper.util.ItemClickListener;
import com.dragonwareapps.wallpaper.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteImageFragment extends Fragment implements ItemClickListener {

    RecyclerView rv_img;
    LoveWallpaperAdapter adapter;
    Context context;
    private List<WallpaperModel> list;
    String  a_id;
    ProgressDialog dialog;
    String param;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    boolean userScrolled = true;
    Shared shared;
    int lastVisibleItem, firstVisible, pastVisiblesItems;
    WallpaperModel model;
    GridLayoutManager layoutManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);


        getActivity().setTitle("All Image");

        context = getActivity();
        shared = new Shared(context);
        rv_img = (RecyclerView) view.findViewById(R.id.rv_img);
        list = new ArrayList<>();

        a_id = getArguments().getString("android_id");


        getApiCall();

        rv_img.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastVisibleItem = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                if (userScrolled) {
                    if ((lastVisibleItem + pastVisiblesItems) >= totalItemCount) {
                        userScrolled = false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    pagecount++;
                                    getApiCall();
                                    //userScrolled = false;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 2000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return view;


    }

    private void getApiCall() {

        String tag_string_req = "string_req";
        String url = Config.BASE_URL;
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    shared.putString(Config.img, data.toString());
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject wallpaper = data.getJSONObject(i);
                        WallpaperModel model = new WallpaperModel();
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

                        list.add(model);

                    }

                     adapter = new LoveWallpaperAdapter(list, context);
                    layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
                    rv_img.setLayoutManager(layoutManager);
                    rv_img.setItemAnimator(new DefaultItemAnimator());
                    rv_img.setAdapter(adapter);
                    adapter.setClickListener(FavouriteImageFragment.this);


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
                    main.put("action", "getFavouriteData");
                    main.put("page", pagecount);
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

    @Override
    public void itemClicked(View View, int position) {
//        model = list.get(position);
//        String id = list.get(position).toString();
//        Intent intent = new Intent(getActivity(), ImageShowActivity.class);
//        intent.putExtra("click", "all_image");
//        intent.putExtra("img", model.getImage());
//        intent.putExtra("id", position);
//        startActivity(intent);
    }
}
