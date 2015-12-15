package konto.DBUtil;

import java.util.ArrayList;

import konto.model.Transaktion;
import konto.model.TransaktionDetail;

public interface ITransaktionDetail {

    public void insertDetail(TransaktionDetail detail);

    public void updateDetail(TransaktionDetail detail);

    public void deleteDetail(int detailId);

    public ArrayList<TransaktionDetail> selectDetail(Transaktion transaktion);

}
