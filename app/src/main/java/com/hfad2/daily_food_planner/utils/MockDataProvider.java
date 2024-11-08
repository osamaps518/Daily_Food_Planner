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
        foods.add(new Food("Mansaf", 800, FoodCategory.PALESTINIAN_MEALS, "Traditional lamb dish with rice and yogurt sauce"));

        // Snacks
        foods.add(new Food("Mixed Nuts", 160, FoodCategory.SNACKS, "Assorted nuts mix"));
        foods.add(new Food("Greek Yogurt", 120, FoodCategory.SNACKS, "Plain greek yogurt"));
        foods.add(new Food("Hummus & Carrots", 150, FoodCategory.SNACKS, "Fresh hummus with carrot sticks"));

        // Fruits
        foods.add(new Food("Apple", 95, FoodCategory.FRUITS, "Medium sized apple"));
        foods.add(new Food("Banana", 105, FoodCategory.FRUITS, "Medium sized banana"));
        foods.add(new Food("Orange", 62, FoodCategory.FRUITS, "Medium sized orange"));

        // Vegetables
        foods.add(new Food("Cucumber", 8, FoodCategory.VEGETABLES, "Fresh cucumber"));
        foods.add(new Food("Tomatoes", 22, FoodCategory.VEGETABLES, "Fresh tomatoes"));
        foods.add(new Food("Carrots", 25, FoodCategory.VEGETABLES, "Raw carrots"));

        // Beverages
        foods.add(new Food("Arabic Coffee", 5, FoodCategory.BEVERAGES, "Traditional Arabic coffee"));
        foods.add(new Food("Mint Tea", 2, FoodCategory.BEVERAGES, "Fresh mint tea"));
        foods.add(new Food("Lemon Water", 4, FoodCategory.BEVERAGES, "Fresh lemon water"));
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
