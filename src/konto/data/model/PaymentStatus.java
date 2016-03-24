package konto.data.model;

public enum PaymentStatus {
    
    NEU(0),
    BEZAHLT(99);
    
    private int statusCode;
    
    PaymentStatus(int statusCode) {
	this.statusCode = statusCode;
    }
    
    public int getStatusCode() {
	return this.statusCode;
    }

}
