package konto.data.DBUtil;

import java.util.ArrayList;

import konto.data.model.Rechnung;

public interface IRechnung {

    public void insertRechnung(Rechnung rechnung);

    public void updateRechnung(Rechnung rechnung);

    public void deleteRechnung(int billId);

    public String downloadRechnung(int billId);

    public ArrayList<Rechnung> selectDataFromPool();
    
    public void close();

}
