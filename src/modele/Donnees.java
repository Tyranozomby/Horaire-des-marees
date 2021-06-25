package modele;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 */
public class Donnees implements Serializable {

    private Marees[] marees;    //Size = 4
    private float[] hauteurs;   //From 0 to 23

    public Donnees() {
        this.marees = new Marees[4];
        this.hauteurs = new float[24];
    }

    public Marees[] getMarees() {
        return marees;
    }

    public float[] getHauteurs() {
        return hauteurs;
    }

    public void setMarees(Marees[] marees) {
        this.marees = marees;
    }

    public void setHauteurs(float[] hauteurs) {
        this.hauteurs = hauteurs;
    }

    @Override
    public String toString() {
        return Arrays.toString(marees) + " | " + Arrays.toString(hauteurs);
    }
}
