package bomoncntt.svk62.mssv2051067158.domain.repository;

import bomoncntt.svk62.mssv2051067158.data.remote.mapper.BackupResult;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.KirinNoodlesBackup;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.Record;
import retrofit2.Call;
import retrofit2.http.Body;

public interface KirinNoodlesBackupRepository {
    Call<Record> getBackUp(String id);
    Call<BackupResult> postBackup(KirinNoodlesBackup kirinNoodlesBackup);
}
