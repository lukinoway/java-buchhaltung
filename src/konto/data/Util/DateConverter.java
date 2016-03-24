package konto.data.Util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter {

    public static java.sql.Date convertLocalDateToSqlDate(LocalDate localdate) {
	LocalDate ld = localdate;
	Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
	Date res = Date.from(instant);
	return new java.sql.Date(res.getTime());
    }

    public static LocalDate convertDateToLocalDate(Date date) {
	// date conversion
	Instant instant = Instant.ofEpochMilli(date.getTime());
	return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }
    
}
