package com.isetsf.nejma;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.isetsf.nejma.domain.Produit;
import com.isetsf.nejma.domain.UserMobile;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity {

    @BindView(R.id.img_product_detail)
    ImageView img_product;
    @BindView(R.id.tv_product_detail)
    TextView tv_product;
    @BindView(R.id.tv_categorie_detail)
    TextView tv_categorie;
    @BindView(R.id.tv_price_detail)
    TextView tv_price;
    @BindView(R.id.img_promo_detail)
    ImageView img_promo;
    @BindView(R.id.star_button)
    LikeButton likebutton;
     Produit product;
    SessionManager session;
    UserMobile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        session=new SessionManager(this);
        user=session.getUser();
        product=new Gson().fromJson(getIntent().getStringExtra("product"),Produit.class);
         prop();

    }

    public void prop(){
        getIsValid();
        Picasso.with(this).load(product.getImage())
                .placeholder(R.drawable.product)
                .error(R.drawable.product)
                .into(img_product);
        if(product.getPromo()==1){
            img_promo.setVisibility(View.VISIBLE);
        }else{
            img_promo.setVisibility(View.INVISIBLE);
        }
        if(product.getCategorie()!=null){
            tv_categorie.setText(product.getCategorie().getNom());
            tv_categorie.setVisibility(View.VISIBLE);
        }else
        {
            tv_categorie.setVisibility(View.INVISIBLE);
        }
        tv_product.setText(product.getNom());
        tv_price.setText(product.getPrix()+"");
        likebutton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addProducttoFavoris();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });
    }


    public void addProducttoFavoris(){
        final String url=Services.SERVER+"AjouterFavorie";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                TastyToast.makeText(getApplicationContext(), response, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("volleyerror",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("idp",product.getId()+"");
                map.put("idu",user.getCin()+"");
                return map;
            }
        };
        Volley.newRequestQueue(ProductActivity.this).add(request);
    }


    public  void getIsValid(){
        String url=Services.SERVER+"VerifFavorie?idp="+product.getId()+"&idu="+user.getCin();
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                UserMobile user=gson.fromJson(response,UserMobile.class);
                if(user!=null){
                    likebutton.setLiked(true);
                }else{
                    likebutton.setLiked(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("volleyerror",error.toString());
            }
        });

        Volley.newRequestQueue(this).add(request);
    }
}
