package com.hfad2.daily_food_planner.utils;

import com.hfad2.daily_food_planner.enums.FoodCategory;
import com.hfad2.daily_food_planner.models.Food;

import java.util.*;
import java.util.stream.Collectors;

public class MockDataProvider {
    private static List<Food> foods;
    private static Map<FoodCategory, List<Food>> foodsByCategory;

    static {
        initializeFoods();
        groupFoodsByCategory();
    }

    private static void initializeFoods() {
        foods = new ArrayList<>();
        // Palestinian Meals
        foods.add(new Food("Musakhan", 650, FoodCategory.PALESTINIAN_MEALS, "Traditional Palestinian sumac chicken with bread"));
        foods.add(new Food("Maqluba", 550, FoodCategory.PALESTINIAN_MEALS, "Upside-down rice, chicken and vegetables"));

        // Snacks
        foods.add(new Food("Mixed Nuts", 160, FoodCategory.SNACKS, "Assorted nuts mix"));
        foods.add(new Food("Greek Yogurt", 120, FoodCategory.SNACKS, "Plain greek yogurt"));

        // Fruits
        foods.add(new Food("Apple", 95, FoodCategory.FRUITS, "Medium sized apple"));
        foods.add(new Food("Banana", 105, FoodCategory.FRUITS, "Medium sized banana"));
    }

    private static void groupFoodsByCategory() {
        foodsByCategory = foods.stream()
                .collect(Collectors.groupingBy(Food::getCategory));
    }

    public static List<Food> getFoodsByCategory(FoodCategory category) {
        return foodsByCategory.getOrDefault(category, new ArrayList<>());
    }

    public static List<Food> getAllFoods() {
        return new ArrayList<>(foods);
    }

    public static FoodCategory[] getAllCategories() {
        return FoodCategory.values();
    }
}
