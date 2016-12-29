package com.theerapat.labhostbasecardemulation;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by theerapat on 11/26/2016.
 */
public class AccountStorage {
    private static final String PREF_ACCOUNT = "com.theerapat.labhostbasecardemulation.pref.account";
    private static final String KEY_ACCOUNT = "com.theerapat.labhostbasecardemulation.key.account";
    private static final String DEFAULT_ACCOUNT = "000001";

    public static String getAccount(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
        String account = preferences.getString(KEY_ACCOUNT, DEFAULT_ACCOUNT);
        return account;
    }

    public static void setAccount(Context context, String accountNum){
        SharedPreferences preferences = context.getSharedPreferences(PREF_ACCOUNT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ACCOUNT, accountNum);
    }
}
