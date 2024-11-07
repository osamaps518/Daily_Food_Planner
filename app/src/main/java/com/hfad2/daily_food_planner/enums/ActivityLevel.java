package com.hfad2.daily_food_planner.enums;

public enum ActivityLevel {
    SEDENTARY("Sedentary (little or no exercise)", 1.2),
    LIGHTLY_ACTIVE("Lightly active (1-3 days/week)", 1.375),
    MODERATELY_ACTIVE("Moderately active (3-5 days/week)", 1.55),
    VERY_ACTIVE("Very active (6-7 days/week)", 1.725),
    EXTRA_ACTIVE("Extra active (very hard exercise & physical job)", 1.9);

    private final String displayName;
    private final double multiplier;  // Each activity level has a specific multiplier

    // Constructor takes both display name and multiplier
    ActivityLevel(String displayName, double multiplier) {
        this.displayName = displayName;
        this.multiplier = multiplier;
    }

    // Getter for display name (for UI)
    public String getDisplayName() {
        return displayName;
    }

    // Getter for multiplier (for calculations)
    public double getMultiplier() {
        return multiplier;
    }
}
