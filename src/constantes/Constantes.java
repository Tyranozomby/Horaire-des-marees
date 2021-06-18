package constantes;

import java.awt.*;

public interface Constantes {

    String OUT_FILE = "output/";
    String IN_FILE = "input/";
    String OBJ_FILE = "objects/";
    String FIC_FILE = "fichiers/";

    String[] NOM_MOIS = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
    String[] NOM_JOURS = {"lun", "mar", "mer", "jeu", "ven", "sam", "dim"};
    String[] NOM_BOUTONS = {"<<", "<", ">", ">>"};

/*    Color OTHER_MONTH_COL = new Color(0, 100, 130);
    Color SELECT_COL = new Color(0, 190, 255);
    Color CURRENT_MONTH_COL = new Color(0, 225, 235,42);*/

    Color OTHER_MONTH_COL = new Color(140, 190, 195);
    Color SELECT_COL = new Color(0, 140, 165);
    Color CURRENT_MONTH_COL = new Color(0, 205, 220);

    Font DATE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);
}
