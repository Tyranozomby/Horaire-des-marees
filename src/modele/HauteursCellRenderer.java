package modele;

import constantes.Constantes;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.TableCellRenderer;

import java.awt.Component;
import java.awt.Color;
import java.awt.Font;

/**
 * TableCellRenderer pour le tableau des hauteurs
 *
 * @see HauteurTableModel
 */
public class HauteursCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = new JLabel("", JLabel.CENTER);
        label.setOpaque(true);

        if (value.getClass() == Double.class || value.equals("---")) {
            label.setText(String.valueOf(value));
            label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
            label.setBackground(Color.WHITE);
        } else {
            label.setText((String) value);
            label.setFont(Constantes.HEADER_FONT);
            label.setBackground(Constantes.HEADER_BG);
        }

        return label;
    }
}
