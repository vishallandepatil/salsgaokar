package com.example.vishallandepatil.incubatore.login;

import android.content.Context;
import android.content.SharedPreferences;


public class PrefManager
{
        // Shared Preferences
        SharedPreferences pref;

        // Editor for Shared preferences
        SharedPreferences.Editor editor;

        // Context
        Context _context;

        // Shared pref mode
        public static int PRIVATE_MODE = 0;

        // Shared preferences file name
        public static final String PREF_NAME = "INCUMONITORE";

   private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
   private static final String MOBILE = "mobile";
    private static final String CLINICNAME = "clinicname";
    private static final String CLINICADRESS = "address";






    public PrefManager(Context context) {
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }


        public void createLogin(String user_details) {


            editor.putString(MOBILE, user_details);

            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.commit();

        }
    public void storeClinicdata(String name,String address) {


        editor.putString(CLINICNAME, name);
        editor.putString(CLINICADRESS, address);

        editor.commit();

    }
    public String getName() {

        return pref.getString(CLINICNAME, null);
    }
    public String getAdress() {

        return pref.getString(CLINICADRESS, null);
    }
        public String getMobie() {

            return pref.getString(MOBILE, null);
        }
    public boolean ISLOGing() {

        return pref.getBoolean(KEY_IS_LOGGED_IN,false);
    }
        public void clearSession()
        {
            editor.remove(MOBILE);
            editor.remove(KEY_IS_LOGGED_IN);
            editor.remove(CLINICADRESS);
            editor.remove(CLINICNAME);
            editor.clear();
            editor.apply();
            editor.commit();

        }
}





