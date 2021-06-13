package util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

public class LectureEcriture {

    /**
     * Méthode de lecture d’un fichier
     *
     * @param parFichier : le fichier lu
     * @return l’objet lu
     */
    public static Object lecture(File parFichier) {
        Object objetLu = null;
        try {
            ObjectInputStream flux = new ObjectInputStream(new FileInputStream(parFichier));
            objetLu = flux.readObject();
            flux.close();
        } catch (ClassNotFoundException parException) {
            parException.printStackTrace();
            System.exit(2);
        } catch (IOException parException) {
            System.err.println("Erreur lecture du fichier " + parException);
            System.exit(2);
        }
        return objetLu;
    }

    /**
     * Méthode d’écriture dans un fichier
     *
     * @param parFichier : le fichier dans lequel on écrit
     * @param parObjet   : l’objet écrit dans le fichier
     */
    public static void ecriture(File parFichier, Object parObjet) {
        try {
            ObjectOutputStream flux = new ObjectOutputStream(new FileOutputStream(parFichier));
            flux.writeObject(parObjet);
            flux.flush();
            flux.close();
        } catch (IOException parException) {
            System.err.println("Erreur écriture du fichier " + parException);
            System.exit(3);
        }
    }
}