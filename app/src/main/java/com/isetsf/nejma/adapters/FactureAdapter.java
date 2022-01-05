package com.isetsf.nejma.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.isetsf.nejma.FactureActivity;
import com.isetsf.nejma.R;
import com.isetsf.nejma.domain.Facture;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by medchiheb on 06/04/17.
 */

public class FactureAdapter extends RecyclerView.Adapter<FactureAdapter.FactureViewHolder> {

    List<Facture> factures;
    Context context;

    public FactureAdapter(Context context,List<Facture> data){
        this.context=context;
        this.factures=data;
    }

    @Override
    public FactureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.facture_item,parent,false);
        return new FactureViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FactureViewHolder holder, int position) {
        final Facture f=factures.get(position);
        holder.tv_date.setText(f.getDate_fact());
        holder.tv_id.setText("#f"+f.getId());
        holder.tv_montant.setText(f.getMontant()+"");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), FactureActivity.class);
                i.putExtra("fact",new Gson().toJson(f));
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return factures.size();
    }


    class FactureViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.tv_fact_date)
        TextView tv_date;
        @BindView(R.id.tv_factid)
        TextView tv_id;
        @BindView(R.id.tv_factmontant)
        TextView tv_montant;
        @BindView(R.id.card_facture_item)
        CardView cardView;
        public FactureViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
