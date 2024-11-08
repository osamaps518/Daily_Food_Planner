package com.hfad2.daily_food_planner.models;

import java.util.*;

public class MealPlan {
    // Changed to support meal types per day
    private Map<String, Map<String, List<Food>>> weeklyPlan;  // Day -> MealType -> Foods
    private final int dailyCalorieTarget;

    public MealPlan(int dailyCalorieTarget) {
        this.dailyCalorieTarget = dailyCalorieTarget;
        this.weeklyPlan = new HashMap<>();
    }

    public void generatePlan(Set<Food> selectedFoods, String[] days, String[] mealTypes) {
        weeklyPlan = new HashMap<>();

        // Approximate calories per meal type (can be adjusted)
        Map<String, Double> mealTypeRatios = new HashMap<>();
        mealTypeRatios.put("الفطور", 0.3);  // 30% of daily calories
        mealTypeRatios.put("الغداء", 0.4);   // 40% of daily calories
        mealTypeRatios.put("العشاء", 0.3);   // 30% of daily calories

        for (String day : days) {
            Map<String, List<Food>> dailyMeals = new HashMap<>();

            for (String mealType : mealTypes) {
                int targetMealCalories = (int) (dailyCalorieTarget * mealTypeRatios.get(mealType));
                List<Food> mealFoods = generateMealFoods(selectedFoods, targetMealCalories);
                dailyMeals.put(mealType, mealFoods);
            }

            weeklyPlan.put(day, dailyMeals);
        }
    }

    private List<Food> generateMealFoods(Set<Food> availableFoods, int targetCalories) {
        List<Food> mealFoods = new ArrayList<>();
        int currentCalories = 0;
        List<Food> foodsList = new ArrayList<>(availableFoods);
        Collections.shuffle(foodsList); // Randomize food selection

        for (Food food : foodsList) {
            if (currentCalories + food.getCalories() <= targetCalories * 1.1) { // Allow 10% overflow
                mealFoods.add(food);
                currentCalories += food.getCalories();
            }

            if (currentCalories >= targetCalories * 0.9) { // At least 90% of target
                break;
            }
        }

        return mealFoods;
    }

    public Map<String, Map<String, List<Food>>> getWeeklyPlan() {
        return weeklyPlan;
    }

    public int getDailyCalorieTarget() {
        return dailyCalorieTarget;
    }

    // Helper methods for calorie calculations
    public int calculateMealCalories(List<Food> foods) {
        return foods.stream()
                .mapToInt(Food::getCalories)
                .sum();
    }

    public int calculateDayCalories(String day) {
        Map<String, List<Food>> dayMeals = weeklyPlan.get(day);
        if (dayMeals == null) return 0;

        return dayMeals.values().stream()
                .mapToInt(this::calculateMealCalories)
                .sum();
    }
}