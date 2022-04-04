package uz.sanjar.dictionarysql.core.database;

import android.content.Context;
import android.content.SharedPreferences;

public class MemoryHelper {
    private static MemoryHelper helper;
    private final SharedPreferences preferences;

    private MemoryHelper(Context context) {
        //context
        preferences = context.getSharedPreferences("puzzle15", Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (helper == null) {
            helper = new MemoryHelper(context);
        }
    }

    public static MemoryHelper getHelper() {
        return helper;
    }

    public boolean getFavState() {
        return preferences.getBoolean("sound_state", false);
    }

    public void setFavState(boolean b) {
        preferences.edit().putBoolean("sound_state", b).apply();
    }

    public boolean getLanguage() {
        return preferences.getBoolean("lang_state", false);
    }

    public void setLanguage(boolean b) {
        preferences.edit().putBoolean("lang_state", b).apply();
    }
}
