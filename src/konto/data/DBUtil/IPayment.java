package konto.data.DBUtil;

import konto.data.container.PaymentContainer;
import konto.data.model.LoginUser;
import konto.data.model.PaymentOrder;

public interface IPayment {
    
    public void createPayment(PaymentOrder payment);
    
    public void updatePayment(PaymentOrder payment);
    
    public void updatePaymentStatus(PaymentOrder payment);
    
    public void deletePayment(PaymentOrder payment);
    
    public PaymentContainer getAllPaymentsForUser(LoginUser user);
    
    public PaymentContainer getOpenPaymentsForUser(LoginUser user);
    
    

}
