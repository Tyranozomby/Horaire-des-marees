package vue;

import constantes.Constantes;
import modele.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.LocalDate;

public class PanelDonnees extends JPanel {

    TableModelMarees modeleMarees = new TableModelMarees();
    TableModelHauteur modeleHauteur = new TableModelHauteur();

    JTable tableauMarees = new JTable(modeleMarees);
    JTable tableauHauteurs = new JTable(modeleHauteur);


    public PanelDonnees() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        // MARÉES

        tableauMarees.setDefaultRenderer(Object.class, new MareesCellRenderer());

        tableauMarees.getTableHeader().setPreferredSize(new Dimension(10, 30));
        tableauMarees.getTableHeader().setBackground(Constantes.HEADER_BG);
        tableauMarees.getTableHeader().setFont(Constantes.HEADER_FONT);
        TableColumnModel cm = tableauMarees.getColumnModel();
        for (int i = 0; i < cm.getColumnCount(); i++) {
            cm.getColumn(i).setPreferredWidth(50);
        }
        tableauMarees.setRowHeight(30);

        JScrollPane paneMarees = new JScrollPane(tableauMarees);
        paneMarees.setPreferredSize(new Dimension(520, 93));

        // HAUTEURS

        tableauHauteurs.setDefaultRenderer(Object.class, new HauteursCellRenderer());

        tableauHauteurs.getTableHeader().setPreferredSize(new Dimension(10, 30));
        tableauHauteurs.getTableHeader().setBackground(Constantes.HEADER_BG);
        tableauHauteurs.getTableHeader().setFont(Constantes.HEADER_FONT);
        cm = tableauHauteurs.getColumnModel();
        cm.getColumn(0).setPreferredWidth(60);
        for (int i = 1; i < cm.getColumnCount(); i++) {
            cm.getColumn(i).setPreferredWidth(35);
        }
        tableauHauteurs.setRowHeight(30);

        JScrollPane paneHauteurs = new JScrollPane(tableauHauteurs);
        paneHauteurs.setPreferredSize(new Dimension(520, 93));

        // AJOUT

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



}
