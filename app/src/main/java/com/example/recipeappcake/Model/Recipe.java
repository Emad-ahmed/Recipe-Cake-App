package com.example.recipeappcake.Model;

public class Recipe {
    String name;
    String description;
    String Price;
    String imageUrl;

    public Recipe(String name, String description, String price, String imageUrl) {
        this.name = name;
        this.description = description;
        Price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return Price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Recipe() {
    }
}
