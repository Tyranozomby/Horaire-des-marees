package modele;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.TableCellRenderer;

import java.awt.Component;
import java.awt.Font;

/**
 * TableCellRenderer pour le tableau des marées.
 *
 * @see MareesTableModel
 */
public class MareesCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = new JLabel((String) value, JLabel.CENTER);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

        return label;
    }
}
