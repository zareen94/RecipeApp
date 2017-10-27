package com.example.user.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.recipeapp.Entity.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScrollingActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    List<Recipe> recipes;
    String name1 ="",title1="",ingredient="",step="",key="";

    TextView recipenametitle,recipetypetitle,recipeingredient,recipestep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get value passing using intent
        name1 = getIntent().getExtras().getString("name");
        title1 = getIntent().getExtras().getString("type");
        ingredient = getIntent().getExtras().getString("ingredient");
        step = getIntent().getExtras().getString("step");
        key = getIntent().getExtras().getString("key");




        recipenametitle = (TextView)findViewById(R.id.recipeNAME);
        recipetypetitle = (TextView)findViewById(R.id.recipeTYPE);
        recipeingredient =(TextView)findViewById(R.id.recipeINGREDIENT);
        recipestep =(TextView)findViewById(R.id.recipeSTEP);

        recipenametitle.setText(name1.toUpperCase());
        recipetypetitle.setText(title1.toUpperCase());
        recipeingredient.setText(ingredient);
        recipestep.setText(step);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog(key,name1,title1);

            }
        });
    }


    private void showUpdateDialog(final String id, final String name, String type)
    {

        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = getLayoutInflater();
        final View dialogView =inflater.inflate(R.layout.update_list,null);

        dialogbuilder.setView(dialogView);

        final Button buttonUpdate = (Button)dialogView.findViewById(R.id.buttonupdate);
        final Button buttonDelete =(Button)dialogView.findViewById(R.id.buttondelete);


        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.show();
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ScrollingActivity.this,UpdateRecipe.class);
                intent.putExtra("id",key);
                intent.putExtra("recipename",name1);
                intent.putExtra("recipetype",title1);
                intent.putExtra("recipeingredient",ingredient);
                intent.putExtra("recipestep",step);
                startActivity(intent);

                alertDialog.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteAsset(id);
                alertDialog.dismiss();

            }
        });


    }

    //delete from database
    private void deleteAsset(String AssetId) {

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("recipe").child("a").child(AssetId);

        dbref.removeValue();
        Intent intent = new Intent(ScrollingActivity.this,Home.class);
        startActivity(intent);
        Toast.makeText(ScrollingActivity.this,"RECIPE DELETED",Toast.LENGTH_LONG).show();

    }


}
