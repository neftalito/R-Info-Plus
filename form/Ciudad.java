
package form;

import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Ciudad {
    MonitorActualizarVentana esperarRefresco;
    boolean ok;
    private int numAv;
    private int numCa;
    public int cantidad_robots;
    public Bolsa[][] ciudad;
    ArrayList<Robot> robots;
    ArrayList<Area> areas;
    ArrayList<Thread> hilos;
    private final PropertyChangeSupport pcs;
    Main form;

    public Ciudad(final Main city) throws Exception {
        this.esperarRefresco = MonitorActualizarVentana.getInstance();
        this.cantidad_robots = 1;
        this.ciudad = new Bolsa[101][101];
        this.robots = new ArrayList<Robot>();
        this.areas = new ArrayList<Area>();
        this.hilos = new ArrayList<Thread>();
        this.pcs = new PropertyChangeSupport(this);
        this.setNumAv(100);
        this.setNumCa(100);
        for (int i = 0; i < this.ciudad.length; ++i) {
            for (int j = 0; j < this.ciudad[0].length; ++j) {
                this.ciudad[i][j] = new Bolsa();
            }
        }
        this.form = city;
        this.ok = false;
    }

    public void agregarHilo(final Thread t) {
        this.hilos.add(t);
    }

    public void setNumAv(final int num) {
        this.numAv = num;
    }

    public void setNumCa(final int num) {
        this.numCa = num;
    }

    public int getNumAv() {
        return this.numAv;
    }

    public int getNumCa() {
        return this.numCa;
    }

    public boolean getOk() {
        return this.ok;
    }

    public void setOk(final boolean ok) {
        this.ok = ok;
    }

    public int getCantidad_robots() {
        return this.cantidad_robots;
    }

    public void setCantidad_robots(final int cantidad_robots) {
        this.cantidad_robots = cantidad_robots;
    }

    public boolean isFreePos(final int ca, final int av) throws Exception {
        if (ca < 1 || ca > this.getNumCa()) {
            this.parseError(
                    "No se puede ejecutar la instrucci\u00f3n \"mover\" debido a que el robot se caer\u00eda de la ciudad");
            throw new Exception(
                    "No se puede ejecutar la instrucci\u00f3n \"mover\" debido a que el robot se caer\u00eda de la ciudad");
        }
        if (av < 1 || av > this.getNumAv()) {
            this.parseError(
                    "No se puede ejecutar la instrucci\u00f3n \"mover\" debido a que el robot se caer\u00eda de la ciudad");
            throw new Exception(
                    "No se puede ejecutar la instrucci\u00f3n \"mover\" debido a que hay un obst\u00e1culo");
        }
        return !this.ciudad[av][ca].getObstaculo();
    }

    public boolean isFreePosRobot(final int ca, final int av) {
        return this.ciudad[av][ca].isOcupado();
    }

    public boolean levantarFlor(final int Av, final int Ca) {
        boolean res = true;
        if (this.HayFlorEnLaEsquina(Av, Ca)) {
            this.ciudad[Av][Ca].setFlores(this.ciudad[Av][Ca].getFlores() - 1);
        } else {
            res = false;
            JOptionPane.showMessageDialog(null,
                    "Run Time Error: No se puede ejecutar la instrucci\u00f3n \"tomarFlor\" debido a que no hay ninguna flor en la esquina ",
                    "error", 0);
        }
        this.form.jsp.refresh();
        return res;
    }

    public boolean HayFlorEnLaEsquina(final int Av, final int Ca) {
        return this.getFlores(Av, Ca) > 0;
    }

    public boolean HayObstaculoEnLaEsquina(final int Av, final int Ca) throws Exception {
        switch (this.robots.get(0).getDireccion()) {
            case 0: {
                return Av < 100 && this.ciudad[Av + 1][Ca].getObstaculo();
            }
            case 180: {
                return Av > 1 && this.ciudad[Av - 1][Ca].getObstaculo();
            }
            case 90: {
                return Ca < 100 && this.ciudad[Av][Ca + 1].getObstaculo();
            }
            case 270: {
                return Ca > 1 && this.ciudad[Av][Ca - 1].getObstaculo();
            }
            default: {
                System.out.println("Fallo en la direccion de Ciudad");
                throw new Exception("Fallo en la direccion de Ciudad");
            }
        }
    }

    public int getFlores(final int Av, final int Ca) {
        return this.ciudad[Av][Ca].getFlores();
    }

    public boolean HayPapelEnLaEsquina(final int Av, final int Ca) {
        return this.getPapeles(Av, Ca) > 0;
    }

    public int getPapeles(final int Av, final int Ca) {
        return this.ciudad[Av][Ca].getPapeles();
    }

    public boolean levantarPapel(final int Av, final int Ca) {
        boolean res = true;
        if (this.HayPapelEnLaEsquina(Av, Ca)) {
            this.ciudad[Av][Ca].setPapeles(this.ciudad[Av][Ca].getPapeles() - 1);
        } else {
            res = false;
            JOptionPane.showMessageDialog(null,
                    "Run Time Error: No se puede ejecutar la instrucci\u00f3n \"tomarPapel\" debido a que no hay ningun papel en la esquina",
                    "error", 0);
        }
        this.form.jsp.refresh();
        return res;
    }

    public void dejarPapel(final int Av, final int Ca) {
        final int old = this.getPapeles(Av, Ca);
        this.ciudad[Av][Ca].setPapeles(old + 1);
        this.pcs.firePropertyChange("EsquinaPapeles", old, this.getPapeles(Av, Ca));
        this.form.jsp.refresh();
    }

    public void dejarFlor(final int Av, final int Ca) {
        final int old = this.getFlores(Av, Ca);
        this.ciudad[Av][Ca].setFlores(old + 1);
        this.pcs.firePropertyChange("EsquinaFlores", old, this.getFlores(Av, Ca));
        this.form.jsp.refresh();
    }

    public void parseError(final String msg) {
        JOptionPane.showMessageDialog(null, "Run Time Error: " + msg, "error", 0);
    }

    public void addRobot(final String name) {
        try {
            final int old = this.robots.size();
            this.robots.add(new Robot(this, name));
            this.esperarRefresco.setCant_robots(this.robots.size());
            this.esperarRefresco.setCant_ejecutandose(this.robots.size());
            this.pcs.firePropertyChange("numRobots", old, this.robots.size());
        } catch (Exception ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean estaRobot(final String str) {
        for (int cant = this.robots.size(), i = 0; i < cant; ++i) {
            if (this.robots.get(i).nombre.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean estaRobotConId(final int id) {
        for (int cant = this.robots.size(), i = 0; i < cant; ++i) {
            if (this.robots.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Robot getRobotByNombre(final String str) throws Exception {
        for (int cant = this.robots.size(), i = 0; i < cant; ++i) {
            if (this.robots.get(i).getNombre().equals(str)) {
                return this.robots.get(i);
            }
        }
        this.parseError("El robot " + str + " no se encuentra en la ciudad");
        throw new Exception("El robot " + str + " no se encuentra en la ciudad");
    }

    public Robot getRobotById(final int id) throws Exception {
        for (int cant = this.robots.size(), i = 0; i < cant; ++i) {
            if (this.robots.get(i).getId() == id) {
                return this.robots.get(i);
            }
        }
        throw new Exception("El robot con id " + id + " no se encuentra en la ciudad");
    }

    public Area getAreaByNombre(final String str) throws Exception {
        for (int cant = this.areas.size(), i = 0; i < cant; ++i) {
            if (this.areas.get(i).getName().equals(str)) {
                return this.areas.get(i);
            }
        }
        throw new Exception("El area " + str + " no se encuentra declarada");
    }

    public void Informar(final String args, final int id) {
        this.form.mostrarMensaje(args, "Informar", 1);
    }

    public void InformarError(final String args, final int id) {
        this.form.mostrarMensaje(args, "Error", 0);
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public boolean HayObstaculo(final int Av, final int Ca) throws Exception {
        return this.ciudad[Av][Ca].getObstaculo();
    }
}
