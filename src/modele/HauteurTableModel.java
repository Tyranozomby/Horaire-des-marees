package modele;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;

/**
 * TableModel pour le tableau des hauteurs.
 *
 * @see HauteursCellRenderer
 */
public class HauteurTableModel implements TableModel {

    private float[] listeHauteur = new float[24];
    private static final String[] header = {"<html><u>Heure:</u></html>", "0h", "1h", "2h", "3h", "4h", "5h", "6h", "7h", "8h", "9h", "10h", "11h",};

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
        if (columnIndex == 0) {
            if (rowIndex == 0) {
                return "AM";
            } else {
                return "PM";
            }
        }
        float val = listeHauteur[rowIndex * 12 + (columnIndex - 1)];
        if (val == 0.0) {
            return "---";
        }
        return (float) Math.round(val * 100.0) / 100.0; // Arrondi au centième
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

    /**
     * Modifie le contenu du tableau.
     *
     * @param list la liste des nouvelles valeurs.
     */
    public void setListeHauteurs(float[] list) {
        listeHauteur = list;
        for (TableModelListener listener : listeners) {
            listener.tableChanged(new TableModelEvent(this));
        }
    }


}
