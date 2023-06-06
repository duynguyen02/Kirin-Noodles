package bomoncntt.svk62.mssv2051067158.data.remote.repository;

import bomoncntt.svk62.mssv2051067158.data.remote.api.client.KirinNoodlesBackupRetrofitClient;
import bomoncntt.svk62.mssv2051067158.data.remote.api.service.KirinNoodlesBackupService;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.BackupResult;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.KirinNoodlesBackup;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.Record;
import bomoncntt.svk62.mssv2051067158.domain.repository.KirinNoodlesBackupRepository;
import retrofit2.Call;
import retrofit2.Retrofit;

public class KirinNoodlesBackupRepositoryImpl implements KirinNoodlesBackupRepository {
    private KirinNoodlesBackupService service;
    private static KirinNoodlesBackupRepositoryImpl instance;

    public synchronized static KirinNoodlesBackupRepositoryImpl getInstance(){
        if (instance == null){
            instance = new KirinNoodlesBackupRepositoryImpl();
        }
        return instance;
    }

    private KirinNoodlesBackupRepositoryImpl() {
        Retrofit retrofit = KirinNoodlesBackupRetrofitClient.getClient();
        service = retrofit.create(KirinNoodlesBackupService.class);
    }

    @Override
    public Call<Record> getBackUp(String id) {
        return service.getBackUp(id);
    }

    @Override
    public Call<BackupResult> postBackup(KirinNoodlesBackup kirinNoodlesBackup) {
        return service.postBackup(kirinNoodlesBackup);
    }

}
