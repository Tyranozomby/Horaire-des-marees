package vue;

import constantes.Constantes;
import modele.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.LocalDate;

public class PanelDonnees extends JPanel {

    private final TableModelMarees modeleMarees = new TableModelMarees();
    private final TableModelHauteur modeleHauteur = new TableModelHauteur();

    private final DefaultCategoryDataset dataSet = new DefaultCategoryDataset();


    public PanelDonnees() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        // MARÉES

        JTable tableauMarees = new JTable(modeleMarees);
        tableauMarees.setDefaultRenderer(Object.class, new MareesCellRenderer());

        tableauMarees.getTableHeader().setPreferredSize(new Dimension(10, 30));
        tableauMarees.getTableHeader().setBackground(Constantes.HEADER_BG);
        tableauMarees.getTableHeader().setFont(Constantes.HEADER_FONT);
        tableauMarees.getTableHeader().setReorderingAllowed(false);  //Empêche de pouvoir déplacer des colonnes
        tableauMarees.getTableHeader().setResizingAllowed(false); //Empêche de pouvoir modifier la taille des colonnes

        TableColumnModel cm = tableauMarees.getColumnModel();
        for (int i = 0; i < cm.getColumnCount(); i++) {
            cm.getColumn(i).setPreferredWidth(50);
        }
        tableauMarees.setRowHeight(30);

        JScrollPane paneMarees = new JScrollPane(tableauMarees);
        paneMarees.setPreferredSize(new Dimension(520, 90));
        paneMarees.setBorder(BorderFactory.createEmptyBorder());

        // HAUTEURS

        JTable tableauHauteurs = new JTable(modeleHauteur);
        tableauHauteurs.setDefaultRenderer(Object.class, new HauteursCellRenderer());

        tableauHauteurs.getTableHeader().setPreferredSize(new Dimension(10, 30));
        tableauHauteurs.getTableHeader().setBackground(Constantes.HEADER_BG);
        tableauHauteurs.getTableHeader().setFont(Constantes.HEADER_FONT);
        tableauHauteurs.getTableHeader().setReorderingAllowed(false);
        tableauHauteurs.getTableHeader().setResizingAllowed(false);

        cm = tableauHauteurs.getColumnModel();
        cm.getColumn(0).setPreferredWidth(60);
        for (int i = 1; i < cm.getColumnCount(); i++) {
            cm.getColumn(i).setPreferredWidth(35);
        }
        tableauHauteurs.setRowHeight(30);

        JScrollPane paneHauteurs = new JScrollPane(tableauHauteurs);
        paneHauteurs.setPreferredSize(new Dimension(520, 90));
        paneHauteurs.setBorder(BorderFactory.createEmptyBorder());

        // GRAPHIQUE

        JFreeChart lineChart = ChartFactory.createLineChart(null, "Heure", "H a u t e u r", dataSet, PlotOrientation.VERTICAL, false, false, false);
        lineChart.setBackgroundPaint(Constantes.BG_COLOR);

        lineChart.getPlot().setBackgroundPaint(Constantes.HEADER_BG);

        CategoryPlot catPlot = lineChart.getCategoryPlot();
        catPlot.getRenderer().setSeriesPaint(0,Color.BLACK);
        catPlot.getRangeAxis().setRange(new Range(-1, 13.0));

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);
        chartPanel.setPreferredSize(new Dimension(522, 300));
        chartPanel.setPopupMenu(null);
        chartPanel.setOpaque(false);

        // AJOUT
        c.insets = new Insets(15, 0, 0, 0);

        c.gridy = 0;
        JLabel marees = new JLabel("Tableau des marées");
        marees.setFont(Constantes.TITLE_FONT);
        add(marees, c);

        c.gridy = 1;
        add(paneMarees, c);

        c.gridy = 2;
        JLabel hauteurs = new JLabel("Tableau des hauteurs");
        hauteurs.setFont(Constantes.TITLE_FONT);
        add(hauteurs, c);

        c.gridy = 3;
        add(paneHauteurs, c);

        c.gridy = 4;
        JLabel graphe = new JLabel("Graphique des hauteurs");
        graphe.setFont(Constantes.TITLE_FONT);
        add(graphe, c);

        c.insets = new Insets(0, 0, 0, 30);
        c.gridy = 5;
        add(chartPanel, c);
    }


    public void setInfos(Port port, LocalDate date) {
        if (port.getMap().containsKey(date)) {
            modeleMarees.setListeMarees(port.getMap().get(date).getMarees());

            float[] tab = port.getMap().get(date).getHauteurs();
            modeleHauteur.setListeHauteurs(tab);

            dataSet.clear();
            for (int i = 0; i < tab.length; i++) { //Boucle permettant l'ajout des valeurs dans le graphes
                if (tab[i] != 0.0) {
                    dataSet.addValue(tab[i], "Hauteur", String.valueOf(i));
                } else {
                    dataSet.addValue(null, "Hauteur", String.valueOf(i));
                }
            }
        } else {
            modeleMarees.setListeMarees(new Marees[4]);

            float[] tab = new float[24];
            modeleHauteur.setListeHauteurs(tab);
            dataSet.clear();
            for (int i = 0; i < tab.length; i++) {
                dataSet.addValue(null, "Hauteur", String.valueOf(i));
            }
        }
    }


}
