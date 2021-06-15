package vue;

import javax.swing.*;
import java.awt.*;

public class PanelChargement extends JPanel {

    private final JProgressBar bar = new JProgressBar();


    public PanelChargement() {
        setBackground(Color.YELLOW);
        bar.setValue(0);
        bar.setStringPainted(true);
        add(bar);
    }


    public JProgressBar getChargementBarre() {
        return bar;
    }
}
