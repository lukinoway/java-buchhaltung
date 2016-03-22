package konto.data.model;

public class Category {

    private int typeId;
    private String typeText;
    private int parent;

    /**
     * use for new Category
     * 
     * @param text
     */
    public Category(String text) {
	this.typeId = 0;
	this.setTypeText(text);
    }

    /**
     * use for DB access
     * 
     * @param Id
     * @param text
     */
    public Category(int Id, String text) {
	this.typeId = Id;
	this.typeText = text;
    }

    public int getTypeId() {
	return typeId;
    }

    public void setTypeId(int typeId) {
	this.typeId = typeId;
    }

    public String getTypeText() {
	return typeText;
    }

    public void setTypeText(String typeText) {
	this.typeText = typeText;
    }

    public int getParent() {
	return parent;
    }

    public void setParent(int parent) {
	this.parent = parent;
    }

}
