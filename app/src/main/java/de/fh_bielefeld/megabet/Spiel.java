package de.fh_bielefeld.megabet;

/**
 * Created by Sari on 12.11.16.
 */

public class Spiel implements Comparable<Spiel> {

    public String heim;
    public String gast;
    private String datum;
    private String uhrzeit;
    private int ergebnis;
    private int tore_heim;
    private int tore_gast;
    private long dbIndex;

    public void setHeim(String heim) {

        this.heim = heim;
    }

    public String getHeim() {

        return heim;
    }

    public void setGast(String gast) {

        this.gast = gast;
    }

    public String getGast() {

        return gast;
    }

    public void setDatum(String datum) {

        this.datum = datum;
    }

    public String getDatum() {

        return datum;
    }

    public void setUhrzeit(String uhrzeit) {

        this.uhrzeit = uhrzeit;
    }

    public String getUhrzeit() {

        return uhrzeit;
    }

    public void setErgebnis(int ergebnis) {

        this.ergebnis = ergebnis;
    }

    public int getErgebnis() {

        return ergebnis;
    }

    public long getDbIndex() {

        return dbIndex;
    }


    public Spiel(String datum, String uhrzeit, long dbIndex){
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.heim = heim;
        this.gast = gast;
        this.ergebnis = ergebnis;
        this.tore_heim = tore_heim;
        this.tore_gast = tore_gast;
        this.dbIndex = dbIndex;
    }

    public Spiel(String heim, String gast) {

        this(heim, gast, -1l);
    }


    @Override
    public int compareTo(Spiel spiel) {
        return this.heim.compareTo(spiel.getHeim());
    }

    @Override
    public String toString() {

        return datum + uhrzeit + heim + gast + ergebnis;
    }

}

