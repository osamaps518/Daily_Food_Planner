package com.hfad2.daily_food_planner.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad2.daily_food_planner.R;
import com.hfad2.daily_food_planner.enums.Gender;
import com.hfad2.daily_food_planner.models.UserProfile;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class ProfileSetupActivity extends AppCompatActivity {
    private Spinner spnGender;
    private static UserProfile userProfile = new UserProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);
        spnGender = findViewById(R.id.spnGender);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Gender,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapter);

        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = parent.getSelectedItem().toString();
                Gender gender = Gender.valueOf(selectedGender.toUpperCase());
                userProfile.setGender(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}