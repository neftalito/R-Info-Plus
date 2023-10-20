
package form;

import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.JLabel;

public class ColorRenderer extends JLabel implements TableCellRenderer {
    Border unselectedBorder;
    Border selectedBorder;
    boolean isBordered;

    public ColorRenderer(final boolean isBordered) {
        this.unselectedBorder = null;
        this.selectedBorder = null;
        this.isBordered = true;
        this.isBordered = isBordered;
        this.setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(final JTable table, final Object color, final boolean isSelected,
            final boolean hasFocus, final int row, final int column) {
        final Color newColor = (Color) color;
        this.setBackground(newColor);
        if (this.isBordered) {
            if (isSelected) {
                if (this.selectedBorder == null) {
                    this.selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground());
                }
                this.setBorder(this.selectedBorder);
            } else {
                if (this.unselectedBorder == null) {
                    this.unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground());
                }
                this.setBorder(this.unselectedBorder);
            }
        }
        this.setToolTipText("RGB value: " + newColor.getRed() + ", " + newColor.getGreen() + ", " + newColor.getBlue());
        return this;
    }
}
