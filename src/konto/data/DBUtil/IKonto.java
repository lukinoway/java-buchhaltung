package konto.data.DBUtil;

import java.util.HashMap;

import konto.data.container.KontoContainer;
import konto.data.model.Konto;
import konto.data.model.LoginUser;

public interface IKonto {

    public void createKonto(Konto konto);

    public void updateKonto(Konto konto);

    public void deleteKonto(Konto konto);
    
    public KontoContainer getKontoForUser(LoginUser user);
    
    public KontoContainer getVisibleKontosForUser(LoginUser user);
    
    public KontoContainer getVisibleKontos(LoginUser user);
    
    public int getUserIdforKonto(int kontoId);
    
    public String getTransferInformationforKonto(int kontoId);
    
    public String getBankURL(int kontoId);
    
    public HashMap<Integer, String> getUserNameforVisibleKonto();

}
