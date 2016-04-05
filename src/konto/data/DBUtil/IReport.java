package konto.data.DBUtil;

import java.sql.ResultSet;
import java.time.LocalDate;

import konto.data.model.LoginUser;

public interface IReport {
    
    public ResultSet getMonthReport(LoginUser user, LocalDate monthYear);
    
    public ResultSet getYearReport(LoginUser user, LocalDate year);

}
