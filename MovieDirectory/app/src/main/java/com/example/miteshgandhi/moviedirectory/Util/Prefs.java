package com.example.miteshgandhi.moviedirectory.Util;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by miteshgandhi on 12/29/17.
 */

public class Prefs {

    SharedPreferences sharedPreferences;

    public Prefs(Activity activity) {
        sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void setSearch(String search)
    {
        sharedPreferences.edit().putString("search",search).commit();
    }
    public String getSearch()
    {
        return sharedPreferences.getString("search","batman");
        // for the first time the default value will be batman i.e without hittimg the submkt button

    }


}
