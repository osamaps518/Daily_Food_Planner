package com.hfad2.daily_food_planner.models;

import java.util.*;

public class MealPlan {
    private Map<String, List<Food>> dailyMeals;  // Key is the day (e.g., "Monday")

    public Map<String, List<Food>> getDailyMeals() {
        return dailyMeals;
    }

    public void setDailyMeals(Map<String, List<Food>> dailyMeals) {
        this.dailyMeals = dailyMeals;
    }

    public List<Food> getDayMeals(String day) {
        return new ArrayList<>();
    }

    public void generatePlan() {}
}
