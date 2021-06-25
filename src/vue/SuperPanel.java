package vue;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;

/**
 * Main panel.<br/>
 * Contain panel for port or date selection and display panel.
 */
public class SuperPanel extends JPanel {

    private final PanelSelection selection = new PanelSelection();
    private final PanelDonnees donnees = new PanelDonnees();

    public SuperPanel() {
        setBackground(new Color(170, 185, 185));
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
