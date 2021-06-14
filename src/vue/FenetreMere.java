package vue;

import constantes.Constantes;
import modele.Calendrier;
import modele.Port;
import util.LectureEcriture;
import util.ParsingData;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

/**
 * Main class used to open the window and start data-collection
 *
 * @author Eliott ROGEAUX & Stéphane LAY → INF1-A
 * @see ParsingData
 * @see LectureEcriture
 */
public class FenetreMere extends JFrame {

    public FenetreMere() {
        super("• WaterFlotte •");

        SuperPanel contentPane = new SuperPanel();
        this.setContentPane(contentPane);

        this.setSize(1080, 720);
        this.setLocationRelativeTo(null);   //Center JFrame in the middle of the screen
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Constantes.BG_COLOR);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new FenetreMere();
        ParsingData.read();

    }
}
