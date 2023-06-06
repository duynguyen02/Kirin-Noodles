package bomoncntt.svk62.mssv2051067158.utils;

import android.content.Context;

import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.data.local.SecurePasswordManager;

public class DataRecoveryHelper {
    private static DataRecoveryHelper instance;

    private KirinNoodlesSQLiteHelper kirinNoodlesSQLiteHelper;

    public synchronized static DataRecoveryHelper getInstance(Context context){
        if(instance == null){
            instance = new DataRecoveryHelper(context);
        }
        return instance;
    }

    private DataRecoveryHelper(Context context){
        this.kirinNoodlesSQLiteHelper = KirinNoodlesSQLiteHelper.getInstance(context.getApplicationContext());
    }

    public void resetData(){
        kirinNoodlesSQLiteHelper.resetAll();
        SecurePasswordManager.removePassword();
        SecurePasswordManager.setLoginWithPassword(false);
    }

}
