package vue;

import constantes.Constantes;
import control.MainController;
import modele.Calendrier;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.Insets;

import java.time.LocalDate;
import java.util.TreeSet;

/**
 * Classe pour créer un calendrier de boutons correspondant à un mois donné.<br/>
 * Utilise la classe Calendrier pour récupérer les dates.<br/>
 *
 * @see Calendrier
 */
public class PanelCalendrier extends JPanel {

    private final JButton[] boutons = new JButton[42];

    public PanelCalendrier(int mois) {
        setOpaque(false);
        setLayout(new GridLayout(0, 7, 8, 8));
        TreeSet<LocalDate> dates = Calendrier.getDates(mois);

        for (String day : Constantes.NOM_JOURS) {
            JLabel label = new JLabel("<html><u>" + day + "</u></hmtl>", JLabel.CENTER);
            add(label);
        }

        int indice = 0;
        for (LocalDate date : dates) {
            JButton bouton = new JButton(String.valueOf(date.getDayOfMonth()));
            bouton.setFocusPainted(false);

            if (date.getMonthValue() < mois) {
                bouton.setBackground(Constantes.OTHER_MONTH_COL);
                bouton.setActionCommand("Previous " + bouton.getText());
                if (date.getYear() != 2021) {
                    bouton.setEnabled(false);
                }
            } else if (date.getMonthValue() > mois) {
                bouton.setBackground(Constantes.OTHER_MONTH_COL);
                bouton.setActionCommand("Next " + bouton.getText());
                if (date.getYear() != 2021) {
                    bouton.setEnabled(false);
                }
            } else if (date.getMonthValue() == mois) {
                bouton.setBackground(Constantes.CURRENT_MONTH_COL);
                bouton.setActionCommand("Current " + bouton.getText());
            }

            boutons[indice] = bouton;
            indice++;
            add(bouton);
        }
    }

    /**
     * Méthode pour récupérer le bouton correspondant au jour demandé dans le mois actuel.<br/>
     *
     * @param jour le jour voulu
     * @return le bouton à définir comme actuel ou null.
     */
    public JButton getButtonOf(int jour) {
        for (JButton dest : boutons) {
            String cmdDest = dest.getActionCommand();
            if (cmdDest.startsWith("Current")) {

                String[] split = cmdDest.split(" ");
                int dayDest = Integer.parseInt(split[1]);

                if (dayDest == jour)
                    return dest;
            }
        }
        return null;
    }

    /**
     * Méthode pour trouver le bouton avec la commande donnée.<br/>
     * Utilisé pour le déplacement avec les flèches.
     *
     * @param command la commande à comparer.
     * @return le bouton correspondant ou null.
     */
    public JButton getButtonOf(String command) {
        for (JButton butt : boutons) {
            if (butt.getActionCommand().equals(command)) {
                return butt;
            }
        }
        return null;
    }

    public void addListener(MainController controller) {
        for (JButton bouton : boutons) {
            bouton.addActionListener(controller);
        }
    }

    @Override
    public Insets getInsets() {
        return new Insets(10, 50, 30, 50);
    }
}
