package vue;

import constantes.Constantes;
import modele.Port;
import util.LectureEcriture;
import util.ParsingData;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

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
        File folder = new File("objets");
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            Port port = (Port) LectureEcriture.lecture(file);
            System.out.println(port);
        }
    }
}
