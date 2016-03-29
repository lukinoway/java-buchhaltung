package konto.data.model;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.Maps;

public enum PaymentStatus {
    
    NEU(0),
    BEZAHLT(99);
    
    private static final Map<Integer, PaymentStatus> LOOKUP = Maps.uniqueIndex(
            Arrays.asList(PaymentStatus.values()),
            PaymentStatus::getStatusCode);  
    
    private final int statusCode;
    
    PaymentStatus(int statusCode) {
	this.statusCode = statusCode;
    }
    
    public int getStatusCode() {
	return this.statusCode;
    }
    
    public static PaymentStatus fromStatus(int status) {
	return LOOKUP.get(status);
    }

}
