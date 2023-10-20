
package form;

import arbol.Variable;
import arbol.sentencia.Sentencia;
import arbol.ParametroFormal;
import arbol.Proceso;
import java.beans.PropertyChangeListener;
import java.awt.Image;
import arbol.Identificador;
import java.io.IOException;
import java.net.UnknownHostException;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import arbol.sentencia.primitiva.Mover;
import java.io.FileReader;
import java.io.File;
import java.awt.Color;
import java.beans.PropertyChangeSupport;
import javax.swing.ImageIcon;
import arbol.DeclaracionVariable;
import arbol.Cuerpo;
import arbol.DeclaracionProcesos;
import java.util.ArrayList;

public class Robot {
    private int ciclos;
    private ArrayList<Coord> misCalles;
    private DeclaracionProcesos procAST;
    private Cuerpo cueAST;
    private DeclaracionVariable varAST;
    private ImageIcon robotImage;
    private ArrayList<Coord> ruta;
    private ArrayList<ArrayList<Coord>> rutas;
    public int Av;
    public int Ca;
    private int direccion;
    private final PropertyChangeSupport pcs;
    private Ciudad city;
    private int floresEnBolsa;
    private int papelesEnBolsa;
    private int floresEnBolsaDeConfiguracion;
    private int papelesEnBolsaDeConfiguracion;
    private ArrayList<Area> areas;
    MonitorEsquinas esquinas;
    MonitorActualizarVentana esperarRefresco;
    public int id;
    private static int cant;
    public int offsetAv;
    public int offsetCa;
    public String dir;
    public MonitorMensajes monitor;
    String nombre;
    Color color;
    public String estado;

    public Robot(final Ciudad city, final String nombre) throws Exception {
        this.robotImage = new ImageIcon(this.getClass().getResource("/images/robotAbajo.png"));
        this.ruta = new ArrayList<Coord>();
        this.rutas = new ArrayList<ArrayList<Coord>>();
        this.Av = 0;
        this.Ca = 0;
        this.direccion = 90;
        this.pcs = new PropertyChangeSupport(this);
        this.floresEnBolsa = 0;
        this.papelesEnBolsa = 0;
        this.floresEnBolsaDeConfiguracion = 0;
        this.papelesEnBolsaDeConfiguracion = 0;
        this.esquinas = MonitorEsquinas.getInstance();
        this.esperarRefresco = MonitorActualizarVentana.getInstance();
        this.offsetAv = 0;
        this.offsetCa = 0;
        this.misCalles = new ArrayList<Coord>();
        this.areas = new ArrayList<Area>();
        this.Av = 0;
        this.Ca = 0;
        this.getRuta().add(new Coord(this.Av, this.Ca));
        this.setNombre(nombre);
        this.city = city;
        this.rutas.add(this.ruta);
        this.id = this.getCity().robots.size();
        this.color = this.getColorById(this.id);
        this.setDireccion(90);
        this.setFlores(this.getFloresEnBolsaDeConfiguracion());
        this.setPapeles(this.getPapelesEnBolsaDeConfiguracion());
        this.estado = "Nuevo  ";
    }

    public void crear() throws UnknownHostException, IOException {
        this.dir = "";
        System.out.println(this.getId());
        if (this.id == 0) {
            final int puerto = 4000;
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;
            archivo = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "Conf.txt");
            try {
                fr = new FileReader(archivo);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Mover.class.getName()).log(Level.SEVERE, null, ex);
            }
            br = new BufferedReader(fr);
            String ip = null;
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                final String[] lineas = linea.split(" ");
                final String robot = lineas[0];
                ip = lineas[1];
                System.out.println(" el robot es : " + robot + " y la ip es : " + ip);
            }
            this.dir = "192.168.0.100";
            this.dir = ip;
        } else {
            System.out.println("Entre al else");
            this.dir = "192.168.0.104";
            final int puerto = 4000;
        }
        try (final Socket s = new Socket(this.dir, 4000)) {
            System.out.println("conectados");
            final DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
            dOut.writeByte(this.getId());
            dOut.flush();
            dOut.close();
        }
    }

    public int getCiclos() {
        return this.ciclos;
    }

    public void setCiclos(final int ciclos) {
        this.ciclos = ciclos;
    }

    public Color getColorById(final int id) {
        switch (id) {
            case 0: {
                return Color.RED;
            }
            case 1: {
                return new Color(0, 137, 221);
            }
            case 2: {
                return Color.PINK;
            }
            case 3: {
                return new Color(0, 153, 68);
            }
            case 4: {
                return Color.MAGENTA;
            }
            default: {
                final int max = 255;
                final int min = 1;
                final int x = (int) (Math.random() * (max - min + 1)) + min;
                final int y = (int) (Math.random() * (max - min + 1)) + min;
                final int z = (int) (Math.random() * (max - min + 1)) + min;
                return new Color(x, y, z);
            }
        }
    }

    public void almacenarMensaje(final String nombreDestino, final String valor) throws Exception {
        final Dato d = new Dato(valor, nombreDestino);
        final int id = this.getCity().getRobotByNombre(nombreDestino).id;
        this.monitor.llegoMensaje(id, d);
    }

    public void recibirMensaje(final Identificador nombreVariable, final int id, final Identificador NombreRobot)
            throws Exception {
        this.monitor.recibirMensaje(nombreVariable, id, NombreRobot);
    }

    public void Informar(final String msj) {
        this.getCity().Informar(msj, this.id);
        this.esperarRefresco.esperar(this.id);
    }

    public void bloquearEsquina(final int Av, final int Ca) {
        this.esquinas.bloquear(Av, Ca);
        this.esperarRefresco.esperar(this.id);
        this.getCity().form.jsp.refresh();
    }

    public void liberarEsquina(final int Av, final int Ca) {
        this.esquinas.liberar(Av, Ca);
        this.esperarRefresco.esperar(this.id);
        this.getCity().form.jsp.refresh();
    }

    public Cuerpo getCuerpo() {
        return this.cueAST;
    }

    public void agregarArea(final Area a) {
        this.areas.add(a);
        for (int i = a.getAv1(); i <= a.getAv2(); ++i) {
            for (int j = a.getCa1(); j <= a.getCa2(); ++j) {
                this.misCalles.add(new Coord(i, j));
            }
        }
    }

    public boolean esAreaVacia() {
        return this.areas.isEmpty();
    }

    public void crearMonitor(final int cant) {
        this.monitor = MonitorMensajes.crearMonitorActualizarVentana(cant, this);
    }

    public void setCuerpo(final Cuerpo cueAST) {
        this.cueAST = cueAST;
    }

    public DeclaracionVariable getVariables() {
        return this.varAST;
    }

    public void setVariables(final DeclaracionVariable varAST) {
        this.varAST = varAST;
    }

    public int getFloresEnBolsaDeConfiguracion() {
        return this.floresEnBolsaDeConfiguracion;
    }

    public void setFloresEnBolsaDeConfiguracion(final int floresEnBolsaDeConfiguracion) {
        this.setFlores(this.floresEnBolsaDeConfiguracion = floresEnBolsaDeConfiguracion);
    }

    public int getPapelesEnBolsaDeConfiguracion() {
        return this.papelesEnBolsaDeConfiguracion;
    }

    public void setPapelesEnBolsaDeConfiguracion(final int papelesEnBolsaDeConfiguracion) {
        this.setPapeles(this.papelesEnBolsaDeConfiguracion = papelesEnBolsaDeConfiguracion);
    }

    public void reset() {
        this.misCalles = new ArrayList<Coord>();
        this.ruta = new ArrayList<Coord>();
        this.rutas = new ArrayList<ArrayList<Coord>>();
        this.areas = new ArrayList<Area>();
        this.rutas.add(this.ruta);
        this.setFlores(this.getFloresEnBolsaDeConfiguracion());
        this.setPapeles(this.getPapelesEnBolsaDeConfiguracion());
        try {
            this.setAv(0);
            this.setCa(0);
        } catch (Exception ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setDireccion(90);
    }

    public Image getImage() {
        switch (this.getDireccion()) {
            case 0: {
                this.robotImage = new ImageIcon(this.getClass().getResource("/images/robotDerecha.png"));
                break;
            }
            case 90: {
                this.robotImage = new ImageIcon(this.getClass().getResource("/images/robotArriba.png"));
                break;
            }
            case 180: {
                this.robotImage = new ImageIcon(this.getClass().getResource("/images/robotIzquierda.png"));
                break;
            }
            default: {
                this.robotImage = new ImageIcon(this.getClass().getResource("/images/robotAbajo.png"));
                break;
            }
        }
        return this.robotImage.getImage();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public void iniciar(final int x, final int y) throws Exception {
        this.Pos(x, y);
        this.setNewX(x);
        this.setNewY(y);
        this.setFlores(this.getFlores());
        this.setPapeles(this.getPapeles());
        this.getCity().form.jsp.refresh();
    }

    public void choque(final String nom, final int id, final int av, final int ca) throws Exception {
        for (final Robot r : this.getCity().robots) {
            if (r.id != id && r.Av == av && r.Ca == ca) {
                this.city.parseError(" Se produjo un choque entre el robot " + nom + " y el robot " + r.getNombre()
                        + " en la avenida " + av + " y la calle " + ca);
                throw new Exception(" Se produjo un choque entre el robot " + nom + " y el robot " + r.getNombre()
                        + " en la avenida " + av + " y la calle " + ca);
            }
        }
    }

    public void mover() throws Exception {
        int av = this.PosAv();
        int ca = this.PosCa();
        switch (this.getDireccion()) {
            case 0: {
                ++av;
                break;
            }
            case 180: {
                --av;
                break;
            }
            case 90: {
                ++ca;
                break;
            }
            case 270: {
                --ca;
                break;
            }
        }
        if (!this.puedeMover(av, ca, this.areas)) {
            this.city.parseError(
                    "No se puede ejecutar la instrucci\u00f3n \"mover\" debido a que no corresponde a un area asignada del robot");
            throw new Exception(
                    "No se puede ejecutar la instrucci\u00f3n \"mover\" debido a que no corresponde a un area asignada del robot");
        }
        if (this.getCity().isFreePos(ca, av)) {
            this.addPos(av, ca);
            this.setFlores(this.getFlores());
            this.setPapeles(this.getPapeles());
            this.choque(this.nombre, this.id, this.Av, this.Ca);
            this.esperarRefresco.esperar(this.id);
            this.getCity().form.jsp.refresh();
            return;
        }
        this.city.parseError("No se puede ejecutar la instrucci\u00f3n \"mover\" debido a que hay un obst\u00e1culo");
        throw new Exception("No se puede ejecutar la instrucci\u00f3n \"mover\" debido a que hay un obst\u00e1culo");
    }

    public boolean puedeMover(final int av, final int ca, final ArrayList<Area> areas) {
        for (final Coord c : this.misCalles) {
            if (c.getX() == av && c.getY() == ca) {
                return true;
            }
        }
        return false;
    }

    public int[] getXCoord() {
        final int[] x = new int[this.ruta.size()];
        for (int c = 0; c < this.ruta.size(); ++c) {
            final Coord p = this.ruta.get(c);
            x[c] = p.getX();
        }
        return x;
    }

    public int[] getYCoord() {
        final int[] y = new int[this.ruta.size() + 1];
        for (int c = 0; c < this.ruta.size(); ++c) {
            final Coord p = this.ruta.get(c);
            y[c] = p.getY();
        }
        return y;
    }

    public void addPos(final int av, final int ca) throws Exception {
        try {
            final int old = this.city.ciudad[av][ca].getFlores();
            this.setAv(av);
            this.setCa(ca);
            this.pcs.firePropertyChange("esquinaFlores", old, this.city.ciudad[av][ca].getFlores());
        } catch (Exception e) {
            throw new Exception("Una de las nuevas coordenadas cae fuera de la ciudad.Av: " + av + " Ca: " + ca
                    + " Calles: " + this.city.getNumCa() + " Avenidas: " + this.city.getNumAv());
        }
    }

    public void setAv(final int av) throws Exception {
        if (av > this.city.getNumAv()) {
            throw new Exception();
        }
        if (av != this.PosAv()) {
            this.ruta.add(new Coord(av, this.PosCa()));
            if (av > this.PosAv()) {
                this.setDireccion(0);
            } else {
                this.setDireccion(180);
            }
        }
        this.setNewX(av);
        this.setNewY(this.Ca);
    }

    public void setNewX(final int av) {
        final int old = this.PosAv();
        this.Av = av;
        this.pcs.firePropertyChange("av", old, av);
    }

    public void setNewY(final int ca) {
        final int old = this.PosCa();
        this.Ca = ca;
        this.pcs.firePropertyChange("ca", old, ca);
    }

    public void setCa(final int ca) throws Exception {
        if (ca > this.city.getNumCa()) {
            throw new Exception();
        }
        if (ca != this.PosCa()) {
            this.ruta.add(new Coord(this.PosAv(), ca));
            if (ca < this.PosCa()) {
                this.setDireccion(270);
            } else {
                this.setDireccion(90);
            }
        }
        this.setNewY(ca);
        this.setNewX(this.Av);
    }

    public void setDireccion(final int direccion) {
        final int old = this.direccion;
        this.direccion = direccion;
        this.pcs.firePropertyChange("direccion", old, direccion);
    }

    public void setEstado(final String str) {
        final String s = this.getEstado();
        this.estado = str;
        this.pcs.firePropertyChange("estado", s, str);
    }

    public String getEstado() {
        return this.estado;
    }

    public int getDireccion() {
        return this.direccion;
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public void mirarEnDireccion(final int direccion) throws Exception {
        int c;
        for (c = 0; c < 5 && this.getDireccion() != direccion; ++c) {
            this.derecha();
        }
        if (c == 5) {
            throw new Exception("La direcci\u00f3n especificada no corresponde.");
        }
    }

    public void derecha() {
        switch (this.getDireccion()) {
            case 0: {
                this.setDireccion(270);
                break;
            }
            case 270: {
                this.setDireccion(180);
                break;
            }
            case 180: {
                this.setDireccion(90);
                break;
            }
            case 90: {
                this.setDireccion(0);
                break;
            }
        }
        this.esperarRefresco.esperar(this.id);
        this.getCity().form.jsp.refresh();
    }

    public int PosCa() {
        return this.Ca;
    }

    public int PosAv() {
        return this.Av;
    }

    public void Pos(final int Av, final int Ca) throws Exception {
        if (!this.puedeMover(Av, Ca, this.areas)) {
            this.city.parseError(
                    "No se puede ejecutar la instrucci\u00f3n \"Pos\" debido a que no corresponde a un area asignada del robot");
            throw new Exception(
                    "No se puede ejecutar la instrucci\u00f3n \"Pos\" debido a que no corresponde a un area asignada del robot");
        }
        if (this.getCity().isFreePos(Ca, Av)) {
            this.getRutas().add(this.getRuta());
            this.setRuta(new ArrayList<Coord>());
            this.getRuta().add(new Coord(Av, Ca));
            this.setNewX(Av);
            this.setNewY(Ca);
            this.choque(this.nombre, this.id, Av, Ca);
            this.esperarRefresco.esperar(this.id);
            this.getCity().form.jsp.refresh();
            return;
        }
        this.city.parseError("No se puede ejecutar la instrucci\u00f3n \"Pos\" debido a que hay un obst\u00e1culo");
        throw new Exception("No se puede ejecutar la instrucci\u00f3n \"Pos\" debido a que hay un obst\u00e1culo");
    }

    public ArrayList<Coord> getRuta() {
        return this.ruta;
    }

    public Ciudad getCity() {
        return this.city;
    }

    public void tomarFlor() throws Exception {
        if (this.getCity().levantarFlor(this.PosAv(), this.PosCa())) {
            this.setFlores(this.getFlores() + 1);
            this.esperarRefresco.esperar(this.id);
            return;
        }
        this.setFlores(this.getFlores());
        throw new Exception(
                "No se puede ejecutar la instrucci\u00f3n \"tomarFlor\" debido a que no hay ninguna flor en la esquina");
    }

    public int getFlores() {
        return this.floresEnBolsa;
    }

    public void setFlores(final int flores) {
        final int old = this.getFlores();
        this.floresEnBolsa = flores;
        this.pcs.firePropertyChange("flores", old, flores);
    }

    public void tomarPapel() throws Exception {
        if (this.getCity().levantarPapel(this.PosAv(), this.PosCa())) {
            this.setPapeles(this.getPapeles() + 1);
            this.esperarRefresco.esperar(this.id);
            return;
        }
        this.setPapeles(this.getPapeles());
        throw new Exception(
                "No se puede ejecutar la instrucci\u00f3n \"tomarPapel\" debido a que no hay ningun papel en la esquina");
    }

    public boolean HayPapelEnLaBolsa() {
        return this.getPapeles() > 0;
    }

    public boolean HayFlorEnLaBolsa() {
        return this.getFlores() > 0;
    }

    public int getPapeles() {
        return this.papelesEnBolsa;
    }

    public void setColor(final Color col) {
        final Color old = this.color;
        this.color = col;
        this.pcs.firePropertyChange("color", old, col);
    }

    public Color getColor() {
        return this.color;
    }

    public void setPapeles(final int papeles) {
        final int old = this.getPapeles();
        this.papelesEnBolsa = papeles;
        this.pcs.firePropertyChange("papeles", old, papeles);
    }

    public void depositarPapel() throws Exception {
        if (this.getPapeles() > 0) {
            this.setPapeles(this.getPapeles() - 1);
            this.getCity().dejarPapel(this.PosAv(), this.PosCa());
            this.esperarRefresco.esperar(this.id);
            return;
        }
        this.city.parseError(
                "No se puede ejecutar la instrucci\u00f3n \"depositarPapel\" debido a que no hay ningun papel en la bolsa");
        throw new Exception(
                "No se puede ejecutar la instrucci\u00f3n \"depositarPapel\" debido a que no hay ningun papel en la bolsa");
    }

    public void depositarFlor() throws Exception {
        if (this.getFlores() > 0) {
            this.setFlores(this.getFlores() - 1);
            this.getCity().dejarFlor(this.PosAv(), this.PosCa());
            this.esperarRefresco.esperar(this.id);
            return;
        }
        this.city.parseError(
                "No se puede ejecutar la instrucci\u00f3n \"depositarFlor\" debido a que no hay ninguna en la bolsa de flores");
        throw new Exception(
                "No se puede ejecutar la instrucci\u00f3n \"depositarFlor\" debido a que no hay ninguna en la bolsa de flores");
    }

    public ArrayList<ArrayList<Coord>> getRutas() {
        return this.rutas;
    }

    public void setRuta(final ArrayList<Coord> ruta) {
        this.ruta = ruta;
    }

    public void setRutas(final ArrayList<ArrayList<Coord>> rutas) {
        this.rutas = rutas;
    }

    public DeclaracionProcesos getProcAST() {
        return this.procAST;
    }

    public void setProcAST(final DeclaracionProcesos procAST) throws CloneNotSupportedException {
        synchronized (this) {
            final ArrayList<Proceso> ps = new ArrayList<Proceso>();
            for (final Proceso j : procAST.getProcesos()) {
                final DeclaracionVariable ddvv = j.getDV();
                final Identificador I = new Identificador(j.getI().toString());
                final ArrayList<ParametroFormal> pfs = new ArrayList<ParametroFormal>();
                for (final ParametroFormal pformal : j.getPF()) {
                    final Identificador In = new Identificador(pformal.getI().toString());
                    final ParametroFormal pf = new ParametroFormal(In, pformal.getT(), pformal.getTA());
                    pfs.add(pf);
                }
                final ArrayList<Sentencia> ss = new ArrayList<Sentencia>();
                for (final Sentencia sen : j.getC().getS()) {
                    System.out.println("Sentencia : " + sen.toString());
                    final Sentencia s = (Sentencia) sen.clone();
                    ss.add(s);
                }
                final ArrayList<Variable> dvs = new ArrayList<Variable>();
                for (final Variable v : ddvv.variables) {
                    final Variable V = (Variable) v.clone();
                    dvs.add(V);
                }
                final DeclaracionVariable ddvs = new DeclaracionVariable(dvs);
                final Cuerpo cue = new Cuerpo(ss, ddvs);
                final Proceso p = new Proceso(I, pfs, procAST, ddvs, cue);
                ps.add(p);
            }
            final DeclaracionProcesos dp = new DeclaracionProcesos(ps);
            this.procAST = dp;
        }
    }

    public DeclaracionVariable getVarAST() {
        return this.varAST;
    }

    public void setVarAST(final DeclaracionVariable varAST) {
        this.varAST = varAST;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    static {
        Robot.cant = 0;
    }
}
