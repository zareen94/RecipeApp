package com.example.user.recipeapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.recipeapp.Entity.Recipe;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UpdateRecipe extends AppCompatActivity {

    Button updatebutton;
    EditText recipename, recipein, recipestep;
    DatabaseReference databaseReference;

    String name = "", type = "", ingredient = "", step = "", key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);


        Spinner spinner = (Spinner)findViewById(R.id.recipetype);

        ArrayList<String> item = getCountries("recipetypes.json");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,item);
        spinner.setAdapter(adapter);


        name = getIntent().getExtras().getString("recipename");
        type = getIntent().getExtras().getString("recipetype");
        ingredient = getIntent().getExtras().getString("recipeingredient");
        step = getIntent().getExtras().getString("recipestep");
        key = getIntent().getExtras().getString("id");

        recipename = (EditText) findViewById(R.id.recipename);
        recipein = (EditText) findViewById(R.id.recipeingredient);
        recipestep = (EditText) findViewById(R.id.recipestep);

        updatebutton = (Button)findViewById(R.id.updatebutton);

        recipename.setText(name);
        recipein.setText(ingredient);
        recipestep.setText(step);




        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updaterecipe();
                Intent intent = new Intent(UpdateRecipe.this, Home.class);
                startActivity(intent);



            }
        });


    }

    //update data in the database
    private void updaterecipe() {

        String name1 = recipename.getText().toString().trim();
        String in = recipein.getText().toString().trim();
        String st = recipestep.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference("recipe").child("a").child(key);

        Recipe recipe = new Recipe(key,name1,type,in,st);

        databaseReference.setValue(recipe);
        Toast.makeText(this, "Recipe Update", Toast.LENGTH_LONG).show();

    }

    public ArrayList<String> getCountries(String fileName)
    {
        JSONArray jsonArray = null;
        ArrayList<String> cList = new ArrayList<String>();



        try
        {

            InputStream is=getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] data =new byte[size];
            is.read(data);
            is.close();
            String json = new String(data,"UTF-8");
            jsonArray =new JSONArray(json);
            if(jsonArray != null)
            {

                for (int i =0; i<jsonArray.length();i++)
                {


                    cList.add(jsonArray.getJSONObject(i).getString("name"));
                }
            }
        }
        catch (IOException e){ e.printStackTrace();}
        catch (JSONException je){je.printStackTrace();}
        return cList;
    }

}
