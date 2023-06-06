package bomoncntt.svk62.mssv2051067158.data.remote.mapper;

import com.google.gson.annotations.SerializedName;

import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;

public class TableLocationDto {
    @SerializedName("TableID")
    private int tableID;
    @SerializedName("TableName")
    private String tableName;

    public TableLocationDto(int tableID, String tableName) {
        this.tableID = tableID;
        this.tableName = tableName;
    }

    public static TableLocationDto mapToDto(TableLocation tableLocation){
        return new TableLocationDto(
                tableLocation.getTableID(),
                tableLocation.getTableName()
        );
    }

    public TableLocation mapFromDto(){
        return new TableLocation(
                tableID,
                tableName
        );
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
