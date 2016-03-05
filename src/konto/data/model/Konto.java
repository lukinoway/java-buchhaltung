package konto.data.model;

public class Konto {

	private int kontoId;
    private String kontonr;
    private String kontoname;
    private int userId;

    public Konto(String knr, String kname, int user) {
    	this.kontoId = 0;
		this.kontonr = knr;
		this.kontoname = kname;
		this.userId = user;
    }
    
    public Konto(int kontoId, String knr, String kname, int user) {
    	this.kontoId = kontoId;
		this.kontonr = knr;
		this.kontoname = kname;
		this.userId = user;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
