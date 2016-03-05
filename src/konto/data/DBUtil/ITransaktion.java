package konto.data.DBUtil;

import java.time.LocalDate;

import konto.data.model.Category;
import konto.data.model.Konto;
import konto.data.model.Transaktion;
import konto.ui.view.Transaktion.TransaktionsContainer;

/**
 * Author: lukinoway Function Interface which should be used for Transaktion
 * Operation
 */
public interface ITransaktion {

    public void createTransaktion(Transaktion transaktion);

    public void updateTransaktion(Transaktion transaktion);

    public void deleteTransaktion(Transaktion transaktion);

    public TransaktionsContainer selectDataByDate(LocalDate begin, LocalDate end, Konto konto);

    public TransaktionsContainer selectDataByType(Konto konto, Category category);

    public TransaktionsContainer selectDataByTimeType(LocalDate begin, LocalDate end, Konto konto,
	    Category category);

}
