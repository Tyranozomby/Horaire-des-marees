package vue;

import constantes.Constantes;
import control.MainController;
import modele.Port;
import util.LectureEcriture;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import java.awt.Insets;
import java.awt.Dimension;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PanelSelection extends JPanel {

    private final JComboBox<Port> comboBox;

    private final JLabel labelDate = new JLabel();
    private final JLabel labelCo = new JLabel("", JLabel.CENTER);
    private final JLabel labelMois = new JLabel("", JLabel.CENTER);

    private LocalDate currentDate = LocalDate.now();
    private int currentMonth = currentDate.getMonthValue();
    private JButton selectedButton = new JButton();

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy");

    private final PanelCalendrier[] calendriers = new PanelCalendrier[12];
    private final JButton[] boutonsNav = new JButton[Constantes.NOM_BOUTONS.length];

    private final CardLayout layoutCal = new CardLayout();
    private final JPanel panelCal = new JPanel(layoutCal);

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
        Port port = (Port) comboBox.getSelectedItem();
        assert port != null;
        setLabelCo(port);
        labelCo.setFont(Constantes.TITLE_FONT);
        panelNord.add(labelCo, c);

        c.gridy = 2;
        labelDate.setText(currentDate.format(format));
        labelDate.setFont(Constantes.TITLE_FONT);
        panelNord.add(labelDate, c);


        // Panel CalendrierAct
        panelCal.setOpaque(false);

        for (int i = 0; i < 12; i++) {
            calendriers[i] = new PanelCalendrier(i + 1);
            panelCal.add(calendriers[i], Constantes.NOM_MOIS[i]);
        }
        layoutCal.show(panelCal, Constantes.NOM_MOIS[LocalDate.now().getMonthValue() - 1]);


        // Panel Navigation
        JPanel panelSud = new JPanel(new GridBagLayout());
        panelSud.setOpaque(false);
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < Constantes.NOM_BOUTONS.length / 2; i++) {   // < et <<
            c.gridx = i;
            JButton bouton = new JButton(Constantes.NOM_BOUTONS[i]);
            bouton.setPreferredSize(new Dimension(50, 30));
            bouton.setFocusPainted(false);
            boutonsNav[i] = bouton;
            panelSud.add(boutonsNav[i], c);
        }

        c.gridx = 2;
        labelMois.setText(Constantes.NOM_MOIS[currentDate.getMonthValue() - 1]);    // Mois affiché
        labelMois.setFont(Constantes.TITLE_FONT);
        labelMois.setPreferredSize(new Dimension(110, 20));
        panelSud.add(labelMois, c);

        for (int i = 2; i < Constantes.NOM_BOUTONS.length; i++) {   // >> et >
            c.gridx = i + 1;
            JButton bouton = new JButton(Constantes.NOM_BOUTONS[i]);
            bouton.setPreferredSize(new Dimension(50, 30));
            bouton.setFocusPainted(false);
            boutonsNav[i] = bouton;
            panelSud.add(boutonsNav[i], c);
        }


        // Ajout Panels
        add(panelNord, BorderLayout.NORTH);
        add(panelCal, BorderLayout.CENTER);
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

    public void setLabelCo(Port port) {
        labelCo.setText("Coordonnées: " + port.getLongitude() + " | " + port.getLatitude());
    }

    public JButton getSelectedButton() {
        return selectedButton;
    }

    public void setSelectedButton(JButton selectedButton) {
        this.selectedButton = selectedButton;
    }

    public JButton getCurrentMonthButtonOf(JButton bouton) {
        return calendriers[currentMonth - 1].getActualButtonOf(bouton);
    }

    public int getMonth() {
        return currentMonth;
    }

    public void setMonth(int mois) {
        layoutCal.show(panelCal, Constantes.NOM_MOIS[mois - 1]);
        labelMois.setText(Constantes.NOM_MOIS[mois - 1]);
        currentMonth = mois;
    }

    public void addListener(MainController controller) {
        for (PanelCalendrier cal : calendriers) {
            cal.addListener(controller);
        }
        for (int i = 0; i < Constantes.NOM_BOUTONS.length; i++) {
            boutonsNav[i].setActionCommand(Constantes.NOM_BOUTONS[i]);
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
