package konto.ui.elements;

import com.vaadin.data.util.IndexedContainer;

import konto.ui.session.SessionManager;

public class KontoComboBox extends TemplateComboBox{

    private static final long serialVersionUID = 1L;
    private IndexedContainer kcontainer;
    
    public KontoComboBox() {
	super();
	this.setCaption("Konto");
	kcontainer = SessionManager.getKontoContainer();
	this.fillKontoComboBox();
    }
    
    public KontoComboBox(IndexedContainer kcontainer) {
	super();
	this.kcontainer = kcontainer;
	this.setCaption("Konto");
	this.fillKontoComboBox();
    }
    
    private void fillKontoComboBox() {
	this.setContainerDataSource(kcontainer);
	this.setItemCaptionMode(ItemCaptionMode.PROPERTY);
	this.setItemCaptionPropertyId("Beschreibung");
	this.setNullSelectionAllowed(false);

    }
}
