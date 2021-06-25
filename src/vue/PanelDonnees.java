package vue;

import modele.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;

public class PanelDonnees extends JPanel {

    TableModelMarees modeleMarees = new TableModelMarees();
    TableModelHauteur modeleHauteur = new TableModelHauteur();

    JTable tableauMarees = new JTable(modeleMarees);
    JTable tableauHauteurs = new JTable(modeleHauteur);


    public PanelDonnees() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(540, 720));

        GridBagConstraints c = new GridBagConstraints();

        tableauMarees.setDefaultRenderer(Object.class, new MareesCellRenderer());
        tableauHauteurs.setDefaultRenderer(Object.class, new HauteursCellRenderer());


        tableauMarees.getTableHeader().setPreferredSize(new Dimension(10, 30));
        TableColumnModel cm = tableauMarees.getColumnModel();
        for (int i = 0; i < cm.getColumnCount(); i++) {
            cm.getColumn(i).setPreferredWidth(50);
        }
        tableauMarees.setRowHeight(30);

        tableauHauteurs.getTableHeader().setPreferredSize(new Dimension(10, 30));
        cm = tableauHauteurs.getColumnModel();
        cm.getColumn(0).setPreferredWidth(60);
        for (int i = 1; i < cm.getColumnCount(); i++) {
            cm.getColumn(i).setPreferredWidth(35);
        }
        tableauHauteurs.setRowHeight(30);

        JScrollPane paneMarees = new JScrollPane(tableauMarees);
        paneMarees.setPreferredSize(new Dimension(520, 93));

        JScrollPane paneHauteurs = new JScrollPane(tableauHauteurs);
        paneHauteurs.setPreferredSize(new Dimension(520, 93));

        c.gridy = 0;
        add(new JLabel("Marées"), c);
        c.gridy = 1;
        add(paneMarees, c);
        c.gridy = 2;
        add(new JLabel("Hauteurs"), c);
        c.gridy = 3;
        add(paneHauteurs, c);
    }

    public void setInfos(Port port, LocalDate date) {
        if (port.getMap().containsKey(date)) {
            modeleMarees.setListeMarees(port.getMap().get(date).getMarees());
            modeleHauteur.setListeHauteurs(port.getMap().get(date).getHauteurs());
        } else {
            modeleMarees.setListeMarees(new Marees[4]);
            modeleHauteur.setListeHauteurs(new float[24]);
        }
    }

    @Override
    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

}
