package vue;

import constantes.Constantes;
import control.MainController;
import modele.Calendrier;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.TreeSet;

public class PanelCalendrier extends JPanel {

    private final JButton[] boutons = new JButton[42];

    public PanelCalendrier(int mois) {
        setOpaque(false);
        setLayout(new GridLayout(0, 7, 8, 8));
        LocalDate today = LocalDate.now();
        TreeSet<LocalDate> dates = Calendrier.getDates(mois);

        for (String day : Constantes.NOM_JOURS) {
            JLabel label = new JLabel("<HTML><U>" + day + "</U></HTML>", JLabel.CENTER);
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

            if (date.equals(today)) {
                bouton.setBackground(Constantes.SELECT_COL);
            }

            boutons[indice] = bouton;
            indice++;
            add(bouton);
        }
    }

    public JButton getActualButtonOf(JButton src) {
        for (JButton dest : boutons) {
            String cmdDest = dest.getActionCommand();
            if (cmdDest.startsWith("Current")) {
                String cmdSrc = src.getActionCommand();

                String[] split = cmdDest.split(" ");
                String dayDest = split[1];

                split = cmdSrc.split(" ");
                String daySrc = split[1];

                if (dayDest.equals(daySrc))
                    return dest;
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
