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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * Class with static methods to read the datafiles inside the input-directory then convert them into Port objects and stock those into objects-directory
 * Then move the files into the output-directory
 */
public class ParsingData {

    /**
     * Main method of the class.
     * This method call all that is necessary to create or complete objects with the datafiles in input-directory
     * By itself, this method checks if all required directories are here and start to read input-directory.
     *
     * @param bar JProgressBar to see the evolution
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

        bar.setMaximum(getTotalLines());
        readFolder(input, bar);
    }


    /**
     * Read all files of input directory and even those inside another directory.
     * Then, read .txt files and start to read as free or paying datafile.
     * Expects that all datafiles have the required format.
     *
     * @param folder Directory where to look for files to read
     * @param bar    JProgressBar to see the evolution
     * @see #readHauteur(File, JProgressBar)
     * @see #readMarees(File, JProgressBar)
     */
    private static void readFolder(File folder, JProgressBar bar) {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (!file.isDirectory()) {
                String[] split = file.getName().split("\\.");
                if (split[split.length - 1].equals("txt")) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));

                        String line;
                        if ((line = reader.readLine()) != null) {
                            reader.close();
                            if (line.startsWith("#")) {
                                readHauteur(file, bar);
                            } else {
                                readMarees(file, bar);
                            }
                        } else {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                readFolder(file, bar);
            }
        }
    }


    /**
     * Read data from given file as free data format (sea level hour by hour).
     * Create a new Port object if none exists or complete the corresponding one stored in objects-directory.
     *
     * @param file datafile to read
     * @param bar  JProgressBar to see the evolution
     * @see Port
     */
    private static void readHauteur(File file, JProgressBar bar) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            Port port = new Port();
            HashMap<Date, Donnees> map = new HashMap<>();

            boolean newPort = true;
            int currentHour = 0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
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
                } else if (line.endsWith("1")) {
                    String[] split = line.split(";");
                    String[] time = split[0].split(" ");
                    if (time[1].startsWith("0" + currentHour + ":00") || time[1].startsWith(currentHour + ":00")) {
                        try {
                            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(time[0]);
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
                            currentHour = (currentHour + 1) % 24;

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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
     * Read data from given file as paying data format (high tide and low tide).
     * Create a new Port object if none exists or complete the corresponding one stored in objects-directory.
     * Also fulfill the given JProgressBar
     *
     * @param file datafile to read
     * @see Port
     */
    private static void readMarees(File file, JProgressBar bar) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            Port port = new Port();
            HashMap<Date, Donnees> map = new HashMap<>();

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
                } else if (line.startsWith("2021")) {
                    try {
                        String[] split = line.split("\t");
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(split[0]);
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
                    } catch (ParseException e) {
                        e.printStackTrace();
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
     * Method called by readMarees to split and set Marees object.
     * Create a new Port object if none exists or complete the corresponding one stored in objects-directory.
     * Also fulfill the given JProgressBar
     *
     * @param split  splitted line with tide data
     * @param marees Array for the 4 tides of the day
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
     * Method to get the total number of line that will be read in order to increase properly the JProgressBar
     *
     * @return int corresponding to the total of lines of all the files
     * @see #getLinesOf(File)
     */
    private static int getTotalLines() {
        File input = new File(Constantes.IN_FILE);

        if (!input.exists()) {
            if (!input.mkdir()) {
                System.out.println("Problème de privilège, le dossier '" + Constantes.IN_FILE + "' recherché ne peut être créé");
                System.exit(1);
            }
        }
        return getLinesOf(input);
    }

    /**
     * Count the number of lines of a specific file.
     * If this file is a directory, then count the lines of the files inside itself.
     *
     * @param folder File where to count the number of lines
     * @return number of lines of the file
     */
    private static int getLinesOf(File folder) {
        int count = 0;
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isDirectory()) { // If directory
                count += getLinesOf(file);
            } else if (file.getName().endsWith(".txt")) { //If file .txt
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