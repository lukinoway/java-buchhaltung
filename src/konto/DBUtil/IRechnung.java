package konto.DBUtil;

import java.io.File;
import java.util.ArrayList;

import konto.model.Rechnung;

public interface IRechnung {

	public void insertRechnung(Rechnung rechnung);
	
	public void updateRechnung(Rechnung rechnung);
	
	public void deleteRechnung(int billId);
	
	public String downloadRechnung(int billId);
	
	public ArrayList<Rechnung> selectDataFromPool();
	
}
