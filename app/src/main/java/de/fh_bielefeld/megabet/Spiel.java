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
    private String toreHeim;
    private String toreGast;
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

    public void setToreHeim(String toreHeim) {

        this.toreHeim = toreHeim;
    }

    public String getToreHeim() {

        return toreHeim;
    }


    public void setToreGast (String toreGast) {

        this.toreGast = toreGast;
    }

    public String getToreGast() {

        return toreGast;
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


    public Spiel(String datum, String uhrzeit, String heim, String gast, String toreHeim, String toreGast, long dbIndex){
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.heim = heim;
        this.gast = gast;
        this.ergebnis = ergebnis;
        this.toreHeim = toreHeim;
        this.toreGast = toreGast;
        this.dbIndex = dbIndex;
    }


    public Spiel(String datum, String uhrzeit, String heim, String gast, String toreHeim, String toreGast) {

        this(datum, uhrzeit, heim, gast, toreHeim, toreGast, -1l);
    }

    public Spiel(String datum, String uhrzeit, String heim, String gast, long dbIndex){
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.heim = heim;
        this.gast = gast;
        this.ergebnis = ergebnis;
        this.toreHeim = toreHeim;
        this.toreGast = toreGast;
        this.dbIndex = dbIndex;
    }


    public Spiel(String datum, String uhrzeit, String heim, String gast) {

        this(datum, uhrzeit, heim, gast, -1l);
    }

    /*
    public Spiel(String datum, String uhrzeit, String heim, String gast){
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.heim = heim;
        this.gast = gast;
        this.ergebnis = ergebnis;
        this.toreHeim = toreHeim;
        this.toreGast = toreGast;
        this.dbIndex = dbIndex;
    }
    */

    public Spiel(String datum, String uhrzeit, String heim, String gast, int ergebnis, String toreHeim, String toreGast, long dbIndex){
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.heim = heim;
        this.gast = gast;
        this.ergebnis = ergebnis;
        this.toreHeim = toreHeim;
        this.toreGast = toreGast;
        this.dbIndex = dbIndex;
    }


    @Override
    public int compareTo(Spiel spiel) {
        return this.datum.compareTo(spiel.getDatum());
    }

    public String toString1() {
        return datum + " " + uhrzeit + " " +  heim + " - " + gast;
    }

    @Override
    public String toString() {

        return datum + " " + uhrzeit + " " +  heim + " - " + gast + " " + toreHeim + ":" + toreGast;
    }

}

