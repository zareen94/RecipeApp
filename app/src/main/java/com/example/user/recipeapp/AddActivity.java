package com.example.user.recipeapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
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

public class AddActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button addbutton;
    EditText recipename,recipein,recipestep;
    Spinner recipetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //make firebase path
        databaseReference = FirebaseDatabase.getInstance().getReference("recipe").child("a");


        Spinner spinner = (Spinner)findViewById(R.id.recipetype);
        //set json file value in spinner
        ArrayList<String> item = getCountries("recipetypes.json");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,item);
        spinner.setAdapter(adapter);




        recipename = (EditText)findViewById(R.id.recipename);
        recipetype = (Spinner) findViewById(R.id.recipetype);
        recipein = (EditText)findViewById(R.id.recipeingredient);
        recipestep = (EditText)findViewById(R.id.recipestep);


        addbutton = (Button) findViewById(R.id.button_add);



       addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAsset();


            }
        });


    }

    //insert into firebase
    private void addAsset(){
        String rname =recipename.getText().toString().trim();
        String tname = recipetype.getSelectedItem().toString();
        String iname =recipein.getText().toString().trim();
        String sname =recipestep.getText().toString().trim();

        if((!TextUtils.isEmpty(rname)) && (!TextUtils.isEmpty(tname)) && (!TextUtils.isEmpty(iname)) && (!TextUtils.isEmpty(sname)))
        {

            String id = databaseReference.push().getKey();


            Recipe recipe = new Recipe(id,rname,tname,iname,sname);

            databaseReference.child(id).setValue(recipe);
            Toast.makeText(this,"RECIPE ADDED",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AddActivity.this,Home.class);
            startActivity(intent);
            //Toast.makeText(AddActivity.this,"Done",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"PLEASE COMPLETE ALL THE FORM", Toast.LENGTH_LONG).show();
        }
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





