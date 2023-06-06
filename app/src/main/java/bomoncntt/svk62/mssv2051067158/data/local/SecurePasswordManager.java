package bomoncntt.svk62.mssv2051067158.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

public class SecurePasswordManager {
    private static final String PREFS_NAME = "9bb8359fb15a69ad55d94458e0bc682e";
    private static EncryptedSharedPreferences sharedPreferences;

    private static final String PASSWORD_KEY = "password_key";
    private static final String LOGIN_WITH_PASSWORD = "login_with_password";

    public static void init(Context context) {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    PREFS_NAME,
                    masterKeyAlias,
                    context.getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void savePassword(String password) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PASSWORD_KEY, password);
            editor.apply();
        }
        else {
            throw new IllegalStateException("SecurePasswordManager has not been initialized. Call init() first.");
        }
    }

    public static void setLoginWithPassword(boolean isLoginWithPassword){
        if (sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(LOGIN_WITH_PASSWORD, isLoginWithPassword);
            editor.apply();
        }
        else {
            throw new IllegalStateException("SecurePasswordManager has not been initialized. Call init() first.");
        }
    }

    public static boolean isLoginWithPassword(){
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(LOGIN_WITH_PASSWORD, false);
        }
        return false;
    }

    public static String getPassword() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(PASSWORD_KEY, null);
        }
        return null;
    }


    public static void removePassword(){
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(PASSWORD_KEY);
            editor.apply();
        }
        else {
            throw new IllegalStateException("SecurePasswordManager has not been initialized. Call init() first.");
        }
    }
}
