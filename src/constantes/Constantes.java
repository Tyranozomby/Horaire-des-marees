package constantes;

import java.awt.Color;
import java.awt.Font;

/**
 * Constantes du projet
 */
public interface Constantes {

    // Global
    Color BG_COLOR = new Color(200, 200, 200);
    Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);

    // Parsing / Lecture / Écriture
    String OUT_FILE = "output/";
    String IN_FILE = "input/";
    String OBJ_FILE = "objects/";
    String FIC_FILE = "fichiers/";

    // Sélection port et date
    String[] NOM_MOIS = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
    String[] NOM_JOURS = {"lun", "mar", "mer", "jeu", "ven", "sam", "dim"};
    String[] NOM_BOUTONS = {"<<", "<", ">", ">>"};

    Color OTHER_MONTH_COL = new Color(140, 190, 195);
    Color SELECT_COL = new Color(0, 140, 165);
    Color CURRENT_MONTH_COL = new Color(0, 205, 220);

    // Affichage données
    Font HEADER_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 15);
    Color HEADER_BG = new Color(0, 205, 220);
}
