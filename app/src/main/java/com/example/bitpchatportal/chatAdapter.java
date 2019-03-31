package com.example.bitpchatportal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.chatViewHolder>{

    private List<chat_list> chatLists;

    public chatAdapter(List<chat_list> chatLists){
        this.chatLists = chatLists;
    }

    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chat_list,viewGroup,false);

        return new chatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull chatViewHolder chatViewHolder, int i) {
        chatViewHolder.title.setText(chatLists.get(i).getName());
        chatViewHolder.body.setText(chatLists.get(i).getMsg());

    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }


    public class chatViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView body;

        public chatViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.titleTextView);
            body = (TextView)itemView.findViewById(R.id.msgTextView);
        }
    }
}
