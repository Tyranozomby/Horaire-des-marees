package vue;

import constantes.Constantes;

import javax.swing.*;
import java.awt.*;

/**
 * Main panel.<br/>
 * Contain panel for port or date selection and display panel.
 */
public class SuperPanel extends JPanel {

    private final PanelSelection selection = new PanelSelection();
    private final PanelDonnees donnees = new PanelDonnees();

    public SuperPanel() {
        setBackground(Constantes.BG_COLOR);
        setLayout(new BorderLayout());


        add(selection, BorderLayout.WEST);
        add(donnees, BorderLayout.EAST);
    }

    public PanelSelection getSelectPanel() {
        return selection;
    }

    public PanelDonnees getDataPanel() {
        return donnees;
    }
}
