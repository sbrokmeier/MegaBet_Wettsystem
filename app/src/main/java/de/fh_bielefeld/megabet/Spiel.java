package de.fh_bielefeld.megabet;

/**
 * Created by Sari on 12.11.16.
 */

public class Spiel {
    private int spiel_id;
    private String heim;
    private String gast;
    private String datum;
    private String uhrzeit;
    private int ergebnis;
    private int tore_heim;
    private int tore_gast;

    public void setHeim(String heim) {
        this.heim = heim;
    }

    public String getHeim() {
        return heim;
    }

    public void setGast(String gast) {
        this.gast = gast;
    }

    public Spiel(String heim, String gast, String datum, String uhrzeit, int ergebnis, int tore_heim, int tore_gast){
        this.heim = heim;
        this.gast = gast;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.ergebnis = ergebnis;
        this.tore_heim = tore_heim;
        this.tore_gast = tore_gast;
    }

  //  public void compareTo(Spiel) {
        //    return this.heim.compareTo(spiel.getHeim());
  //  }

/*
    public class Person implements Comparable<Person> {
        private String name;
        private int alter;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setAlter(int alter) {
            this.alter = alter;
        }

        public int getAlter() {
            return alter;
        }

        public Person(String name, int alter) {
            this.name = name;
            this.alter = alter;
        }

        @Override
        public int compareTo(Person person) {
            return this.name.compareTo(person.getName());
        }

        @Override
        public String toString() {
            return name + ", " + alter;
        }
    }
}
*/

}