package modele;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Classe représentant une marée. Elle est composé d'une heure, d'une hauteur ainsi que d'un coefficient.
 */
public class Marees implements Serializable {

    private final LocalTime heure;
    private final float hauteur;
    private final int coeff;

    public Marees(LocalTime heure, float hauteur, int coeff) {
        this.heure = heure;
        this.hauteur = hauteur;
        this.coeff = coeff;
    }

    public Marees() {
        this.heure = null;
        this.hauteur = 0;
        this.coeff = 0;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public float getHauteur() {
        return hauteur;
    }

    public int getCoeff() {
        return coeff;
    }

    @Override
    public String toString() {
        return heure + " " + hauteur + " " + coeff;
    }

}
