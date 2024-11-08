package com.hfad2.daily_food_planner.utils;

import com.hfad2.daily_food_planner.models.Food;

import java.util.*;

// New class: SelectedFoodsManager.java
public class SelectedFoodsManager {
    // static field to work across activities
    private static Set<Food> selectedFoods = new HashSet<>();

    public static void addFood(Food food) {
        selectedFoods.add(food);
    }

    public static void removeFood(Food food) {
        selectedFoods.remove(food);
    }

    public static Set<Food> getSelectedFoods() {
        return selectedFoods;
    }

    public static boolean hasSelections() {
        return !selectedFoods.isEmpty();
    }
}