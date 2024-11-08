package com.hfad2.daily_food_planner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hfad2.daily_food_planner.R;
import com.hfad2.daily_food_planner.models.Food;

import java.util.*;

public class MealPlanAdapter extends BaseAdapter {
    private static final int TYPE_DAY_HEADER = 0;
    private static final int TYPE_MEAL = 1;

    private final Context context;
    private Map<String, Map<String, List<Food>>> weeklyMealPlan;
    private List<AdapterItem> flattenedList;
    private Set<Integer> expandedPositions;

    public static class AdapterItem {
        public final int type;
        public final String day;
        public final String mealType;
        public final List<Food> foods;

        AdapterItem(int type, String day, String mealType, List<Food> foods) {
            this.type = type;
            this.day = day;
            this.mealType = mealType;
            this.foods = foods;
        }
    }

    public MealPlanAdapter(Context context, Map<String, Map<String, List<Food>>> weeklyMealPlan) {
        this.context = context;
        this.weeklyMealPlan = weeklyMealPlan;
        this.expandedPositions = new HashSet<>();
        this.flattenedList = flattenData();
    }

    private List<AdapterItem> flattenData() {
        List<AdapterItem> items = new ArrayList<>();

        for (Map.Entry<String, Map<String, List<Food>>> dayEntry : weeklyMealPlan.entrySet()) {
            String day = dayEntry.getKey();
            items.add(new AdapterItem(TYPE_DAY_HEADER, day, null, null));

            Map<String, List<Food>> meals = dayEntry.getValue();
            for (Map.Entry<String, List<Food>> mealEntry : meals.entrySet()) {
                items.add(new AdapterItem(TYPE_MEAL, day, mealEntry.getKey(), mealEntry.getValue()));
            }
        }

        return items;
    }

    public void updateData(Map<String, Map<String, List<Food>>> newData) {
        this.weeklyMealPlan = newData;
        this.flattenedList = flattenData();
        notifyDataSetChanged();
    }

    public void toggleItemExpansion(int position) {
        if (expandedPositions.contains(position)) {
            expandedPositions.remove(position);
        } else {
            expandedPositions.add(position);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return flattenedList.size();
    }

    @Override
    public AdapterItem getItem(int position) {
        return flattenedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return flattenedList.get(position).type;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterItem item = getItem(position);
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            if (item.type == TYPE_DAY_HEADER) {
                view = inflater.inflate(R.layout.meal_plan_day_header, parent, false);
            } else {
                view = inflater.inflate(R.layout.meal_plan_meal_item, parent, false);
            }
        }

        if (item.type == TYPE_DAY_HEADER) {
            bindDayHeader(view, item);
        } else {
            bindMealItem(view, item, expandedPositions.contains(position));
        }

        return view;
    }

private void bindDayHeader(View view, AdapterItem item) {
    TextView tvDayHeader = view.findViewById(R.id.tvDayHeader);
    TextView tvDayCalories = view.findViewById(R.id.tvDayCalories);

    tvDayHeader.setText(item.day);

    int totalDayCalories = calculateDayCalories(item.day);
    tvDayCalories.setText(context.getString(R.string.total_calories, totalDayCalories));
}

    private void bindMealItem(View view, AdapterItem item, boolean isExpanded) {
        TextView tvMealType = view.findViewById(R.id.tvMealType);
        TextView tvMealCalories = view.findViewById(R.id.tvMealCalories);
        TextView tvFoodList = view.findViewById(R.id.tvFoodList);

        tvMealType.setText(item.mealType);

        int mealCalories = calculateMealCalories(item.foods);
        tvMealCalories.setText(context.getString(R.string.meal_calories, mealCalories));

        if (isExpanded && item.foods != null && !item.foods.isEmpty()) {
            StringBuilder foodList = new StringBuilder();
            for (Food food : item.foods) {
                foodList.append(food.getName())
                        .append(" (")
                        .append(food.getCalories())
                        .append(" سعرة)")
                        .append("\n");
            }
            tvFoodList.setText(foodList.toString().trim());
            tvFoodList.setVisibility(View.VISIBLE);
        } else {
            tvFoodList.setVisibility(View.GONE);
        }
    }

    private int calculateMealCalories(List<Food> foods) {
        int total = 0;
        if (foods != null) {
            for (Food food : foods) {
                total += food.getCalories();
            }
        }
        return total;
    }

    private int calculateDayCalories(String day) {
        Map<String, List<Food>> meals = weeklyMealPlan.get(day);
        int total = 0;
        if (meals != null) {
            for (List<Food> foodList : meals.values()) {
                total += calculateMealCalories(foodList);
            }
        }
        return total;
    }
}