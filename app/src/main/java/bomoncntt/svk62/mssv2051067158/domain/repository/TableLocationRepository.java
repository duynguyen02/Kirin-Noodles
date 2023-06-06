package bomoncntt.svk62.mssv2051067158.domain.repository;

import java.util.List;

import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;

public interface TableLocationRepository {
    boolean addTableLocation(TableLocation tableLocation);
    TableLocation getTableLocationByID(int tableLocationID);
    List<TableLocation> getAllTableLocations();
    boolean updateTableLocation(TableLocation tableLocation);
    boolean deleteTableLocation(int tableLocationID);
}
