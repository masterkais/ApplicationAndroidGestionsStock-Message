package com.isetsf.nejma;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.isetsf.nejma.adapters.ProductAdapter;
import com.isetsf.nejma.domain.Produit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProduitsFragment extends Fragment {

   @BindView(R.id.produits_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_products_list)
    SwipeRefreshLayout swipe;
    List<Produit> data=new ArrayList<>();
    ProductAdapter adapter;


    public ProduitsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_produits, container, false);
        ButterKnife.bind(this,v);
        setHasOptionsMenu(true);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        adapter=new ProductAdapter(getContext(),data);
        recyclerView.setAdapter(adapter);

        getData();
        return v;
    }


   public void getData(){
        swipe.setRefreshing(true);
       data.clear();
       String url=Services.SERVER+"ListeProduit";
       StringRequest request=new StringRequest(url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               Gson gson=new Gson();
               Type type=new TypeToken<List<Produit>>(){}.getType();
               List<Produit> recived=gson.fromJson(response,type);
               data.addAll(recived);
               adapter.notifyDataSetChanged();
               swipe.setRefreshing(false);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Log.i("volleyerro",error.toString());
           }
       });
       Volley.newRequestQueue(getContext()).add(request);
   }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         inflater.inflate(R.menu.product_menu,menu);

        final MenuItem item = menu.findItem(R.id.action_filter_search);
       SearchView searchView= (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Search..");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Produit> filteredModelList = filter(data, newText);

                adapter.setFilter(filteredModelList);
                return true;
            }
        });
        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
// Do something when collapsed
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                        return true; // Return true to expand action view
                    }
                });

    }

    private List<Produit> filter(List<Produit> users, String query) {
        query = query.toLowerCase();final List<Produit> filteredModelList = new ArrayList<>();
        for (Produit user : users) {
            final String text = user.getNom().toLowerCase();
            final String text1 = user.getLibelle().toLowerCase();
            if (text.contains(query) || text1.contains(query)) {
                filteredModelList.add(user);
            }
        }
        return filteredModelList;
    }
}
