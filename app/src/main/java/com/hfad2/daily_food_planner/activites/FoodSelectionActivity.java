package com.hfad2.daily_food_planner.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import com.hfad2.daily_food_planner.R;
import com.hfad2.daily_food_planner.adapters.FoodListAdapter;
import com.hfad2.daily_food_planner.enums.FoodCategory;
import com.hfad2.daily_food_planner.models.Food;
import com.hfad2.daily_food_planner.utils.MockDataProvider;
import com.hfad2.daily_food_planner.utils.SelectedFoodsManager;

import java.util.*;

public class FoodSelectionActivity extends AppCompatActivity {
    private Spinner spnFoodCategory;
    private ListView lvFoods;
    private Button btnNext;
    private FoodListAdapter foodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selection);
        initializeViews();
        handleFoodCategorySelection();
        handleNextButtonClick();
    }

    private void initializeViews(){
        spnFoodCategory = findViewById(R.id.spnFoodCategory);
        lvFoods = findViewById(R.id.lvFoods);
        btnNext = findViewById(R.id.btnNext);
    }

private void handleFoodCategorySelection(){
    FoodCategory[] categories = MockDataProvider.getAllCategories();

    String[] displayNames = new String[categories.length];
    for (int i = 0; i < categories.length; i++) {
        displayNames[i] = categories[i].getDisplayName();
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, displayNames);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnFoodCategory.setAdapter(adapter);

    spnFoodCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            FoodCategory selectedCategory = categories[position];

            List<Food> foodsByChosenCategory = MockDataProvider.getFoodsByCategory(selectedCategory);

            mapFoodToListView(foodsByChosenCategory);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    });
}

    private void mapFoodToListView(List<Food> foods) {
        foodListAdapter = new FoodListAdapter(this, foods);
        lvFoods.setAdapter(foodListAdapter);


        btnNext.setEnabled(SelectedFoodsManager.hasSelections());

        lvFoods.setOnItemClickListener((parent, view, position, id) -> {
            CheckBox checkBox = view.findViewById(R.id.cbFood);
            checkBox.setChecked(!checkBox.isChecked());
            btnNext.setEnabled(SelectedFoodsManager.hasSelections());
        });
    }
    private void handleNextButtonClick() {
        btnNext.setOnClickListener(v -> {
            if (SelectedFoodsManager.hasSelections()) {
                Intent intent = new Intent(this, MealPlanActivity.class);
                startActivity(intent);
            }
        });
    }
}