package com.hfad2.daily_food_planner.models;
import androidx.annotation.NonNull;

import com.hfad2.daily_food_planner.enums.FoodCategory;

import java.io.Serializable;

// Need to make the class Serializable to allow it's transfer between activities
public class Food implements Serializable {
    private String name;
    private int calories;
    private FoodCategory category;
    private String description;

    public Food(String name, int calories, FoodCategory category, String description) {
        this.name = name;
        this.calories = calories;
        this.category = category;
        this.description = description;
    }

    @NonNull
    @Override
    public String toString(){
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}