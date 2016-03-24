package konto.ui.elements;

import com.vaadin.ui.Select;

import konto.data.DBUtil.IUser;
import konto.data.DBUtil.UserDBUtil;
import konto.data.container.UserContainer;

public class UserComboBox extends TemplateComboBox {

    private static final long serialVersionUID = 1L;
    IUser userUtil = new UserDBUtil();
    
    public UserComboBox() {
	super();
	this.setCaption("User Auswahl");
	this.fillTypeComboBox();
    }

    private void fillTypeComboBox() {
	UserContainer ucontainer = userUtil.getUsers();
	this.setContainerDataSource(ucontainer);
	this.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
	this.setItemCaptionPropertyId("User");
	this.setNullSelectionAllowed(false);
    }

}
