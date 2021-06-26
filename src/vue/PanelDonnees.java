package vue;

import constantes.Constantes;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import modele.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

import org.jfree.chart.*;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

public class PanelDonnees extends JPanel {

    private TableModelMarees modeleMarees = new TableModelMarees();
    private TableModelHauteur modeleHauteur = new TableModelHauteur();

    private  JTable tableauMarees = new JTable(modeleMarees);
    private JTable tableauHauteurs = new JTable(modeleHauteur);

    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset();


    public PanelDonnees() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        // MARÉES

        tableauMarees.setDefaultRenderer(Object.class, new MareesCellRenderer());

        tableauMarees.getTableHeader().setPreferredSize(new Dimension(10, 30));
        tableauMarees.getTableHeader().setBackground(Constantes.HEADER_BG);
        tableauMarees.getTableHeader().setFont(Constantes.HEADER_FONT);
        tableauMarees.getTableHeader().setReorderingAllowed(false);  //Empeche de pouvoir déplacer des colonnes
        tableauMarees.getTableHeader().setResizingAllowed(false); //Empeche de pouvoir modifier la taille des colonnes

        TableColumnModel cm = tableauMarees.getColumnModel();
        for (int i = 0; i < cm.getColumnCount(); i++) {
            cm.getColumn(i).setPreferredWidth(50);
        }
        tableauMarees.setRowHeight(30);

        JScrollPane paneMarees = new JScrollPane(tableauMarees);
        paneMarees.setPreferredSize(new Dimension(520, 90));
        paneMarees.setBorder(BorderFactory.createEmptyBorder());




        // HAUTEURS

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


        // Graphique
        JFreeChart lineChart = ChartFactory.createLineChart("Graphique",
                "Heure","Hauteur",
                dataSet,
                PlotOrientation.VERTICAL,
                false,true,false );

        CategoryPlot plot = lineChart.getCategoryPlot();
        ValueAxis axis = plot.getRangeAxis();
        axis.setRange(new Range(-2,12.0));

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setDomainZoomable(false); //empecher le zoom lors d'une selection
        chartPanel.setRangeZoomable(false);
        chartPanel.setPreferredSize( new Dimension( 522 , 300 ) );
        chartPanel.setPopupMenu(null);

        // AJOUT

        c.gridy = 0;
        add(new JLabel("Marées"), c);
        c.gridy = 1;
        add(paneMarees, c);
        c.gridy = 2;
        add(new JLabel("Hauteurs"), c);
        c.gridy = 3;
        add(paneHauteurs, c);
        c.gridy = 4;
        add(chartPanel,c);
    }


    public void setInfos(Port port, LocalDate date) {
        if (port.getMap().containsKey(date)) {
            float[] tab = port.getMap().get(date).getHauteurs();
            modeleMarees.setListeMarees(port.getMap().get(date).getMarees());
            modeleHauteur.setListeHauteurs(port.getMap().get(date).getHauteurs());

            dataSet.clear();
            for (int i = 0; i < 24 ; i++){ //Boucle permettant l'ajout des valeurs dans le graphes
                dataSet.addValue(tab[i],"Hauteur", String.valueOf(i));
            }
        } else {
            modeleMarees.setListeMarees(new Marees[4]);
            modeleHauteur.setListeHauteurs(new float[24]);
            dataSet.clear();
        }
    }



}
