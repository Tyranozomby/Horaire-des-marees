package modele;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Port implements Serializable {

    private String nom;
    private float latitude;
    private float longitude;
    private HashMap<Date, Donnees> map;

    public Port() {
        this.nom = "";
        this.latitude = 0;
        this.longitude = 0;
        this.map = null;
    }

    public String getNom() {
        return nom;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public HashMap<Date, Donnees> getMap() {
        return map;
    }

    public Donnees getDonnees(Date date) {
        return map.get(date);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setMap(HashMap<Date, Donnees> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return nom + " " + longitude + " " + latitude + " " + map;
    }
}
