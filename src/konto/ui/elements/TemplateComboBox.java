package konto.ui.elements;

import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;

public abstract class TemplateComboBox extends ComboBox{

    private static final long serialVersionUID = 1L;

    public TemplateComboBox() {
	super();
    }
    
    /**
     * HelperFunction to get Container ID's from ComboBox
     * 
     * @param box
     * @return
     */
    public int getComboBoxIDValue() {
	System.out.println("BoxEntryValue: " + this.getValue());

	int boxValue = (Integer) this.getValue();
	Item boxItem = this.getItem(boxValue);
	int boxId = (Integer) boxItem.getItemProperty("ID").getValue();

	System.out.println("Box ID: " + boxId);
	return boxId;
    }
    
    /**
     * Select value by ID
     * @param idValue
     */
    public void setComboBoxValue(int idValue) {
	for (int i=1; i <= this.size(); i++) {
	    if (idValue == (Integer) this.getItem(i).getItemProperty("ID").getValue()) {
		this.setValue(i);
	    }
	}

    }
}
