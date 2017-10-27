package com.example.user.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.recipeapp.Entity.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {


    DatabaseReference databaseReference;

    ListView listrecipes;

    List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recipes =new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("recipe").child("a");

        listrecipes = (ListView)findViewById(R.id.listrecipe);
        Button add = (Button)findViewById(R.id.button_add);







        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,AddActivity.class);
                startActivity(intent);
            }
        });

        listrecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Recipe asset =recipes.get(position);
                Intent intent = new Intent(Home.this,ScrollingActivity.class);
                intent.putExtra("key",asset.getKey());
                intent.putExtra("name", asset.getName());
                intent.putExtra("type", asset.getType());
                intent.putExtra("ingredient", asset.getIngredient());
                intent.putExtra("step", asset.getStep());

                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        //order the list by type ascending
        Query data = databaseReference.orderByChild("type");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                recipes.clear();

                for(DataSnapshot recipesnapshot : dataSnapshot.getChildren()){

                    Recipe recipe =recipesnapshot.getValue(Recipe.class);
                    recipes.add(recipe);
                }

                RecipeList adapter = new RecipeList(Home.this, recipes);
                listrecipes.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}
