package konto.data.DBUtil;

import java.time.LocalDate;
import java.util.ArrayList;

import konto.data.model.Transaktion;

/**
 * Author: lukinoway Function Interface which should be used for Transaktion
 * Operation
 */
public interface ITransaktion {

    public void insertTransaktion(Transaktion transaktion);

    public void updateTransaktion(Transaktion transaktion);

    public void deleteTransaktion(Transaktion transaktion);

    public ArrayList<Transaktion> selectDataByDate(LocalDate begin, LocalDate end, int kontoId);

    public ArrayList<Transaktion> selectDataByType(int kontoId, int transaktionsType);

    public ArrayList<Transaktion> selectDataByTimeType(LocalDate begin, LocalDate end, int kontoId,
	    int transaktionsType);

    public void close();
}