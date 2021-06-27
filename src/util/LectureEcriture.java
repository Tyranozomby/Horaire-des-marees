package util;

import modele.Port;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LectureEcriture {

    /**
     * Méthode de lecture d’un fichier.
     *
     * @param file le fichier lu.
     * @return l’objet lu.
     */
    public static Object lecture(File file) {
        Object objetLu = null;
        try {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
            objetLu = stream.readObject();
            stream.close();
        } catch (ClassNotFoundException | IOException parException) {
            System.err.println("Erreur lecture du fichier '" + file.getName() + "'" + parException);
            System.exit(2);
        }
        return objetLu;
    }

    /**
     * Méthode d’écriture dans un fichier
     *
     * @param file   le fichier d'écriture.
     * @param object l’objet à écrire.
     */
    public static void ecriture(File file, Object object) {
        try {
            ObjectOutputStream flux = new ObjectOutputStream(new FileOutputStream(file));
            flux.writeObject(object);
            flux.flush();
            flux.close();
        } catch (IOException parException) {
            System.err.println("Erreur écriture du fichier '" + file.getName() + "'" + parException);
            System.exit(3);
        }
    }

    /**
     * Méthode pour lire tous les objets d'un fichier.
     *
     * @param folder le fichier de lecture.
     * @return un tableau contenant les ports lus.
     * @see Port
     */
    public static Port[] lireTout(File folder) {
        ArrayList<Port> liste = new ArrayList<>();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            liste.add((Port) lecture(file));
        }
        return liste.toArray(new Port[0]);
    }
}