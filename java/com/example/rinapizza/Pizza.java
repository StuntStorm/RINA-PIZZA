package com.example.rinapizza;
import java.io.Serializable;
import java.util.List;


public class Pizza implements Serializable {
    private String name;
    private int imageResourceId;
    private List<String> ingredients;

    public Pizza(String name, int imageResourceId, List<String> ingredients) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}

