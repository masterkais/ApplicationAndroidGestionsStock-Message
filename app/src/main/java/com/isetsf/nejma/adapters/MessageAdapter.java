package com.isetsf.nejma.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isetsf.nejma.R;
import com.isetsf.nejma.domain.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by medchiheb on 06/04/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    List<Message> data;
    Context context;

    public MessageAdapter(Context context,List<Message> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);

        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        Message m=data.get(position);
        holder.tv_content.setText(m.getMessage());
        holder.tv_message.setText(m.getObjet());
        holder.tv_date.setText(m.getDate());
        holder.tv_etat.setText(m.getType());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_message_item)
        TextView tv_message;
        @BindView(R.id.tv_message_content)
        TextView tv_content;
        @BindView(R.id.tv_message_date)
        TextView tv_date;
        @BindView(R.id.tv_message_etat)
        TextView tv_etat;
        public MessageViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
