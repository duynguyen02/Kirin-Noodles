package bomoncntt.svk62.mssv2051067158;

import android.app.Application;

import bomoncntt.svk62.mssv2051067158.data.local.SecurePasswordManager;
import bomoncntt.svk62.mssv2051067158.data.local.repository.KirinNoodlesRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.utils.DataRecoveryHelper;
import bomoncntt.svk62.mssv2051067158.utils.FileIOHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SecurePasswordManager.init(this);
        KirinNoodlesRepositoryImpl.getInstance(this);
        FileIOHelper.getInstance(this);
        DataRecoveryHelper.getInstance(this);
    }

}
