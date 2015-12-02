package konto.DBUtil;

import java.io.File;

import konto.model.Rechnung;

public interface IRechnung {

	public void insertRechnung(Rechnung rechnung);
	
	public void updateRechnung(Rechnung rechnung);
	
	public void deleteRechnung(int billId);
	
	public File downloadRechnung(int billdId);
	
}
