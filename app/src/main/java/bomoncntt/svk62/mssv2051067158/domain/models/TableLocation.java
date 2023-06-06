package bomoncntt.svk62.mssv2051067158.domain.models;

public class TableLocation {
    private int tableID;
    private String tableName;

    public TableLocation(int tableID, String tableName) {
        this.tableID = tableID;
        this.tableName = tableName;
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
