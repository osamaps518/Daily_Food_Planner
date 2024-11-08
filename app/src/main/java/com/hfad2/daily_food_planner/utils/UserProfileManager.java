package com.hfad2.daily_food_planner.utils;

import com.hfad2.daily_food_planner.models.UserProfile;

public class UserProfileManager {
    private static UserProfile currentProfile;
    private static boolean profileReady;

    public static void setProfile(UserProfile profile) {
        currentProfile = profile;
    }

    public static UserProfile getProfile() {
        return currentProfile;
    }
    public static void setProfileIsReady(boolean state){
        profileReady = state;
    }
    public static void setCurrentProfile(UserProfile currentProfilee){
        currentProfile = currentProfilee;
    }
    public static boolean isProfileReady(){
        return profileReady;
    }
}