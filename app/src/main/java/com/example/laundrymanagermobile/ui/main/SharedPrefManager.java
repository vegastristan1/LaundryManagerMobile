package com.example.laundrymanagermobile.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.laundrymanagermobile.LoginActivity;
import com.example.laundrymanagermobile.MainActivity;
import com.example.laundrymanagermobile.apiconnection.Manager;

public class SharedPrefManager {
    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_STORENAME = "keystorename";
    private static final String KEY_OWNER = "keyowner";
    private static final String KEY_CONTACT_NUMBER = "keycontactnumber";
    private static final String KEY_ADDRESS = "keyaddress";
    private static final String KEY_ID = "keyid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(Manager manager) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, manager.getId());
        editor.putString(KEY_USERNAME, manager.getStore_username());
        editor.putString(KEY_EMAIL, manager.getStore_email_address());
        editor.putString(KEY_PASSWORD, manager.getStore_password());
        editor.putString(KEY_STORENAME, manager.getStore_name());
        editor.putString(KEY_OWNER, manager.getStore_owner());
        editor.putString(KEY_CONTACT_NUMBER, manager.getStore_contact_number());
        editor.putString(KEY_ADDRESS, manager.getStore_address());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public Manager getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Manager(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_STORENAME, null),
                sharedPreferences.getString(KEY_OWNER, null),
                sharedPreferences.getString(KEY_CONTACT_NUMBER, null),
                sharedPreferences.getString(KEY_ADDRESS, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
