package vue;

import javax.swing.*;
import java.awt.*;

public class SuperPanel extends JPanel {

    private final PanelSelection selection = new PanelSelection();
    private final PanelDonnees donnees = new PanelDonnees();

    public SuperPanel() {
        setBackground(new Color(170, 185, 185));
        setLayout(new BorderLayout());

        add(selection, BorderLayout.WEST);
        add(donnees, BorderLayout.EAST);


    }


}
