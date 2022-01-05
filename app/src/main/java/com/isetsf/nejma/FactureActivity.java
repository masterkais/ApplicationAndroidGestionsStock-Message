package com.isetsf.nejma;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.isetsf.nejma.adapters.ProductAdapter;
import com.isetsf.nejma.domain.Facture;
import com.isetsf.nejma.domain.Produit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactureActivity extends AppCompatActivity {
    @BindView(R.id.tv_fact_id_detail)
    TextView tv_factid;
    @BindView(R.id.tv_fact_date_detail)
    TextView tv_date;
    @BindView(R.id.tv_montant_fact_detail)
    TextView tv_montant;
    @BindView(R.id.product_fact_list)
    RecyclerView recyclerView;
    List<Produit> data=new ArrayList<>();
    ProductAdapter adapter;
    Facture facture;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facture);
        ButterKnife.bind(this);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading..");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        facture=new Gson().fromJson(getIntent().getStringExtra("fact"),Facture.class);
        tv_date.setText(facture.getDate_fact());
        tv_montant.setText(facture.getMontant()+"");
        tv_factid.setText("#f"+facture.getId());
        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        adapter=new ProductAdapter(this,data);
        recyclerView.setAdapter(adapter);
        getDaata();
    }

    public void getDaata(){
        dialog.show();
        data.clear();
        String url=Services.SERVER+"GetProduitFacture?idf="+facture.getId();
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                Type type=new TypeToken<List<Produit>>(){}.getType();
                List<Produit> recived=gson.fromJson(response,type);
                data.addAll(recived);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("volleyerrror",error.toString());
            }
        });
        Volley.newRequestQueue(this).add(request);
    }
}
