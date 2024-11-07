package com.hfad2.daily_food_planner.enums;

public enum FoodCategory {
    PALESTINIAN_MEALS("Palestinian Meals"),
    SNACKS("Snacks"),
    FRUITS("Fruits"),
    VEGETABLES("Vegetables"),
    BEVERAGES("Beverages");

    private final String displayName;

    FoodCategory(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
