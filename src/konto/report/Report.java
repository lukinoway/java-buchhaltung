package konto.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.util.Collection;

import konto.ui.session.SessionManager;

public class Report {
    
    public Report() {
	try {
	    report()
	    	.columns(
	    		col.column("Datum", "Datum", type.dateType()),
	    		col.column("Konto", "Konto", type.stringType()),
	    		col.column("Kategorie", "Kategorie", type.stringType()),
	    		col.column("Beschreibung", "Text", type.stringType()),
	    		col.column("Betrag", "Betrag", type.bigDecimalType()))
	    	.title(cmp.text("Getting started"))
	    	.pageFooter(cmp.pageXofY())
	    	.setDataSource((Collection<?>) SessionManager.getTransaktionsContainer());
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
