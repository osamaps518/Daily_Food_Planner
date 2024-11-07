package com.hfad2.daily_food_planner.models;

import com.hfad2.daily_food_planner.enums.ActivityLevel;
import com.hfad2.daily_food_planner.enums.Gender;

import org.threeten.bp.Period;
import org.threeten.bp.LocalDate;

public class UserProfile {
    private float height;
    private float weight;
    private Gender gender;
    private ActivityLevel activityLevel;
    private LocalDate dateOfBirth;
    private int age;
    private int dailyCalorieTarget;

    public UserProfile(float height, float weight, Gender gender, ActivityLevel activityLevel, LocalDate dateOfBirth) {
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.activityLevel = activityLevel;
        this.dateOfBirth = dateOfBirth;
        this.dailyCalorieTarget = calculateDailyCalories();
        this.age = calculateAge();
    }

    public int getAge() {
        return age;
    }

    public int calculateAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public int getDailyCalorieTarget() {
        return dailyCalorieTarget;
    }

    public void setDailyCalorieTarget(int dailyCalorieTarget) {
        this.dailyCalorieTarget = dailyCalorieTarget;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    private int calculateDailyCalories() {
        // Basic Metabolic Rate (BMR)
        double bmr;

        if (gender == Gender.MALE) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * getAge());
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * getAge());
        }

        // Total Daily Energy Expenditure (TDEE)
        return (int) (bmr * activityLevel.getMultiplier());
    }
}