package modele;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.time.LocalTime;
import java.util.ArrayList;

public class TableModelMarees implements TableModel {

    private Marees[] listeMarees = new Marees[4];
    private static final String[] header = {"Heure (PM)", "Hauteur (PM)", "Coeff (PM)", "Heure (BM)", "Hauteur (BM)"};

    private final ArrayList<TableModelListener> listeners = new ArrayList<>();

    @Override
    public int getRowCount() {
        return 2;
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return header[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Marees mar;
        if (columnIndex >= 3) {
            mar = listeMarees[rowIndex * 2 + 1];
        } else {
            mar = listeMarees[rowIndex * 2];
        }

        if (mar == null) {
            return "///";
        }

        if (columnIndex == 0) {
            LocalTime time = mar.getHeure();
            if (time != null) {
                return time.toString();
            } else {
                return "--:--";
            }
        } else if (columnIndex == 1) {
            float hauteur = mar.getHauteur();
            if (hauteur != 0) {
                return String.valueOf(hauteur);
            } else {
                return "--.--";
            }
        } else if (columnIndex == 2) {
            int coeff = mar.getCoeff();
            if (coeff != 0) {
                return String.valueOf(coeff);
            } else {
                return "---";
            }
        } else if (columnIndex == 3) {
            LocalTime time = mar.getHeure();
            if (time != null) {
                return time.toString();
            } else {
                return "--:--";
            }
        } else { //columnIndex == 4
            float hauteur = mar.getHauteur();
            if (hauteur != 0) {
                return String.valueOf(hauteur);
            } else {
                return "--.--";
            }
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    public void setListeMarees(Marees[] list) {
        listeMarees = list;
        for (TableModelListener listener : listeners) {
            listener.tableChanged(new TableModelEvent(this));
        }
    }
}
