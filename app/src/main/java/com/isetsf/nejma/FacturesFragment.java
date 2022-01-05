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
import com.google.gson.reflect.TypeToken;
import com.isetsf.nejma.adapters.FactureAdapter;
import com.isetsf.nejma.domain.Facture;
import com.isetsf.nejma.domain.UserMobile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FacturesFragment extends Fragment {

    @BindView(R.id.swipe_factures_item)
    SwipeRefreshLayout swipe;
    @BindView(R.id.factures_list)
    RecyclerView recyclerView;
    List<Facture> data=new ArrayList<>();
    FactureAdapter adapter;
    SessionManager session;
    UserMobile user;

    public FacturesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_factures, container, false);
        ButterKnife.bind(this,v);
        session=new SessionManager(getContext());
        user=session.getUser();
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        adapter=new FactureAdapter(getContext(),data);
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
        String url=Services.SERVER+"ListeFacture?cin="+user.getCin();
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                Type type=new TypeToken<List<Facture>>(){}.getType();
                List<Facture> recived=gson.fromJson(response,type);
                data.addAll(recived);
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
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
