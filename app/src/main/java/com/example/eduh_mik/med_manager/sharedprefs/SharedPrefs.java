package com.example.eduh_mik.med_manager.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eduh_mik.med_manager.models.User;
import com.google.gson.Gson;


/**
 * Created by mayore on 5/29/2017.
 */

public class SharedPrefs {
    SharedPreferences accountPreferences;
    SharedPreferences.Editor accountPreferencesEditor;
    Context context;
    final String IS_LOGGED_IN = "Is logged in";
    final String USER = "User";
    public SharedPrefs(Context context){
        this.context=context;
        accountPreferences=context.getSharedPreferences("Account", Context.MODE_PRIVATE);
        accountPreferencesEditor=accountPreferences.edit();
    }
    public void setIsloggedIn(boolean isloggedIn) {accountPreferencesEditor.putBoolean(IS_LOGGED_IN,isloggedIn).commit();}
    public boolean getIsloggedIn() {return accountPreferences.getBoolean(IS_LOGGED_IN,false);}
    //public void setUser(User user) {accountPreferencesEditor.putString(USER,new Gson().toJson(user)).commit();}
    //public User getUser() {return new Gson().fromJson(accountPreferences.getString(USER, new Gson().toJson(new User())),User.class);}

}
