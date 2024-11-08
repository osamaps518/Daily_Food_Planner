package com.hfad2.daily_food_planner.activites;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad2.daily_food_planner.R;
import com.hfad2.daily_food_planner.models.Food;
import com.hfad2.daily_food_planner.utils.SelectedFoodsManager;
import com.hfad2.daily_food_planner.utils.UserProfileManager;
import com.hfad2.daily_food_planner.adapters.MealPlanAdapter;

import java.util.*;

public class MealPlanActivity extends AppCompatActivity {
    private ListView lvMealPlan;
    private TextView tvCalorieTarget;
    private Button btnRegenerate;
    private Button btnSave;
    private MealPlanAdapter adapter;
    private Map<String, Map<String, List<Food>>> weeklyMealPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        initializeViews();
        initializeMealPlan();
        setupButtonListeners();
    }

    private void initializeViews() {
        lvMealPlan = findViewById(R.id.lvMealPlan);
        tvCalorieTarget = findViewById(R.id.tvCalorieTarget);
        btnRegenerate = findViewById(R.id.btnRegenerate);
        btnSave = findViewById(R.id.btnSave);
    }

    private void initializeMealPlan() {
        if (UserProfileManager.getProfile() == null) {
            return;
        }
        int dailyTarget = UserProfileManager.getProfile().getDailyCalorieTarget();
        tvCalorieTarget.setText(getString(R.string.daily_calorie_target, dailyTarget));

        Set<Food> selectedFoods = SelectedFoodsManager.getSelectedFoods();
        if (selectedFoods == null || selectedFoods.isEmpty()) {
            return;
        }


        generateWeeklyPlan();

        // Create and set adapter
        adapter = new MealPlanAdapter(this, weeklyMealPlan);
        lvMealPlan.setAdapter(adapter);

        // Add click listener
        lvMealPlan.setOnItemClickListener((parent, view, position, id) -> {
            adapter.toggleItemExpansion(position);
        });
    }

    private void generateWeeklyPlan() {
        weeklyMealPlan = new HashMap<>();
        String[] days = getResources().getStringArray(R.array.days_of_week);
        String[] mealTypes = getResources().getStringArray(R.array.meal_types);
        Set<Food> selectedFoods = SelectedFoodsManager.getSelectedFoods();

        // Generate plan for each day
        for (String day : days) {
            Map<String, List<Food>> dailyMeals = new HashMap<>();
            for (String mealType : mealTypes) {
                List<Food> mealFoods = generateMealPlan(mealType, selectedFoods);
                dailyMeals.put(mealType, mealFoods);
            }
            weeklyMealPlan.put(day, dailyMeals);
        }
    }

    private List<Food> generateMealPlan(String mealType, Set<Food> availableFoods) {
        List<Food> mealFoods = new ArrayList<>();
        int targetCalories = calculateMealTargetCalories(mealType);
        int currentCalories = 0;

        List<Food> foodList = new ArrayList<>(availableFoods);
        Collections.shuffle(foodList);

        for (Food food : foodList) {
            if (currentCalories + food.getCalories() <= targetCalories) {
                mealFoods.add(food);
                currentCalories += food.getCalories();
            }
        }

        return mealFoods;
    }

    private int calculateMealTargetCalories(String mealType) {
        int dailyTarget = UserProfileManager.getProfile().getDailyCalorieTarget();
        if (mealType.equals(getString(R.string.breakfast))) {
            return (int) (dailyTarget * 0.3);
        } else if (mealType.equals(getString(R.string.lunch))) {
            return (int) (dailyTarget * 0.4);
        } else {
            return (int) (dailyTarget * 0.3);
        }
    }

    private void setupButtonListeners() {
        btnRegenerate.setOnClickListener(v -> {
            generateWeeklyPlan();
            adapter.updateData(weeklyMealPlan);
        });

        btnSave.setOnClickListener(v -> {
            Toast.makeText(this, "تم حفظ خطة الوجبات", Toast.LENGTH_SHORT).show();
        });
    }
}