package bomoncntt.svk62.mssv2051067158.data.remote.mapper;

import com.google.gson.annotations.SerializedName;

public class Record {
    @SerializedName("record")
    private KirinNoodlesBackup kirinNoodlesBackup;

    public Record(KirinNoodlesBackup kirinNoodlesBackup) {
        this.kirinNoodlesBackup = kirinNoodlesBackup;
    }

    public KirinNoodlesBackup getKirinNoodlesBackup() {
        return kirinNoodlesBackup;
    }

    public void setKirinNoodlesBackup(KirinNoodlesBackup kirinNoodlesBackup) {
        this.kirinNoodlesBackup = kirinNoodlesBackup;
    }
}
