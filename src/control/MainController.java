package control;

import constantes.Constantes;
import util.ParsingData;
import vue.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainController implements ActionListener {

    private SuperPanel superPanel;
    private PanelSelection selectPanel;
    private PanelDonnees dataPanel;

    public MainController(FenetreMere mere) {

        new Thread(() -> { //Thread pour la barre de chargement principale
            PanelChargement load = (PanelChargement) mere.getContentPane();
            ParsingData.read(load.getChargementBarre());

            superPanel = new SuperPanel();
            selectPanel = superPanel.getSelectPanel();
            dataPanel = superPanel.getDataPanel();

            selectPanel.addListener(this);
            dataPanel.addListener(this);

            mere.setContentPane(superPanel); //Affiche le panel principal
            mere.validate(); //Valide la modification
        }).start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        int currentMonth = selectPanel.getMonth();

        if (command.contains(" ")) {
            JButton newSelect = (JButton) e.getSource();

            String[] split = command.split(" ");
            String type = split[0];
            int dayNum = Integer.parseInt(split[1]);

            DateTimeFormatter format = DateTimeFormatter.ofPattern("d M yyyy");
            LocalDate newDate = LocalDate.parse(dayNum + " " + currentMonth + " 2021", format);

            if (type.equals("Previous")) {
                newDate = newDate.minusMonths(1);
                selectPanel.setMonth(currentMonth - 1);
                newSelect = selectPanel.getCurrentMonthButtonOf(newSelect);
            } else if (type.equals("Next")) {
                newDate = newDate.plusMonths(1);
                selectPanel.setMonth(currentMonth + 1);
                newSelect = selectPanel.getCurrentMonthButtonOf(newSelect);
            }

            selectPanel.getSelected().setBackground(Constantes.CURRENT_MONTH_COL);
            selectPanel.setSelected(newSelect);
            newSelect.setBackground(Constantes.SELECT_COL);
            selectPanel.setDate(newDate);

        } else if (command.equals("Port")) {
            System.out.println(selectPanel.getPort());
            //TODO
        } else if (command.equals("<<")) {
            selectPanel.setMonth(1);
        } else if (command.equals("<")) {
            if (currentMonth > 1)
                selectPanel.setMonth(currentMonth - 1);
        } else if (command.equals(">")) {
            if (currentMonth < 12)
                selectPanel.setMonth(currentMonth + 1);
        } else if (command.equals(">>")) {
            selectPanel.setMonth(12);
        }
    }
}
