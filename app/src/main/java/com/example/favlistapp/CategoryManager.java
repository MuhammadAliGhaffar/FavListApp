package com.example.favlistapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class CategoryManager {

    private Context mContext;

    public CategoryManager(Context Context) {
        this.mContext = Context;
    }

    public void saveCategory(Category category){
        //Add implementation "androidx.preference:preference:1.1.1" in build .gradle check stacoverflow
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        //in arraylist values must be uniques or isko hashset ka object chaye uske constructor mn arraylist di ha
        HashSet itemsHashset=new HashSet(category.getItems());
        editor.putStringSet(category.getName(),itemsHashset);
        editor.apply();


    }

    //function that retrieve tha data from databse
    public ArrayList<Category> retrieveCategories(){

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(mContext);
        Map<String,?> data=sharedPreferences.getAll();

        ArrayList<Category> categories=new ArrayList<>();

        for(Map.Entry< String , ? > entry : data.entrySet()){

            Category category=new Category(entry.getKey(),new ArrayList<String>((HashSet) entry.getValue()));
            categories.add(category);

        }

        return categories;
    }


}
