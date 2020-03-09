
package com.example.gestion_de_stock;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {
    public SessionManager(LoginActivity LoginActivity) {

    }

    private SharedPreferences prefs;

    public SessionManager(Context cntx) {
// TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void createSession(String user, String id) {
        prefs.edit().putString("username", user).commit();
        prefs.edit().putString("id", id).commit();
    }


}