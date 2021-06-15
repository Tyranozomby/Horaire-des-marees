package control;

import util.ParsingData;
import vue.FenetreMere;
import vue.PanelChargement;
import vue.SuperPanel;

public class MainController {

    private SuperPanel superPanel;

    public MainController(FenetreMere mere) {

        PanelChargement load = new PanelChargement();
        mere.setContentPane(load);

        new Thread(() -> {
            ParsingData.read(load.getChargementBarre());
            superPanel = new SuperPanel();
            mere.setContentPane(superPanel);
            mere.repaint();
        }).start();

    }
}
