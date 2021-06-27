package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Classe représentant un port. Il est caractérisé par un nom, une longitude et une latitude, ainsi qu'une HashMap de Données triées par LocalDate.
 *
 * @see Donnees
 */
public class Port implements Serializable {

    private String nom;
    private float latitude;
    private float longitude;
    private HashMap<LocalDate, Donnees> map;

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

    public HashMap<LocalDate, Donnees> getMap() {
        return map;
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

    public void setMap(HashMap<LocalDate, Donnees> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return nom;
    }
}
