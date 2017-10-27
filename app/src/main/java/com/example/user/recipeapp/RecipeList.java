package com.example.user.recipeapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.recipeapp.Entity.Recipe;

import java.util.List;
import java.util.Locale;

/**
 * Created by user on 25/10/2017.
 */

public class RecipeList extends ArrayAdapter<Recipe> {

    private Activity context;
    private List<Recipe> recipes;

    public RecipeList(Activity context,List<Recipe> recipes){
        super(context, R.layout.listrecipe,recipes);

        this.context = context;
        this.recipes = recipes;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listrecipe = inflater.inflate(R.layout.listrecipe,null,true);

        TextView textName = (TextView)listrecipe.findViewById(R.id.recipeName);
        TextView textType = (TextView)listrecipe.findViewById(R.id.recipeType);

        Recipe recipe = recipes.get(position);

        textName.setText(recipe.getName().toUpperCase());
        textType.setText(recipe.getType().toUpperCase());



        return listrecipe;
    }
}
