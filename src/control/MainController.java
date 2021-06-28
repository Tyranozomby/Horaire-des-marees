package control;

import constantes.Constantes;
import util.ParsingData;
import vue.*;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe principale, utilisée pour lancer le projet.<br/>
 * Crée la fenêtre et lie la selection de port ou de date avec l'affichage des données.<br/>
 * Utilise un thread to pour les fichiers de données, tout en incrémentant une barre de progression.
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
            load.setText("Chargement de l'affichage général...");

            SuperPanel superPanel = new SuperPanel();
            selectPanel = superPanel.getSelectPanel();
            dataPanel = superPanel.getDataPanel();

            selectPanel.addListener(this);
            dateChange(selectPanel.getButtonOfDay(LocalDate.now().getDayOfMonth()));

            mere.setContentPane(superPanel); //Affiche le panel principal
            mere.validate(); //Valide la modification

            // Équivalent à un keyListener
            keyListener(mere);
        }).start();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        int currentMonth = selectPanel.getMonth();

        if (command.contains(" ")) {
            dateChange((JButton) e.getSource());
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

    /**
     * Détecte la date sélectionnée et change l'affichage du calendrier.
     *
     * @param button JButton du calendrier.
     */
    private void dateChange(JButton button) {
        String command = button.getActionCommand();
        int currentMonth = selectPanel.getMonth();

        String[] split = command.split(" ");
        int dayNum = Integer.parseInt(split[1]);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("d M yyyy");
        LocalDate newDate;

        if (command.startsWith("Previous ")) {
            currentMonth--;
        } else if (command.startsWith("Next ")) {
            currentMonth++;
        }
        newDate = LocalDate.parse(dayNum + " " + currentMonth + " 2021", format);

        selectPanel.setMonth(currentMonth);
        button = selectPanel.getButtonOfDay(dayNum);
        selectPanel.getSelectedButton().setBackground(Constantes.CURRENT_MONTH_COL);
        selectPanel.setSelectedButton(button);
        selectPanel.setDate(newDate);

        button.setBackground(Constantes.SELECT_COL);
        dataPanel.setInfos(selectPanel.getPort(), selectPanel.getDate());
    }

    /**
     * Méthode appelée quand une des clés enregistrées a été pressée.
     * Permet de se déplacer dans le calendrier avec gauche and droite ou de changer le port avec haut et bas.
     *
     * @param key valeur entière de la clé.
     */
    public void keyPressed(int key) {
        JButton retour = null;

        if (key == KeyEvent.VK_RIGHT)
            retour = selectPanel.leftRight(selectPanel.getDate().plusDays(1));
        else if (key == KeyEvent.VK_LEFT)
            retour = selectPanel.leftRight(selectPanel.getDate().minusDays(1));
        else if (key == KeyEvent.VK_DOWN)
            selectPanel.down();
        else if (key == KeyEvent.VK_UP)
            selectPanel.up();

        if (retour != null)
            dateChange(retour);

    }

    /**
     * Clés que nous voulons écouter.
     *
     * @param mere fenêtre principale.
     */
    public void keyListener(JFrame mere) {
        mere.getRootPane().registerKeyboardAction(e -> keyPressed(KeyEvent.VK_LEFT), KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        mere.getRootPane().registerKeyboardAction(e -> keyPressed(KeyEvent.VK_RIGHT), KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        mere.getRootPane().registerKeyboardAction(e -> keyPressed(KeyEvent.VK_UP), KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        mere.getRootPane().registerKeyboardAction(e -> keyPressed(KeyEvent.VK_DOWN), KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }


    /**
     * Méthode pour lancer le programme.
     *
     * @param args pas utilisés.
     */
    public static void main(String[] args) {
        new MainController();
    }
}
