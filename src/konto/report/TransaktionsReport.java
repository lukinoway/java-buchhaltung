package konto.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.renderer.category.BarRenderer;

import java.sql.ResultSet;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.ReportBuilder;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.builder.chart.BarChartBuilder;
import net.sf.dynamicreports.report.builder.chart.PieChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.group.GroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.chart.DRIChartCustomizer;

public class TransaktionsReport {
    
    public TransaktionsReport() {
    }
    
    public JasperReportBuilder build(ResultSet resSet) {
	
	final JasperReportBuilder report = DynamicReports.report();
	CurrencyType currencyType = new CurrencyType();
	    
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
	TextColumnBuilder<BigDecimal> betragColumn = col.column("Betrag", "transaktion_betrag", currencyType);
	
	// SubTotal
	AggregationSubtotalBuilder<Date> minDateField = sbt.min(dateColumn).setLabel("Von: ");
	AggregationSubtotalBuilder<Date> maxDateField = sbt.max(dateColumn).setLabel("Bis: ");
	AggregationSubtotalBuilder<Long> transaktionCnt = sbt.count(betragColumn).setLabel("Anzahl an Transaktionen: ");
	AggregationSubtotalBuilder<BigDecimal> summary = sbt.sum(betragColumn).setLabel("Summe: ");
	
		
	// Charts
	BarChartBuilder kategorieChart = cht.barChart()
		.customizers(new ChartCustomizer())
		.setTitle("Summe pro Kategorie")
		.setCategory(kategorieColumn)
		.addSerie(cht.serie(betragColumn))
		.setCategoryAxisFormat(cht.axisFormat().setLabel("Kategorie"));
	
//	PieChartBuilder pieChart = cht.pieChart()
//		.customizers(new ChartCustomizer())
//		.setTitle("Verteilung:")
//		.setLabelFormat("{0} {2}")
//		.setKey(kategorieColumn)
//		.addSerie(cht.serie(betragColumn))
//		.setCircular(true);

	try {
	    report
	    	.columns(
	    		dateColumn, kontoColumn, kategorieColumn, textColumn, betragColumn)
	    	.setColumnTitleStyle(columnTitleStyle)
	    	.highlightDetailEvenRows()
	    	.title(
	    		cmp.horizontalList()
	    		.add(
	    		cmp.text("Auswertung: ").setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
	    		)
	    		.newRow()
	    		.add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10))
//	    		.newRow()
//	    		.add(pieChart)
	    		)
	    	.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
	    	.summary(
	    		cmp.horizontalList()
	    		.add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10))
	    		.newRow()
	    		.add(kategorieChart)
	    		)
	    	.setDataSource(resSet)
	    	.groupBy(kategorieColumn.setStyle(boldStyle))
	    	.subtotalsAtFirstGroupFooter(
	    		sbt.sum(betragColumn).setStyle(boldStyle).setLabel("Summe von " + "Kategorie"  + ": ").setDataType(currencyType))
	    	.subtotalsAtTitle(minDateField, maxDateField, transaktionCnt, summary.setStyle(boldStyle))
	    	;
	    	
	    	
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	return report;
    }
    
    private class ChartCustomizer implements DRIChartCustomizer, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void customize(JFreeChart chart, ReportParameters reportParameters) {
	    //BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
	    CategoryAxis domainAxis = chart.getCategoryPlot().getDomainAxis();
	    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(45.0));
	    
	}
	
    }
    
    private class CurrencyType extends BigDecimalType {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getPattern() {
	    return "€ #,###.00";
	}
    }

}
