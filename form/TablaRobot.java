
package form;

import java.util.EventObject;
import javax.swing.event.ChangeEvent;
import javax.swing.event.CellEditorListener;
import java.util.LinkedList;
import java.beans.PropertyChangeEvent;
import java.awt.Component;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.table.TableCellEditor;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.GridBagLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.GridBagConstraints;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

public class TablaRobot extends JPanel implements PropertyChangeListener {
    final String[] columnNames;
    GridBagConstraints gbc;
    Ciudad city;
    final DefaultTableModel modelo;

    TablaRobot(final Ciudad city) {
        this.columnNames = new String[] { "Robot", "Flores", "Papeles", "Color" };
        this.gbc = new GridBagConstraints();
        this.city = city;
        this.setLayout(new GridBagLayout());
        this.gbc.fill = 1;
        (this.modelo = new MiModelo()).addColumn("Robot");
        this.modelo.addColumn("Flores");
        this.modelo.addColumn("Papeles");
        this.modelo.addColumn("Color");
        this.city.addPropertyChangeListener(this);
        this.modelo.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(final TableModelEvent e) {
                final int row = e.getFirstRow();
                final int col = e.getColumn();
                if (col >= 0) {
                    final Object value = TablaRobot.this.modelo.getValueAt(row, col);
                    switch (col) {
                        case 1: {
                            if (value == null) {
                                city.robots.get(row).setFloresEnBolsaDeConfiguracion(0);
                                break;
                            }
                            final int x = Integer.parseInt(value.toString());
                            city.robots.get(row).setFloresEnBolsaDeConfiguracion(x);
                            break;
                        }
                        case 2: {
                            if (value == null) {
                                city.robots.get(row).setPapelesEnBolsaDeConfiguracion(0);
                                break;
                            }
                            final int x = Integer.parseInt(value.toString());
                            city.robots.get(row).setPapelesEnBolsaDeConfiguracion(x);
                            break;
                        }
                        case 3: {
                            final Color co = (Color) value;
                            city.robots.get(row).setColor(co);
                            city.form.jsp.refresh();
                            break;
                        }
                    }
                }
            }
        });
        final Object[] datos = new Object[4];
        for (final Robot rr : this.city.robots) {
            datos[0] = rr.getNombre();
            datos[1] = 0;
            datos[2] = 0;
            datos[3] = rr.getColor();
            this.modelo.addRow(datos);
        }
        final JTable table1 = new JTable(this.modelo);
        table1.setDefaultRenderer(Color.class, new ColorRenderer(true));
        table1.setDefaultEditor(Color.class, new ColorEditor());
        table1.setPreferredScrollableViewportSize(new Dimension(200, 80));
        this.add(new JScrollPane(table1), this.gbc);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        final String propertyName = evt.getPropertyName();
        final String propertyValue = evt.getNewValue().toString();
        if (propertyName.equals("numRobots")) {
            final int i = Integer.parseInt(propertyValue) - 1;
            final Robot rr = this.city.robots.get(i);
            final Object[] datos = { rr.getNombre(), 0, 0, rr.getColor() };
            this.modelo.addRow(datos);
        }
    }

    public class MiModelo extends DefaultTableModel implements TableCellEditor {
        private LinkedList suscriptores;

        MiModelo() {
            this.suscriptores = new LinkedList();
        }

        @Override
        public void addCellEditorListener(final CellEditorListener l) {
            this.suscriptores.add(l);
        }

        protected void editado(final boolean cambiado) {
            final ChangeEvent evento = new ChangeEvent(this);
            for (int i = 0; i < this.suscriptores.size(); ++i) {
                final CellEditorListener aux = (CellEditorListener) this.suscriptores.get(i);
                if (cambiado) {
                    aux.editingStopped(evento);
                } else {
                    aux.editingCanceled(evento);
                }
            }
        }

        @Override
        public boolean isCellEditable(final int row, final int column) {
            return column > 0;
        }

        @Override
        public Class getColumnClass(final int columna) {
            if (columna == 1) {
                return Integer.class;
            }
            if (columna == 2) {
                return Integer.class;
            }
            return this.getValueAt(0, columna).getClass();
        }

        @Override
        public int getColumnCount() {
            return TablaRobot.this.columnNames.length;
        }

        @Override
        public String getColumnName(final int col) {
            return TablaRobot.this.columnNames[col];
        }

        @Override
        public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected,
                final int row, final int column) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Object getCellEditorValue(final int aRow, final int aColumn) {
            System.out.println(" ACA ESTOY");
            return null;
        }

        @Override
        public boolean shouldSelectCell(final EventObject anEvent) {
            return true;
        }

        @Override
        public boolean stopCellEditing() {
            return true;
        }

        @Override
        public void cancelCellEditing() {
        }

        @Override
        public void removeCellEditorListener(final CellEditorListener l) {
            this.suscriptores.remove(l);
        }

        @Override
        public boolean isCellEditable(final EventObject anEvent) {
            return true;
        }

        @Override
        public Object getCellEditorValue() {
            System.out.println("IMPRIMI");
            return null;
        }
    }
}
