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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
//    private void handleFoodCategorySelection(){
//        FoodCategory[] categories = MockDataProvider.getAllCategories();
//        // Create an array of display names for the spinner
//        String[] displayNames = new String[categories.length];
//        for (int i = 0; i < categories.length; i++) {
//            displayNames[i] = categories[i].getDisplayName();
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                 android.R.layout.simple_spinner_item, displayNames);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnFoodCategory.setAdapter(adapter);
//
//        spnFoodCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                FoodCategory selectedCategory = categories[position];
//                List<Food> foodsByChosenCategory = MockDataProvider.getFoodsByCategory(selectedCategory);
//                mapFoodToListView(foodsByChosenCategory);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
private void handleFoodCategorySelection(){
    FoodCategory[] categories = MockDataProvider.getAllCategories();
    // Debug log
    Log.d("FoodSelection", "Categories loaded: " + Arrays.toString(categories));

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
            // Debug log
            Log.d("FoodSelection", "Selected category: " + selectedCategory);

            List<Food> foodsByChosenCategory = MockDataProvider.getFoodsByCategory(selectedCategory);
            // Debug log
            Log.d("FoodSelection", "Foods loaded: " + foodsByChosenCategory.size());

            mapFoodToListView(foodsByChosenCategory);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    });
}
//
//    private void mapFoodToListView(List<Food> foods) {
//        // Create new instance of our custom adapter
//        foodListAdapter = new FoodListAdapter(this, foods);
//
//        // Set the adapter to the ListView
//        lvFoods.setAdapter(foodListAdapter);
//
//        // Enable the next button only if foods are selected
//        btnNext.setEnabled(SelectedFoodsManager.hasSelections()); // Initially disabled
//
//        // Add a listener to the ListView to track selections
//        lvFoods.setOnItemClickListener((parent, view, position, id) -> {
//            CheckBox checkBox = view.findViewById(R.id.cbFood);
//            checkBox.setChecked(!checkBox.isChecked()); // Toggle checkbox
//
//            // Update button state using SelectedFoodsManager
//            btnNext.setEnabled(SelectedFoodsManager.hasSelections());
//        });
//    }

    private void mapFoodToListView(List<Food> foods) {
        // Debug log
        Log.d("FoodSelection", "Mapping foods to ListView: " + foods.size());

        foodListAdapter = new FoodListAdapter(this, foods);
        lvFoods.setAdapter(foodListAdapter);

        // Debug log
        Log.d("FoodSelection", "Adapter set to ListView");

        btnNext.setEnabled(SelectedFoodsManager.hasSelections());

        lvFoods.setOnItemClickListener((parent, view, position, id) -> {
            // Debug log
            Log.d("FoodSelection", "Item clicked at position: " + position);

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