package com.hfad2.daily_food_planner.activites;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad2.daily_food_planner.R;
import com.hfad2.daily_food_planner.enums.ActivityLevel;
import com.hfad2.daily_food_planner.enums.Gender;
import com.hfad2.daily_food_planner.models.UserProfile;
import com.hfad2.daily_food_planner.utils.SelectedFoodsManager;
import com.hfad2.daily_food_planner.utils.UserProfileManager;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProfileSetupActivity extends AppCompatActivity {
    private Spinner spnGender;
    private EditText edtBirthDate;
    private UserProfile userProfile;
    private Spinner spnActivityLevel;
    private EditText edtHeight;
    private EditText edtWeight;
    private Button btnToNextForm;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_user_profile_step1);

        // Initialize user profile
        userProfile = new UserProfile();

        // Initialize views for the first form first
        initializeViewsForFirstForm();

        // set up handlers for the views initialized in the first form
        handleGenderSelection();
        handleDateSelection();
        handleNextButtonClick();
    }

    private void initializeViewsForFirstForm() {
        spnGender = findViewById(R.id.spnGender);
        edtBirthDate = findViewById(R.id.edtBirthDate);
        btnToNextForm = findViewById(R.id.btnToNextForm);
    }

    private void handleGenderSelection() {
        // Create and set adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Gender,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapter);

        // Set up listener
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = parent.getSelectedItem().toString();
                Gender gender = Gender.valueOf(selectedGender.toUpperCase());
                userProfile.setGender(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing to do here
            }
        });
    }

    private void handleDateSelection() {
        // Configure EditText
        edtBirthDate.setFocusable(false);
        edtBirthDate.setClickable(true);

        // Set up click listener
        edtBirthDate.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%d",
                            selectedDay, selectedMonth + 1, selectedYear); // add 1 to month because months are zero indexed
                    edtBirthDate.setText(formattedDate);

                    LocalDate selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay);
                    userProfile.setDateOfBirth(selectedDate);
                }, year, month, day);
        // Can't exceed today's date
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        // Can't choose a date older than 100 years
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.YEAR, -100);
        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());

        datePickerDialog.show();
    }

    private void handleNextButtonClick() {
        btnToNextForm.setOnClickListener(v -> {
            if (validateFirstForm()) {
                setContentView(R.layout.activity_user_profile_step2);
                initializeViewsForSecondForm();
                handleActivityLevelSelection();
                handleHeightWeightInput();
            }
        });
    }

    private boolean validateFirstForm() {
        if (userProfile.getGender() == null ||
                TextUtils.isEmpty(edtBirthDate.getText())) {
            Toast.makeText(this, "الرجاء إدخال جميع البيانات", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void initializeViewsForSecondForm(){
        edtHeight = findViewById(R.id.edtHeight);
        edtWeight = findViewById(R.id.edtWeight);
        spnActivityLevel = findViewById(R.id.spnActivityLevel);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
//
//    private void handleHeightWeightInput() {
//        btnSubmit.setOnClickListener(v -> {
//            if (validateSecondForm()) {
//                // Get height and weight values
//                float height = Float.parseFloat(edtHeight.getText().toString());
//                float weight = Float.parseFloat(edtWeight.getText().toString());
//
//                // Set them in the user profile
//                userProfile.setHeight(height);
//                userProfile.setWeight(weight);
//
//                // Show success message
//                Toast.makeText(this, "تم حفظ البيانات بنجاح", Toast.LENGTH_SHORT).show();
//                UserProfileManager.setCurrentProfile(userProfile);
//                UserProfileManager.setProfileIsReady(true);
//            }
//        });
//    }

    private void handleHeightWeightInput() {
        btnSubmit.setOnClickListener(v -> {
            if (validateSecondForm()) {
                // Get height and weight values
                float height = Float.parseFloat(edtHeight.getText().toString());
                float weight = Float.parseFloat(edtWeight.getText().toString());

                // Set them in the user profile
                userProfile.setHeight(height);
                userProfile.setWeight(weight);

                // Update the manager
                UserProfileManager.setCurrentProfile(userProfile);
                UserProfileManager.setProfileIsReady(true);

                // Show success message
                Toast.makeText(this, "تم حفظ البيانات بنجاح", Toast.LENGTH_SHORT).show();

                // Start the next activity
                Intent intent = new Intent(this, FoodSelectionActivity.class);
                startActivity(intent);
            }
        });
    }
    private void handleActivityLevelSelection() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_levels,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnActivityLevel.setAdapter(adapter);

        spnActivityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Map the selected text to ActivityLevel enum
                userProfile.setActivityLevel(mapActivityLevel(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing to do here
            }
        });
    }

    private ActivityLevel mapActivityLevel(int position) {
        switch(position) {
            case 0: return ActivityLevel.SEDENTARY;
            case 1: return ActivityLevel.LIGHTLY_ACTIVE;
            case 2: return ActivityLevel.MODERATELY_ACTIVE;
            case 3: return ActivityLevel.VERY_ACTIVE;
            case 4: return ActivityLevel.EXTRA_ACTIVE;
            default: throw new IllegalArgumentException("Invalid activity level position");
        }
    }

    private boolean validateSecondForm() {
        // Check if fields are empty
        if (TextUtils.isEmpty(edtHeight.getText()) ||
                TextUtils.isEmpty(edtWeight.getText()) ||
                userProfile.getActivityLevel() == null) {
            Toast.makeText(this, "الرجاء إدخال جميع البيانات", Toast.LENGTH_SHORT).show();
            return false;
        }
        return validateHeightAndWeight();
    }

    public boolean validateHeightAndWeight(){

        // Simple validation for height and weight
        try {
            float height = Float.parseFloat(edtHeight.getText().toString());
            float weight = Float.parseFloat(edtWeight.getText().toString());

            if (height < 40 || height > 250) {
                edtHeight.setError("الطول يجب أن يكون بين 40 و 250 سم");
                return false;
            }

            if (weight < 20 || weight > 300) {
                edtWeight.setError("الوزن يجب أن يكون بين 20 و 300 كغم");
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "الرجاء إدخال أرقام صحيحة", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}