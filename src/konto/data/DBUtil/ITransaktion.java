package konto.data.DBUtil;

import java.time.LocalDate;

import konto.data.container.TransaktionsContainer;
import konto.data.model.LoginUser;
import konto.data.model.Transaktion;

/**
 * Author: lukinoway Function Interface which should be used for Transaktion
 * Operation
 */
public interface ITransaktion {

    public void createTransaktion(Transaktion transaktion);

    public void updateTransaktion(Transaktion transaktion);

    public void deleteTransaktion(Transaktion transaktion);
   
    public TransaktionsContainer getAllTransaktionsForUser(LoginUser user);
    
    public TransaktionsContainer getTransaktionsForKontoCategory(int kontoId, int categoryId);
    
    public TransaktionsContainer getTransaktionsForDateKontoCategory(LocalDate begin, LocalDate end, int kontoId, int categoryId);
    
    public TransaktionsContainer getTransaktionsForMonthKontoCategory(LocalDate monthYear, int kontoId, int categoryId);
    
    public TransaktionsContainer getTransaktionsForYearKontoCategory(LocalDate year, int kontoId, int categoryId);
    
    public void getReport(LoginUser user);

}
