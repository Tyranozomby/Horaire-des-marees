package vue;

import constantes.Constantes;

import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * Panel principal.<br/>
 * Contient les panels de sélection et d'affichage des données.
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
