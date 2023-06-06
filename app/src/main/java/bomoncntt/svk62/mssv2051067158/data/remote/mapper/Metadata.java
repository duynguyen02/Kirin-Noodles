package bomoncntt.svk62.mssv2051067158.data.remote.mapper;

import com.google.gson.annotations.SerializedName;

public class Metadata {
    @SerializedName("id")
    private String id;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("private")
    private boolean _private;
    @SerializedName("collectionId")
    private String collectionId;

    public Metadata(String id, String createdAt, boolean _private, String collectionId) {
        this.id = id;
        this.createdAt = createdAt;
        this._private = _private;
        this.collectionId = collectionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean is_private() {
        return _private;
    }

    public void set_private(boolean _private) {
        this._private = _private;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
}
