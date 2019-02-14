package com.example.heyshan.worknhire;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }


    public ExampleAdapter(Context context, ArrayList<ExampleItem> exampleList){
        mContext= context;
        mExampleList=exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item,parent,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int i) {
        ExampleItem currentItem=mExampleList.get(i);

        String nameofworker=currentItem.getName();
        String emailofworker=currentItem.getEmail();
        String availableofWorker=currentItem.getEmpAvailable();

        holder.mEmpName.setText(nameofworker);
        holder.mEmpEmail.setText(emailofworker);
        holder.mEmpAvailable.setText(availableofWorker);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{

        public TextView mEmpName;
        public TextView mEmpEmail;
        public TextView mEmpAvailable;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            mEmpName=itemView.findViewById(R.id.text_view_workername);
            mEmpEmail=itemView.findViewById(R.id.text_view_workeremail);
            mEmpAvailable=itemView.findViewById(R.id.text_view_workeravailable);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                           mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
