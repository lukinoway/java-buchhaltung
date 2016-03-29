package konto.data.model;

public class Konto {

    private int kontoId;
    private String kontonr;
    private String kontoname;
    private String kontoTransferInfo;
    private String bankURL;
    private int userId;
    private boolean visible;

    public Konto(String knr, String kname, int user, boolean visible, String kontoTransferInfo, String bankURL) {
	this.kontoId = 0;
	this.kontonr = knr;
	this.kontoname = kname;
	this.userId = user;
	this.setVisible(visible);
	this.setKontoTransferInfo(kontoTransferInfo);
	this.bankURL = bankURL;
    }

    public Konto(int kontoId, String knr, String kname, int user, boolean visible) {
	this.kontoId = kontoId;
	this.kontonr = knr;
	this.kontoname = kname;
	this.userId = user;
	this.setVisible(visible);
    }
    
    public Konto(int kontoId, String knr, String kname, int user, boolean visible, String kontoTransferInfo, String bankURL) {
	this.kontoId = kontoId;
	this.kontonr = knr;
	this.kontoname = kname;
	this.userId = user;
	this.setVisible(visible);
	this.setKontoTransferInfo(kontoTransferInfo);
	this.bankURL = bankURL;
    }

    public int getKontoId() {
	return kontoId;
    }

    public void setKontoId(int kontoId) {
	this.kontoId = kontoId;
    }

    public String getKontoNr() {
	return this.kontonr;
    }

    public String getKontoName() {
	return this.kontoname;
    }

    public void setKontoNr(String knr) {
	this.kontonr = knr;
    }
    
    public void setKontoName(String name) {
	this.kontoname = name;
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public boolean isVisible() {
	return visible;
    }

    public void setVisible(boolean visible) {
	this.visible = visible;
    }

    public String getKontoTransferInfo() {
	return kontoTransferInfo;
    }

    public void setKontoTransferInfo(String kontoTransferInfo) {
	this.kontoTransferInfo = kontoTransferInfo;
    }

    public String getBankURL() {
	return bankURL;
    }

    public void setBankURL(String bankURL) {
	this.bankURL = bankURL;
    }

}
