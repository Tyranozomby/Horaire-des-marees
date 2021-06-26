package control;

import constantes.Constantes;
import util.ParsingData;
import vue.*;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class MainController implements ActionListener, KeyListener {

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
            JButton today = new JButton();
            today.setActionCommand("Current " + LocalDate.now().getDayOfMonth());
            buttonClicked(selectPanel.getCurrentMonthButtonOf(today));

            mere.setContentPane(superPanel); //Affiche le panel principal
            mere.validate(); //Valide la modification
            mere.addKeyListener(this);
        }).start();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        int currentMonth = selectPanel.getMonth();

        if (command.contains(" ")) {
            buttonClicked((JButton) e.getSource());
        } else if (command.equals("Port")) {
            selectPanel.setLabelCo(selectPanel.getPort());
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

    private void buttonClicked(JButton button) {
        String command = button.getActionCommand();
        int currentMonth = selectPanel.getMonth();

        String[] split = command.split(" ");
        String type = split[0];
        int dayNum = Integer.parseInt(split[1]);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("d M yyyy");
        LocalDate newDate;

        if (type.equals("Previous")) {
            currentMonth--;
            selectPanel.setMonth(currentMonth);
            button = selectPanel.getCurrentMonthButtonOf(button);
        } else if (type.equals("Next")) {
            currentMonth++;
            selectPanel.setMonth(currentMonth);
            button = selectPanel.getCurrentMonthButtonOf(button);
        }
        newDate = LocalDate.parse(dayNum + " " + currentMonth + " 2021", format);

        selectPanel.getSelectedButton().setBackground(Constantes.CURRENT_MONTH_COL);
        selectPanel.setSelectedButton(button);
        button.setBackground(Constantes.SELECT_COL);
        selectPanel.setDate(newDate);
        dataPanel.setInfos(selectPanel.getPort(), selectPanel.getDate());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            System.out.println("Droite");
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            System.out.println("Gauche");
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            System.out.println("Bas");
        else if (e.getKeyCode() == KeyEvent.VK_UP)
            System.out.println("Haut");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

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
