package com.rx.android.jamspeedlibrary.utils;

import android.content.SharedPreferences;

/**
 * @author Jam
 *         create at 2016/5/19 17:42
 */
public final class EnumPreferences {
    private EnumPreferences() {
    }

    public static <T extends Enum<T>> T getEnumValue(SharedPreferences preferences, Class<T> type,
                                                     String key, T defaultValue) {
        String name = preferences.getString(key, null);
        if (name != null) {
            try {
                return Enum.valueOf(type, name);
            } catch (IllegalArgumentException ignored) {
            }
        }

        return defaultValue;
    }

    public static void saveEnumValue(SharedPreferences preferences, String key, Enum value) {
        preferences.edit().putString(key, value.name()).apply();
    }
}
