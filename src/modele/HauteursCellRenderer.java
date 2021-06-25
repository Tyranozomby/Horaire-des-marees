package modele;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * CellRenderer for sea level table
 */
public class HauteursCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = new JLabel("", JLabel.CENTER);
        label.setOpaque(true);

        if (value.getClass() == Double.class) {
            label.setText(String.valueOf(value));
            label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        } else {
            label.setText((String) value);
            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            label.setBackground(Color.LIGHT_GRAY);
        }

        return label;
    }
}
