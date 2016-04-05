package konto.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class ReportUtil {
    
    private StreamResource pdfRes;
    FileDownloader fileDownloader = null;
    
    public void prepareForPdfReport(ResultSet resSet) {

	TransaktionsReport trReport = new TransaktionsReport();
	pdfRes = createPdfResource(trReport.build(resSet));
	if(fileDownloader == null) {
	    fileDownloader = new FileDownloader(pdfRes);
	}
	if(resSet != null) {
	    fileDownloader.setFileDownloadResource(pdfRes);
	}
    }
    
    private StreamResource createPdfResource(JasperReportBuilder report) {
	return new StreamResource(new StreamResource.StreamSource() {
	    
	    private static final long serialVersionUID = 1L;

	    @Override
	    public InputStream getStream() {
		ByteArrayOutputStream pdfBuffer = new ByteArrayOutputStream();
		try {
		    report.toPdf(pdfBuffer);
		} catch (DRException e) {
		    e.printStackTrace();
		}
		
		return new ByteArrayInputStream(
                        pdfBuffer.toByteArray());
	    }
	}, new String("report.pdf"));
    }
    
    public void extendButton(Button buttonToExtend) {
        fileDownloader.extend(buttonToExtend);
    }

}
