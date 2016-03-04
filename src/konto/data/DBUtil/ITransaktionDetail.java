package konto.data.DBUtil;

import java.util.ArrayList;

import konto.data.model.Transaktion;
import konto.data.model.TransaktionDetail;

public interface ITransaktionDetail {

    public void insertDetail(TransaktionDetail detail);

    public void updateDetail(TransaktionDetail detail);

    public void deleteDetail(int detailId);

    public ArrayList<TransaktionDetail> selectDetail(Transaktion transaktion);
    
    public void close();

}
