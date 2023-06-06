package bomoncntt.svk62.mssv2051067158.data.remote.api.service;

import bomoncntt.svk62.mssv2051067158.data.remote.mapper.BackupResult;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.KirinNoodlesBackup;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.Record;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KirinNoodlesBackupService {
    @Headers({
            "X-Master-key: $2b$10$bF5djKek84jgVShxew0j7.D/DfTDFwuE2ZmUPNlI/JVd7WSLb10kW"
    })
    @GET("v3/b/{id}")
    Call<Record> getBackUp(@Path("id") String id);


    @Headers({
            "Content-Type: application/json",
            "X-Access-Key: $2b$10$JH8VG8qj8Sij7q1mAzeC.eUBSoVlnOqFIzKH5hcgMzt8QXDXBlLDq",
            "X-Master-key: $2b$10$bF5djKek84jgVShxew0j7.D/DfTDFwuE2ZmUPNlI/JVd7WSLb10kW",
            "X-Bin-Private: true",
            "X-Collection-Id: 64707c92b89b1e2299a5260b"
    })
    @POST("v3/b")
    Call<BackupResult> postBackup(@Body KirinNoodlesBackup kirinNoodlesBackup);

}
