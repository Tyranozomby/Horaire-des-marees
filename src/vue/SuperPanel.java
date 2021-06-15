package vue;

import constantes.Constantes;
import modele.Port;
import util.LectureEcriture;

import javax.swing.*;
import java.io.File;

public class SuperPanel extends JPanel {

    private final JComboBox<Port> comboBox = new JComboBox<>(LectureEcriture.lireTout(new File(Constantes.OBJ_FILE)));

    public SuperPanel() {
        setOpaque(false);
        add(comboBox);
    }


}
