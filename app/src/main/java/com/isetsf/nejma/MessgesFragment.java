package com.isetsf.nejma;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.isetsf.nejma.adapters.MessageAdapter;
import com.isetsf.nejma.domain.Message;
import com.isetsf.nejma.domain.UserMobile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MessgesFragment extends Fragment {
    @BindView(R.id.messages_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_message_list)
    SwipeRefreshLayout swipe;
    MessageAdapter adapter;
    List<Message> data=new ArrayList<>();
    UserMobile user;
    SessionManager session;


    public MessgesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_messges, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this,v);
        session=new SessionManager(getActivity());
        user= session.getUser();
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        adapter=new MessageAdapter(getContext(),data);
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
        String url=Services.SERVER+"ListeMessage?cin="+user.getCin();
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                Type type=new TypeToken<List<Message>>(){}.getType();
                List<Message> recived=gson.fromJson(response,type);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                session.logout(getActivity());
                break;

            case R.id.new_message:
                Intent i=new Intent(getContext(),NewMessage.class);
                startActivity(i);
                break;
        }
        return true;

    }
}
