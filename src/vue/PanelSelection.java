package vue;

import constantes.Constantes;
import modele.Calendrier;
import modele.Port;
import util.LectureEcriture;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class PanelSelection extends JPanel {

    private final JComboBox<Port> comboBox;
    private final PanelCalendrier[] calendriers = new PanelCalendrier[12];

    public PanelSelection() {
        setOpaque(false);
        setLayout(new BorderLayout());

        Port[] listePorts = LectureEcriture.lireTout(new File(Constantes.OBJ_FILE));
        System.out.println(Arrays.toString(listePorts));
        comboBox = new JComboBox<>(listePorts);
        add(comboBox, BorderLayout.NORTH);

        for (int i = 0; i < 12; i++) {
            calendriers[i] = new PanelCalendrier(i + 1);
        }
    }
}
