package com.isetsf.nejma;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.isetsf.nejma.adapters.ProductAdapter;
import com.isetsf.nejma.domain.Produit;
import com.isetsf.nejma.domain.UserMobile;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavorisFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @BindView(R.id.swipe_favoris)
    SwipeRefreshLayout swipe;
    @BindView(R.id.favoris_list)
    RecyclerView recyclerView;
    List<Produit> data=new ArrayList<>();
    ProductAdapter adapter;
    SessionManager session;
    UserMobile user;


    public FavorisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_favoris, container, false);
        ButterKnife.bind(this,v);
        session=new SessionManager(getContext());
        user=session.getUser();
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        adapter=new ProductAdapter(getContext(),data);
        recyclerView.setAdapter(adapter);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        getData();
        return v;
    }

    public void getData(){
        data.clear();
        swipe.setRefreshing(true);
        String url=Services.SERVER+"ListeProduitFavori?cin="+user.getCin();
        Log.i("cracked",url);
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String prd=obj.getJSONArray("produits").toString();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Produit>>() {
                    }.getType();
                    List<Produit> recived = gson.fromJson(prd, type);
                    data.addAll(recived);
                    adapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
                }catch (Exception er){
                    er.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("volleyerror",error.toString());
            }
        });
        Volley.newRequestQueue(getContext()).add(request);
    }


}
