package com.example.favlistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    //1st activity ka floating button uske liye adapter class create ki ha

    /*String[] categories={
            "Hobbies","Sports","Entertaiment","News","Games","Foods","Electronics Gadgets","Countries","Techonology","Mobiles"
    };*/

    //activity to another activity uske liye interface create krhy hain obj passing jab user click
    //kry gaw to category name action bar per ayegaw

    interface CategoryIsClickedInterface{
        void categoryIsClicked(Category category);
    }

    private ArrayList<Category> categories;
    private CategoryIsClickedInterface categoryIsClickedListener;

    public CategoryRecyclerAdapter(ArrayList<Category> categories,CategoryIsClickedInterface categoryIsClickedListener) {
        this.categories = categories;
        this.categoryIsClickedListener=categoryIsClickedListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.category_viewholder,parent,false);

        CategoryViewHolder categoryViewHolder=new CategoryViewHolder(view);

        return categoryViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {

        holder.getTxtCategoryNumber().setText(Integer.toString(position+1));
        holder.getTxtCategoryName().setText(categories.get(position).getName());

        //jab user item per click kry to user ko dosri activity py lyjaye
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIsClickedListener.categoryIsClicked(categories.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addCategory(Category category){
        categories.add(category);

        notifyItemInserted(categories.size()-1);

    }

}
