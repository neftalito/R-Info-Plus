
package form;

import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.JDialog;
import javax.swing.JColorChooser;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.AbstractCellEditor;

public class ColorEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    Color currentColor;
    JButton button;
    JColorChooser colorChooser;
    JDialog dialog;
    protected static final String EDIT = "edit";

    public ColorEditor() {
        (this.button = new JButton()).setActionCommand("edit");
        this.button.addActionListener(this);
        this.button.setBorderPainted(false);
        this.colorChooser = new JColorChooser();
        this.dialog = JColorChooser.createDialog(this.button, "Seleccione un color", true, this.colorChooser, this,
                null);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if ("edit".equals(e.getActionCommand())) {
            this.button.setBackground(this.currentColor);
            this.colorChooser.setColor(this.currentColor);
            this.dialog.setVisible(true);
            this.fireEditingStopped();
        } else {
            this.currentColor = this.colorChooser.getColor();
        }
    }

    @Override
    public Object getCellEditorValue() {
        return this.currentColor;
    }

    @Override
    public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected,
            final int row, final int column) {
        this.currentColor = (Color) value;
        return this.button;
    }
}
