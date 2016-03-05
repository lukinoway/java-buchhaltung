package konto.data.DBUtil;

import konto.data.model.Konto;
import konto.data.model.LoginUser;
import konto.ui.view.Konto.KontoContainer;

public interface IKonto {

    public void createKonto(Konto konto);

    public void updateKonto();

    public void deleteKonto(Konto konto);
    
    public KontoContainer getKontoForUser(LoginUser user);

}
