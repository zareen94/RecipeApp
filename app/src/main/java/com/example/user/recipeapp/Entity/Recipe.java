package com.example.user.recipeapp.Entity;

/**
 * Created by user on 24/10/2017.
 */

public class Recipe {

    String key;
    String name;
    String type;
    String ingredient;
    String step;

    public Recipe()
    {

    }

    public Recipe( String key,String name, String type, String ingredient, String step) {

        this.key = key;
        this.name = name;
        this.type = type;
        this.ingredient = ingredient;
        this.step = step;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
