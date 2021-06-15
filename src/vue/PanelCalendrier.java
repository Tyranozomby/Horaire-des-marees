package vue;

import modele.Calendrier;

import javax.swing.*;
import java.util.Date;
import java.util.TreeSet;

public class PanelCalendrier extends JPanel {

    public PanelCalendrier(int mois) {
        setOpaque(false);

        TreeSet<Date> dates = Calendrier.getDates(mois);
    }
}
