package com.hfad2.daily_food_planner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hfad2.daily_food_planner.R;
import com.hfad2.daily_food_planner.models.Food;
import com.hfad2.daily_food_planner.utils.SelectedFoodsManager;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Food> {
    private final Context context;
    private final List<Food> foods;
    private final ArrayList<Boolean> selectedItems; // Track multiple selections

    public FoodListAdapter(Context context, List<Food> foods) {
        super(context, R.layout.food_list_item, foods);
        this.context = context;
        this.foods = foods;
        // Initialize all items as unselected
        this.selectedItems = new ArrayList<>();
        for (int i = 0; i < foods.size(); i++) {
            selectedItems.add(false);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Food food = foods.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.food_list_item, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.cbFood);
        TextView foodName = convertView.findViewById(R.id.tvFoodName);

        foodName.setText(food.getName());

        // Set initial state based on global selection
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(SelectedFoodsManager.getSelectedFoods().contains(food));

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                SelectedFoodsManager.addFood(food);
            } else {
                SelectedFoodsManager.removeFood(food);
            }
        });

        return convertView;
    }

    // Method to get all selected foods
    public List<Food> getSelectedFoods() {
        List<Food> selectedFoods = new ArrayList<>();
        for (int i = 0; i < foods.size(); i++) {
            if (selectedItems.get(i)) {
                selectedFoods.add(foods.get(i));
            }
        }
        return selectedFoods;
    }
}
