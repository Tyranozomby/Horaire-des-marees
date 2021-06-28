package vue;

import constantes.Constantes;

import javax.swing.JFrame;
import javax.swing.ImageIcon;

/**
 * Unique fenêtre du projet.
 */
public class FenetreMere extends JFrame {

    public FenetreMere() {
        super("• WaterFlotte Visualizer •");

        this.setContentPane(new PanelChargement());
        this.setSize(1080, 720);
        this.setLocationRelativeTo(null);   //Centre la JFrame au milieu de l'écran
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setIconImage(new ImageIcon(Constantes.FIC_FILE + "icon.jpg").getImage());
    }
}
