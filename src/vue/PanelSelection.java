package vue;

import constantes.Constantes;
import control.MainController;
import modele.Port;
import util.LectureEcriture;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PanelSelection extends JPanel {

    private final JComboBox<Port> comboBox;

    private final JLabel labelDate = new JLabel();
    private final JLabel labelMois = new JLabel("", JLabel.CENTER);

    private LocalDate currentDate = LocalDate.now();
    private int shownMonth = currentDate.getMonthValue();
    private JButton selected;

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy");

    private final PanelCalendrier[] calendriers = new PanelCalendrier[12];
    private final String[] NOM_BOUTONS = {"<<", "<", ">", ">>"};
    private final JButton[] boutonsNav = new JButton[NOM_BOUTONS.length];

    private final CardLayout layout = new CardLayout();
    private final JPanel panelCentre = new JPanel(layout);

    public PanelSelection() {
        setOpaque(false);
        setLayout(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Panel Nord
        JPanel panelNord = new JPanel(new GridBagLayout());
        panelNord.setOpaque(false);

        Port[] listePorts = LectureEcriture.lireTout(new File(Constantes.OBJ_FILE));
        comboBox = new JComboBox<>(listePorts);
        panelNord.add(comboBox, c);

        c.insets = new Insets(20, 0, 0, 0);
        c.gridy = 1;
        labelDate.setText(currentDate.format(format));
        labelDate.setFont(Constantes.DATE_FONT);
        panelNord.add(labelDate, c);


        // Panel CalendrierAct
        panelCentre.setOpaque(false);

        for (int i = 0; i < 12; i++) {
            calendriers[i] = new PanelCalendrier(i + 1);
            panelCentre.add(calendriers[i], Constantes.NOM_MOIS[i]);
        }
        selected = calendriers[shownMonth - 1].getSelected();
        panelCentre.setPreferredSize(new Dimension(540, 400));
        layout.show(panelCentre, Constantes.NOM_MOIS[LocalDate.now().getMonthValue() - 1]);


        // Panel Navigation
        JPanel panelSud = new JPanel(new GridBagLayout());
        panelSud.setOpaque(false);
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < 2; i++) {
            c.gridx = i;
            JButton bouton = new JButton(NOM_BOUTONS[i]);
            bouton.setPreferredSize(new Dimension(50, 30));
            bouton.setFocusPainted(false);
            boutonsNav[i] = bouton;
            panelSud.add(boutonsNav[i], c);
        }

        c.gridx = 2;
        labelMois.setText(Constantes.NOM_MOIS[currentDate.getMonthValue() - 1]);
        labelMois.setFont(Constantes.DATE_FONT);
        labelMois.setPreferredSize(new Dimension(110, 20));
        panelSud.add(labelMois, c);

        for (int i = 2; i < NOM_BOUTONS.length; i++) {
            c.gridx = i + 1;
            JButton bouton = new JButton(NOM_BOUTONS[i]);
            bouton.setPreferredSize(new Dimension(50, 30));
            bouton.setFocusPainted(false);
            boutonsNav[i] = bouton;
            panelSud.add(boutonsNav[i], c);
        }


        // Ajout Panels
        add(panelNord, BorderLayout.NORTH);
        add(panelCentre, BorderLayout.CENTER);
        add(panelSud, BorderLayout.SOUTH);
    }

    public LocalDate getDate() {
        return currentDate;
    }

    public void setDate(LocalDate date) {
        currentDate = date;
        labelDate.setText(currentDate.format(format));
    }

    public Port getPort() {
        return (Port) comboBox.getSelectedItem();
    }

    public JButton getSelected() {
        return selected;
    }

    public void setSelected(JButton selected) {
        this.selected = selected;
    }

    public JButton getCurrentMonthButtonOf(JButton bouton) {
        return calendriers[shownMonth - 1].getButtonOf(bouton);
    }

    public int getMonth() {
        return shownMonth;
    }

    public void setMonth(int mois) {
        layout.show(panelCentre, Constantes.NOM_MOIS[mois - 1]);
        labelMois.setText(Constantes.NOM_MOIS[mois - 1]);
        shownMonth = mois;
    }

    public void addListener(MainController controller) {
        for (PanelCalendrier cal : calendriers) {
            cal.addListener(controller);
        }
        for (int i = 0; i < NOM_BOUTONS.length; i++) {
            boutonsNav[i].setActionCommand(NOM_BOUTONS[i]);
            boutonsNav[i].addActionListener(controller);
        }
        comboBox.setActionCommand("Port");
        comboBox.addActionListener(controller);
    }

    @Override
    public Insets getInsets() {
        return new Insets(50, 0, 50, 0);
    }


}
