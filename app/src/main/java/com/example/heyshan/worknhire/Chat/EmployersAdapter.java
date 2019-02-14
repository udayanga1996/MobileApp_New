package com.example.heyshan.worknhire.Chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.heyshan.worknhire.R;

import java.util.List;

/**
 * Created by DELL on 12/1/2017.
 * All Rights Reserved by MLPJ©
 */

public class EmployersAdapter extends RecyclerView.Adapter<EmployersAdapter.ViewHolder> {

    public EmployersAdapter(List<EmployerItem> employerItems, Context context) {
        this.employerItems = employerItems;
        this.context = context;
    }

    private List<EmployerItem> employerItems;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.chat_card_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final EmployerItem employerItem = employerItems.get(position);
        holder.tvName.setText(employerItem.getName());

        holder.linearLayoutChatItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ChatActivity.class);

                intent.putExtra("receiverId", employerItem.getUserId());
                intent.putExtra("receiverName", employerItem.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return employerItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName;
        public LinearLayout linearLayoutChatItem;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView)itemView.findViewById(R.id.tvName);
            linearLayoutChatItem = (LinearLayout)itemView.findViewById(R.id.linearLayoutChatItem);
        }
    }
}
