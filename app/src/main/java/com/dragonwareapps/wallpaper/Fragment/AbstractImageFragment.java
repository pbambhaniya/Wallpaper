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
import android.widget.LinearLayout;
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
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class    AbstractImageFragment extends Fragment implements ItemClickListener {
    LinearLayout lnr_popular, lnr_rating, lnr_all;
    View v1, v2, v3;
    ProgressDialog dialog;
    Shared shared;
    int lastVisibleItem, firstVisible, pastVisiblesItems;
    Context context;
    LoveWallpaperAdapter adapter;
    RecyclerView rv_image;
    ArrayList<WallpaperModel> list;
    ArrayList<WallpaperModel> list2;
    ArrayList<WallpaperModel> list3;
    GridLayoutManager layoutManager;
    String param;
    WallpaperModel model;
    int pagecount = 1;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    boolean userScrolled = true;
    String click;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_abstract_image, container, false);
        context = getActivity();
        shared = new Shared(context);
        lnr_all = (LinearLayout) view.findViewById(R.id.lnr_all);
        lnr_popular = (LinearLayout) view.findViewById(R.id.lnr_popular);
        lnr_rating = (LinearLayout) view.findViewById(R.id.lnr_rating);
        rv_image = (RecyclerView) view.findViewById(R.id.rv_image);
        v1 = (View) view.findViewById(R.id.v1);
        v2 = (View) view.findViewById(R.id.v2);
        v3 = (View) view.findViewById(R.id.v3);

        list = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        click = "all_image";
        rv_image.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        getApiCall();
        v1.setVisibility(View.VISIBLE);
        lnr_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v1.setVisibility(View.VISIBLE);
                click = "all_image";
                adapter = new LoveWallpaperAdapter(list, context);
                layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
                rv_image.setLayoutManager(layoutManager);
                rv_image.setItemAnimator(new DefaultItemAnimator());
                rv_image.setAdapter(adapter);
                adapter.setClickListener(AbstractImageFragment.this);

            }
        });

        lnr_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v2.setVisibility(View.VISIBLE);
                v1.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                click = "rating_image";
                adapter = new LoveWallpaperAdapter(list2, context);
                layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
                rv_image.setLayoutManager(layoutManager);
                rv_image.setItemAnimator(new DefaultItemAnimator());
                rv_image.setAdapter(adapter);
                adapter.setClickListener(AbstractImageFragment.this);
            }
        });
        lnr_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v3.setVisibility(View.VISIBLE);
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
                click = "popular_image";
                adapter = new LoveWallpaperAdapter(list3, context);
                layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
                rv_image.setLayoutManager(layoutManager);
                rv_image.setItemAnimator(new DefaultItemAnimator());
                rv_image.setAdapter(adapter);
                adapter.setClickListener(AbstractImageFragment.this);
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
                    JSONObject data = jsonObject.getJSONObject("data");
                    shared.putString(Config.img, data.toString());
                    JSONArray jsonArray = data.getJSONArray("is_new");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject wallpaper = jsonArray.getJSONObject(i);
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
                        model.setIs_favourite(wallpaper.getString("is_favourite"));
                        list.add(model);

                    }

                    adapter = new LoveWallpaperAdapter(list, context);
                    layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
                    rv_image.setLayoutManager(layoutManager);
                    rv_image.setItemAnimator(new DefaultItemAnimator());
                    rv_image.setAdapter(adapter);
                    adapter.setClickListener(AbstractImageFragment.this);


                    JSONArray rating = data.getJSONArray("is_rating");
                    for (int i = 0; i < rating.length(); i++) {
                        JSONObject wallpaper = rating.getJSONObject(i);
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
                        model.setIs_favourite(wallpaper.getString("is_favourite"));
                        list2.add(model);

                    }

                    JSONArray popular = data.getJSONArray("is_popular");
                    for (int i = 0; i < popular.length(); i++) {
                        JSONObject wallpaper = popular.getJSONObject(i);
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
                        model.setIs_favourite(wallpaper.getString("is_favourite"));
                        list3.add(model);

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
                    main.put("action", "wallpaper");
                    main.put("page", pagecount);
                    main.put("wp_category_id", "1");
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
        model = list.get(position);
        Intent intent = new Intent(getActivity(), ImageShowActivity.class);
        intent.putExtra("click", click);
        intent.putExtra("img", model.getImage());
        intent.putExtra("id", position);
        startActivity(intent);
    }
}
