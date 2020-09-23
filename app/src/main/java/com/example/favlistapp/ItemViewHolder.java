package com.example.favlistapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    //1st activity se dosri activity py jo floating button uske liye ViewHolder class create ki ha

    public TextView item_textView;

    public ItemViewHolder(View itemView){
        super(itemView);
        item_textView=itemView.findViewById(R.id.item_textView);
    }


}
