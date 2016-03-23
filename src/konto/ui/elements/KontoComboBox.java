package konto.ui.elements;

import com.vaadin.ui.Select;

import konto.data.container.KontoContainer;
import konto.ui.session.SessionManager;

public class KontoComboBox extends TemplateComboBox{

    private static final long serialVersionUID = 1L;
    
    public KontoComboBox() {
	super();
	this.setCaption("Konto");
	this.fillKontoComboBox();
    }
    
    private void fillKontoComboBox() {
	KontoContainer kcontainer = SessionManager.getKontoContainer();

	this.setContainerDataSource(kcontainer);
	this.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
	this.setItemCaptionPropertyId("Beschreibung");
	this.setNullSelectionAllowed(false);

    }
}
