package vue;

import modele.Port;

import javax.swing.*;
import java.time.LocalDate;

public class PanelDonnees extends JPanel {

    public PanelDonnees() {
        setOpaque(false);
    }

    public void setInfos(Port port, LocalDate date) {
        System.out.println(port + " | " + date);
    }

}
