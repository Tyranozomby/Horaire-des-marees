package vue;

import control.MainController;
import util.LectureEcriture;
import util.ParsingData;

import javax.swing.JFrame;

/**
 * Class used to start the program and open the main window
 *
 * @author Eliott ROGEAUX & Stéphane LAY → INF1-A
 * @see ParsingData
 * @see LectureEcriture
 */
public class FenetreMere extends JFrame {

    public FenetreMere() {
        super("• WaterFlotte Visualizer •");

        this.setContentPane(new PanelChargement());
        this.setSize(1080, 720);
        this.setLocationRelativeTo(null);   //Center JFrame in the middle of the screen
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MainController(new FenetreMere());
    }
}
