package control;

import constantes.Constantes;
import util.ParsingData;
import vue.*;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class used to start the program.<br/>
 * Main class of the project.
 * Create the window and link the selection of port or date with data displaying.<br/>
 * Use a thread to read datafiles while having a moving loading bar.
 *
 * @author <b>Eliott ROGEAUX</b> & <b>Stéphane LAY</b> → <u>INF1-A1</u>
 */
public class MainController implements ActionListener {

    private PanelSelection selectPanel;
    private PanelDonnees dataPanel;

    public MainController() {
        FenetreMere mere = new FenetreMere();

        new Thread(() -> { //Thread pour la barre de chargement principale
            PanelChargement load = (PanelChargement) mere.getContentPane();
            ParsingData.read(load.getChargementBarre());

            SuperPanel superPanel = new SuperPanel();
            selectPanel = superPanel.getSelectPanel();
            dataPanel = superPanel.getDataPanel();

            selectPanel.addListener(this);

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
            LocalDate newDate;

            if (type.equals("Previous")) {
                currentMonth--;
                selectPanel.setMonth(currentMonth);
                newSelect = selectPanel.getCurrentMonthButtonOf(newSelect);
            } else if (type.equals("Next")) {
                currentMonth++;
                selectPanel.setMonth(currentMonth);
                newSelect = selectPanel.getCurrentMonthButtonOf(newSelect);
            }
            newDate = LocalDate.parse(dayNum + " " + currentMonth + " 2021", format);

            selectPanel.getSelectedButton().setBackground(Constantes.CURRENT_MONTH_COL);
            selectPanel.setSelectedButton(newSelect);
            newSelect.setBackground(Constantes.SELECT_COL);
            selectPanel.setDate(newDate);
            dataPanel.setInfos(selectPanel.getPort(), selectPanel.getDate());

        } else if (command.equals("Port")) {
            dataPanel.setInfos(selectPanel.getPort(), selectPanel.getDate());
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

    /**
     * Method to start the program
     *
     * @param args not used
     */
    public static void main(String[] args) {
        new MainController();
    }
}
