package com.example.favlistapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CategoryFragment.OnCategoryInteractionListener{


    //Making varibale for intent function to redirect another activity
    public static final String CATEGORY_OBJECT_KEY ="CATEGORY_KEY";
    public static final int MAIN_ACTIVITY_REQUEST_CODE =1000;
    private CategoryFragment mCategoryFragment;
    private boolean isTablet = false;
    private CategoryItemsFragment mCategoryItemsFragment;
    FloatingActionButton fab;

    private FrameLayout categoryItemsFragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCategoryFragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.category_fragment);
        categoryItemsFragmentContainer = findViewById(R.id.category_items_fragment_container);

        isTablet = categoryItemsFragmentContainer != null;

        //Adding floating Action Bar ID

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCreateCategoryDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //Alert box for fab add button to add fields
    private void displayCreateCategoryDialog(){
        String alertTitle=getString(R.string.create_category);
        String positiveButtonTitle=getString(R.string.postive_button_title);
        //context should main activity becasuse we want to show dialog box on MainActivity
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
        final EditText categoryEditText=new EditText(this);
        //now input type
        categoryEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        alertBuilder.setTitle(alertTitle);
        alertBuilder.setView(categoryEditText);

        alertBuilder.setPositiveButton(positiveButtonTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Category category=new Category(categoryEditText.getText().toString(),new ArrayList<String>());
                mCategoryFragment.giveCategoryToManager(category);
                dialogInterface.dismiss();
                displayCategoryItems(category);


            }
        });

        alertBuilder.create().show();

    }
    //Making Intent function to ek activity se dosri activity py jayegee by clicking

    private void displayCategoryItems(Category category){
        if(!isTablet) {
            //code for mobile device
            Intent categoryItemsIntent = new Intent(this, CategoryItemsActivity.class);
            categoryItemsIntent.putExtra(CATEGORY_OBJECT_KEY, category);
            startActivityForResult(categoryItemsIntent, MAIN_ACTIVITY_REQUEST_CODE);
        }else {
            //code for tablet device
            if(mCategoryItemsFragment != null){
                getSupportFragmentManager().beginTransaction()
                        .remove(mCategoryItemsFragment).commit();
                mCategoryItemsFragment=null;
            }
            setTitle(category.getName());
            mCategoryItemsFragment = CategoryItemsFragment.newInstance(category);
            if(mCategoryItemsFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.category_items_fragment_container,mCategoryItemsFragment).addToBackStack(null).commit();

            }
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayCreateCategoryItemDialog();
                }
            });


        }
    }

    private void displayCreateCategoryItemDialog() {

        final EditText itemEditText = new EditText(this);
        itemEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        new AlertDialog.Builder(this)
                .setTitle("Enter item name here")
                .setView(itemEditText)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String item = itemEditText.getText().toString();
                        mCategoryItemsFragment.addItemToCategory(item);
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MAIN_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if(data != null){
                mCategoryFragment.saveCategory((Category) data.getSerializableExtra(CATEGORY_OBJECT_KEY));
            }
        }



    }



    @Override
    public void categoryIsTapped(Category category) {

        displayCategoryItems(category);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setTitle(getString(R.string.app_name));

        if(mCategoryItemsFragment != null && mCategoryItemsFragment.category != null){

            mCategoryFragment.getCategoryManager().saveCategory(mCategoryItemsFragment.category);
        }
        if(mCategoryItemsFragment != null){
            getSupportFragmentManager().beginTransaction()
                    .remove(mCategoryItemsFragment).commit();
            mCategoryItemsFragment=null;
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCreateCategoryDialog();
            }
        });

    }
}