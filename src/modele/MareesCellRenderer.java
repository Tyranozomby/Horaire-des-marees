package modele;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * CellRenderer for tides table
 */
public class MareesCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = new JLabel((String) value,JLabel.CENTER);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));


        return label;
    }
}
