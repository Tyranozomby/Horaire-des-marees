package control;

import util.ParsingData;
import vue.FenetreMere;
import vue.PanelChargement;
import vue.SuperPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {

    private SuperPanel superPanel;

    public MainController(FenetreMere mere) {


        new Thread(() -> { //Thread pour la barre de chargement principale
            PanelChargement load = (PanelChargement) mere.getContentPane();
            ParsingData.read(load.getChargementBarre());
            superPanel = new SuperPanel();

            mere.setContentPane(superPanel); //Affiche le panel principal
            mere.validate();
        }).start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
