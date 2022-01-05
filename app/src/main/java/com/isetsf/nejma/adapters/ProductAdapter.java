package com.isetsf.nejma.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.isetsf.nejma.ProductActivity;
import com.isetsf.nejma.R;
import com.isetsf.nejma.domain.Produit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by medchiheb on 06/04/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    List<Produit> data;
    Context context;
    public ProductAdapter (Context context,List<Produit> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
       final Produit p=data.get(position);
        if(p.getCategorie()!=null){
            holder.tv_categorie.setText(p.getCategorie().getNom());
        }else{
            holder.tv_categorie.setText("");
        }

        holder.tv_price.setText(p.getPrix()+"");
        holder.tv_product.setText(p.getNom());
        Picasso.with(context).load(p.getImage())
                .placeholder(R.drawable.product)
                .error(R.drawable.product)
                .into(holder.img_product);

        if(p.getPromo()==1){
            holder.img_promo.setVisibility(View.VISIBLE);
        }else
        { holder.img_promo.setVisibility(View.INVISIBLE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), ProductActivity.class);
                i.putExtra("product",new Gson().toJson(p));
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_product_item)
        TextView tv_product;
        @BindView(R.id.img_product_item)
        ImageView img_product;
        @BindView(R.id.tv_categorie_product)
        TextView tv_categorie;
        @BindView(R.id.tv_price_product)
        TextView tv_price;
        @BindView(R.id.card_product_item)
        CardView cardView;
        @BindView(R.id.img_product_promo)
        ImageView img_promo;
        public ProductViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    public void setFilter(List<Produit> users) {
        data = new ArrayList<>();
        data.addAll(users);
        notifyDataSetChanged();
    }
}
