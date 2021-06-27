package util;

import constantes.Constantes;
import modele.Donnees;
import modele.Marees;
import modele.Port;

import javax.swing.JProgressBar;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDate;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

/**
 * Classe avec des méthodes statiques pour lire les fichiers de données dans le dossier <i>input</i>, puis les convertir en ports et les stocker dans le dossier <i>objects</i>.
 * <br/>
 * Déplace ensuite les fichiers lus dans le dossier <i>output</i>.
 */
public class ParsingData {

    /**
     * Méthode principale de la classe.<br/>
     * Elle vérifie la présence des dossier nécessaires puis commence la lecture des fichiers présents.<br/>
     * Elle incrémente aussi une barre de chargement pour voir l'avancement de la lecture.<br/>
     *
     * @param bar JProgressBar
     * @see #readFolder(File, JProgressBar)
     */
    public static void read(JProgressBar bar) {
        File input = new File(Constantes.IN_FILE);
        File output = new File(Constantes.OUT_FILE);
        File objets = new File(Constantes.OBJ_FILE);

        if (!input.exists()) {
            if (!input.mkdir()) {
                System.out.println("Problème de privilège, le dossier '" + Constantes.IN_FILE + "' recherché ne peut être créé");
                System.exit(1);
            }
        }
        if (!output.exists()) {
            if (!output.mkdir()) {
                System.out.println("Problème de privilège, le dossier '" + Constantes.OUT_FILE + "' recherché ne peut être créé");
                System.exit(1);
            }
        }
        if (!objets.exists()) {
            if (!objets.mkdir()) {
                System.out.println("Problème de privilège, le dossier '" + Constantes.OBJ_FILE + "' recherché ne peut être créé");
                System.exit(1);
            }
        }

        int totalLines = getTotalLines(input);

        if (totalLines != 0) {
            bar.setMaximum(totalLines);
            readFolder(input, bar);
        } else { // Rien à lire
            bar.setMaximum(100);
            bar.setValue(100);
        }
    }


    /**
     * Lit tous les fichiers du dossier <i>input</i> et même ceux des dossiers à l'intérieur.<br/>
     * Ensuite, lit les fichiers .txt et commence à les parser en tant que gratuites ou payantes.<br/>
     * On s'attend à ce que tous les fichiers .txt soient des données et au format requis.<br/>
     *
     * @param folder dossier dans lequel lire.
     * @param bar    JProgressBar pour voir l'évolution.
     * @see #readHauteurs(File, JProgressBar)
     * @see #readMarees(File, JProgressBar)
     */
    private static void readFolder(File folder, JProgressBar bar) {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (!file.isDirectory()) {
                if (file.getName().endsWith(".txt")) { // Ignore les autres types de fichiers
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));

                        String line;
                        if ((line = reader.readLine()) != null) {
                            reader.close();
                            if (line.startsWith("#")) { // = Fichier données gratuites
                                readHauteurs(file, bar);
                            } else {
                                readMarees(file, bar);  // = Fichier données payantes
                            }
                        } else {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                readFolder(file, bar);  //Appel récursif si le fichier est un dossier
            }
        }
    }


    /**
     * Lit les données du fichier donné en tant que format gratuit (Hauteurs d'eau heure par heure)<br/>
     * Crée un nouveau port si aucun n'existe ou complète celui correspondant stocké dans le dossier <i>objects</i><br/>
     * Déplace le fichier dans le dossier <i>output</i> lors que la lecture est finie.<br/>
     * Remplit la barre de progression.
     *
     * @param file fichier de données à lire
     * @param bar  JProgressBar à incrémenter
     * @see Port
     */
    private static void readHauteurs(File file, JProgressBar bar) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            Port port = new Port();
            HashMap<LocalDate, Donnees> map = new HashMap<>();

            boolean newPort = true;
            int currentHour;
            int previousHour = -1;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) { // Ligne d'informations complémentaires (nom, coordonnées)
                    String[] split = line.split(" ");
                    switch (split[1]) {
                        case "Station":
                            String nom = split[3];
                            File objet = new File(Constantes.OBJ_FILE + nom + ".ser");
                            if (objet.exists()) {
                                port = (Port) LectureEcriture.lecture(objet);
                                map = port.getMap();
                                newPort = false;
                            } else {
                                port.setNom(nom);
                            }
                            break;
                        case "Longitude":
                            if (newPort) {
                                port.setLongitude(Float.parseFloat(split[split.length - 1]));
                            }
                            break;
                        case "Latitude":
                            if (newPort) {
                                port.setLatitude(Float.parseFloat(split[split.length - 1]));
                            }
                            break;
                    }
                } else if (line.endsWith("1")) {    // Ligne de données de source 1
                    String[] split = line.split(";");
                    String[] time = split[0].split(" ");
                    String[] hour = time[1].split(":");

                    currentHour = Integer.parseInt(hour[0], 10);

                    if (currentHour != previousHour) {
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate date = LocalDate.parse(time[0], format);

                        if (map.containsKey(date)) {
                            float[] hauteurs = map.get(date).getHauteurs();
                            hauteurs[currentHour] = Float.parseFloat(split[1]);
                            map.get(date).setHauteurs(hauteurs);
                        } else {
                            Donnees donnees = new Donnees();
                            float[] hauteurs = donnees.getHauteurs();
                            hauteurs[currentHour] = Float.parseFloat(split[1]);
                            donnees.setHauteurs(hauteurs);
                            map.put(date, donnees);
                        }
                        previousHour = currentHour;
                    }
                }
                bar.setValue(bar.getValue() + 1);   // Incrémente la barre de chargement
            }
            reader.close();
            port.setMap(map);
            LectureEcriture.ecriture(new File(Constantes.OBJ_FILE + port.getNom() + ".ser"), port);
            file.renameTo(new File(Constantes.OUT_FILE + file.getName()));  // Déplace le fichier lu dans le dossier output
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Lit les données du fichier donné en tant que format payant (Marées hautes et marées basses)<br/>
     * Crée un nouveau port si aucun n'existe ou complète celui correspondant stocké dans le dossier <i>objects</i><br/>
     * Déplace le fichier dans le dossier <i>output</i> lors que la lecture est finie.<br/>
     * Remplit la barre de progression.
     *
     * @param file datafile to read
     * @see Port
     * @see Marees
     */
    private static void readMarees(File file, JProgressBar bar) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            Port port = new Port();
            HashMap<LocalDate, Donnees> map = new HashMap<>();

            String nom = "";
            boolean newPort = true;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Site")) {
                    String[] site = line.split(" ", 3);
                    String[] lieu = site[2].split(" ");
                    for (int i = 0; i < lieu.length; i++) {
                        if (lieu[i].startsWith("(") || lieu[i].startsWith("[")) {
                            lieu[i] = "";
                        } else {
                            nom += lieu[i] + " ";
                        }
                    }
                    nom = nom.substring(0, nom.length() - 1);
                    nom = nom.replace(' ', '-').toUpperCase();
                    File objet = new File(Constantes.OBJ_FILE + nom + ".ser");
                    if (objet.exists()) {
                        port = (Port) LectureEcriture.lecture(objet);
                        map = port.getMap();
                        newPort = false;
                    } else {
                        port.setNom(nom);
                    }
                } else if (line.startsWith("latitude")) {
                    if (newPort) {
                        String[] co = line.split(" - ");
                        String latLong = co[0];
                        String lonLong = co[1];

                        String[] lat = latLong.split(" ");
                        String[] lon = lonLong.split(" ");

                        port.setLatitude(Float.parseFloat(lat[2]));
                        port.setLongitude(Float.parseFloat(lon[2]));
                    }
                } else if (line.startsWith("2021")) {   // Ne prend en compte que l'année 2021
                    String[] split = line.split("\t");
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(split[0], format);
                    if (map.containsKey(date)) {
                        Marees[] marees = map.get(date).getMarees();

                        setMarees(split, marees);
                    } else {
                        Donnees donnees = new Donnees();
                        Marees[] marees = donnees.getMarees();

                        setMarees(split, marees);

                        donnees.setMarees(marees);
                        map.put(date, donnees);
                    }
                }
                bar.setValue(bar.getValue() + 1);
            }
            reader.close();
            port.setMap(map);
            LectureEcriture.ecriture(new File(Constantes.OBJ_FILE + port.getNom() + ".ser"), port);
            file.renameTo(new File(Constantes.OUT_FILE + file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode appelée par readMarees pour stocker les données dans le tableau donnée.<br/>
     * Stocke selon le format suivant: PM, BM, PM, BM (chronologique).<br/>
     *
     * @param split  la ligne découpée aux tabulations
     * @param marees le tableau pour les 4 marées
     * @see #readMarees(File, JProgressBar)
     * @see Marees
     */
    private static void setMarees(String[] split, Marees[] marees) {
        marees[0] = new Marees(LocalTime.parse(split[1]), Float.parseFloat(split[2]), Integer.parseInt(split[3], 10));
        if (split[4].startsWith("--")) {
            marees[2] = new Marees();
        } else {
            marees[2] = new Marees(LocalTime.parse(split[4]), Float.parseFloat(split[5]), Integer.parseInt(split[6], 10));
        }

        marees[1] = new Marees(LocalTime.parse(split[7]), Float.parseFloat(split[8]), 0);
        if (split[9].startsWith("--")) {
            marees[3] = new Marees();
        } else {
            marees[3] = new Marees(LocalTime.parse(split[9]), Float.parseFloat(split[10]), 0);
        }
    }

    /**
     * Méthode pour récupérer le nombre total de lignes qui seront lues, pour pouvoir augmenter correctement la JProgressBar.<br/>
     * Compte le nombre de lignes d'un fichier spécifique.<br/>
     * Si ce fichier est un dossier, alors regarde pour tous les fichiers qu'il contient.<br/>
     *
     * @param folder File where to count the number of lines
     * @return number of lines of the file
     */
    private static int getTotalLines(File folder) {
        int count = 0;
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isDirectory()) { // Si c'est un dossier alors récursif
                count += getTotalLines(file);
            } else if (file.getName().endsWith(".txt")) { // Ne compte que les .txt
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    while (reader.readLine() != null) count++;
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

}