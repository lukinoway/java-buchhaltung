package konto.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.ResultSet;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.ReportBuilder;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;

public class TransaktionsReport {
    
    public TransaktionsReport() {
    }
    
    public JasperReportBuilder build(ResultSet resSet) {
	
	final JasperReportBuilder report = DynamicReports.report();
	    
	// Style
	StyleBuilder boldStyle = stl.style().bold();
	StyleBuilder boldCenteredStyle = stl.style(boldStyle)
		.setHorizontalTextAlignment (HorizontalTextAlignment.CENTER);
	StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
		.setBorder(stl.pen1Point())
		.setBackgroundColor(Color.LIGHT_GRAY);
	StyleBuilder leftColumn = stl.style()
		.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
	StyleBuilder titleStyle = stl.style(boldCenteredStyle)
		.setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
		.setFontSize(15);
	
	// Columns
	TextColumnBuilder<Date> dateColumn = col.column("Datum", "transaktion_date", type.dateType()).setStyle(leftColumn);
	TextColumnBuilder<String> kontoColumn = col.column("Konto", "konto_desc_text", type.stringType());
	TextColumnBuilder<String> kategorieColumn = col.column("Kategorie", "type_text", type.stringType());
	TextColumnBuilder<String> textColumn = col.column("Beschreibung", "transaktion_text", type.stringType());
	TextColumnBuilder<BigDecimal> betragColumn = col.column("Betrag", "transaktion_betrag", type.bigDecimalType());
	
	// Charts
	Bar3DChartBuilder kategorieChart = cht.bar3DChart()
		.setTitle("Summe pro Kategorie")
		.setCategory(kategorieColumn)
		.addSerie(cht.serie(betragColumn));

	try {
	    report
	    	.columns(
	    		dateColumn, kontoColumn, kategorieColumn, textColumn, betragColumn)
	    	.setColumnTitleStyle(columnTitleStyle)
	    	.highlightDetailEvenRows()
	    	.title(
	    		cmp.horizontalList()
	    		.add(
	    		cmp.text("Auswertung:").setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))
	    		.newRow()
	    		.add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10))
	    		)
	    	.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
	    	.summary(kategorieChart)
	    	.setDataSource(resSet)
	    	.groupBy(kategorieColumn)
	    	.subtotalsAtColumnFooter(sbt.sum(betragColumn).setStyle(boldStyle))
	    	.subtotalsAtFirstGroupFooter(sbt.sum(betragColumn).setStyle(boldStyle));
	    	
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	return report;
    }

}
