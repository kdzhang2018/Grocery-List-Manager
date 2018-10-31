package edu.gatech.seclass.glm;

/**
 * Created by dhruv on 10/8/16.
 */

public class ItemType {

    private int Id;
    private String typeName;

    public ItemType(String typeName) {
        this.typeName = typeName;
    }

    public ItemType(int Id, String typeName) {
        this.Id = Id;
        this.typeName = typeName;
    }

    public int getId() { return Id; }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return getTypeName();
    }
}
