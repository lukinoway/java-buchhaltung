package konto.ui.elements;

import com.vaadin.ui.Select;

import konto.data.DBUtil.IKonto;
import konto.data.DBUtil.KontoDBUtil;
import konto.ui.session.SessionManager;
import konto.ui.view.Konto.KontoContainer;

public class KontoComboBox extends TemplateComboBox{

    private static final long serialVersionUID = 1L;
    IKonto kontoUtil = new KontoDBUtil();
    
    
    public KontoComboBox() {
	super();
	this.setCaption("Konto");
	this.fillKontoComboBox();
    }
    
    private void fillKontoComboBox() {
	KontoContainer kcontainer = kontoUtil.getKontoForUser(SessionManager.getUser());

	this.setContainerDataSource(kcontainer);
	this.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
	this.setItemCaptionPropertyId("Beschreibung");
	this.setNullSelectionAllowed(false);

    }
}
