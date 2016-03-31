package konto.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.ResultSet;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;

public class Report {
    
    @SuppressWarnings({ "deprecation", "unused" })
    public Report(ResultSet resSet) {

	    
	// Style
	StyleBuilder boldStyle = stl.style().bold();
	StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);
	StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle).setBorder(stl.pen1Point())
		.setBackgroundColor(Color.LIGHT_GRAY);
	
	// Columns
	TextColumnBuilder<Date> dateColumn = col.column("Datum", "transaktion_date", type.dateType());
	TextColumnBuilder<String> kontoColumn = col.column("Konto", "konto_desc_text", type.stringType());
	TextColumnBuilder<String> kategorieColumn = col.column("Kategorie", "type_text", type.stringType());
	TextColumnBuilder<String> textColumn = col.column("Beschreibung", "transaktion_text", type.stringType());
	TextColumnBuilder<BigDecimal> betragColumn = col.column("Betrag", "transaktion_betrag", type.bigDecimalType());

	try {
	    report()
	    	.columns(
	    		dateColumn, kontoColumn, kategorieColumn, textColumn, betragColumn)
	    	.setColumnTitleStyle(columnTitleStyle)
	    	.highlightDetailEvenRows()
	    	.title(cmp.text("Getting started").setStyle(boldCenteredStyle))
	    	.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
	    	.setDataSource(resSet)
	    	.groupBy(kategorieColumn)
	    	.subtotalsAtColumnFooter(sbt.sum(betragColumn).setStyle(boldStyle))
	    	.subtotalsAtFirstGroupFooter(sbt.sum(betragColumn).setStyle(boldStyle))
	    	.show()
	    	.toJasperPrint();
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
