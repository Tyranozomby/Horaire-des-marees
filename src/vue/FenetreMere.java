package vue;

import javax.swing.JFrame;

/**
 * Window for the project.
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
    }
}
