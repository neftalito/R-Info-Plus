
package form;

import arbol.Identificador;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

public class MonitorMensajes {
    public ArrayList<Dato> datos;
    private static MonitorMensajes instance;
    private final ReentrantLock lock;
    final Condition condicion;
    final Condition pause;
    private int cant_robots;
    private boolean en_ejecucion;
    private boolean[] resultado;
    final Condition[] espera;
    final Condition esperaCualquiera;
    private boolean apretoF7;
    final Condition barrera;
    private boolean pasoAPaso;
    private int bar;
    private int cant_ejecutandose;
    private boolean timerOn;
    private int time;
    private boolean sistemaPausado;
    public Robot r;

    private MonitorMensajes(final int cant, final Robot r) {
        this.lock = new ReentrantLock();
        this.condicion = this.lock.newCondition();
        this.pause = this.lock.newCondition();
        this.cant_robots = 0;
        this.en_ejecucion = false;
        this.esperaCualquiera = this.lock.newCondition();
        this.apretoF7 = false;
        this.barrera = this.lock.newCondition();
        this.pasoAPaso = false;
        this.bar = 0;
        this.cant_ejecutandose = 0;
        this.timerOn = false;
        this.time = 50;
        this.sistemaPausado = false;
        this.r = r;
        this.datos = new ArrayList<Dato>();
        this.espera = new Condition[cant];
        this.resultado = new boolean[cant];
        for (int i = 0; i < cant; ++i) {
            this.espera[i] = this.lock.newCondition();
            this.resultado[i] = false;
        }
    }

    private void waitAgain() {
        try {
            Thread.sleep(this.time);
        } catch (InterruptedException ex) {
        }
        if (!this.sistemaPausado) {
            this.despertar();
        }
        if (this.timerOn) {
            this.waitAgain();
        }
    }

    public static MonitorMensajes getInstance() {
        return MonitorMensajes.instance;
    }

    public static MonitorMensajes crearMonitorActualizarVentana(final int cantidadClientes, final Robot r) {
        return MonitorMensajes.instance = new MonitorMensajes(cantidadClientes, r);
    }

    public void recibirMensaje(final Identificador nombreVariable, final int id, final Identificador NombreRobot)
            throws Exception {
        this.lock.lock();
        try {
            if (this.estaNombreRobotEnDatos(NombreRobot.toString())) {
                final String valor = this.getValorByNombreRobot(NombreRobot.toString());
                this.getRobot().getVariables().findByName(nombreVariable.toString()).setValue(valor);
            } else if (NombreRobot.toString().equals("*")) {
                String str = this.getValorByComodin();
                if (str == null) {
                    this.getRobot().esperarRefresco.esperaMensaje();
                    this.esperaCualquiera.await();
                    str = this.getValorByComodin();
                    this.getRobot().getVariables().findByName(nombreVariable.toString()).setValue(str);
                    final int x = this.getRobot().esperarRefresco.getCant_ejecutandose();
                    this.getRobot().esperarRefresco.setCant_ejecutandose(x + 1);
                    this.getRobot().esperarRefresco.setDormidos(this.getRobot().esperarRefresco.getDormidos() - 1);
                } else {
                    this.getRobot().getVariables().findByName(nombreVariable.toString()).setValue(str);
                }
            } else {
                this.getRobot().esperarRefresco.esperaMensaje();
                this.espera[id].await();
                if (this.estaNombreRobotEnDatos(NombreRobot.toString())) {
                    final String valor = this.getValorByNombreRobot(NombreRobot.toString());
                    this.getRobot().getVariables().findByName(nombreVariable.toString()).setValue(valor);
                    final int x = this.getRobot().esperarRefresco.getCant_ejecutandose();
                    this.getRobot().esperarRefresco.setCant_ejecutandose(x + 1);
                    this.getRobot().esperarRefresco.setDormidos(this.getRobot().esperarRefresco.getDormidos() - 1);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exc ");
            throw new RuntimeException();
        } finally {
            this.lock.unlock();
        }
    }

    private String getValorByNombreRobot(final String nombre) {
        final int i = this.getIndexByNombreRobot(nombre);
        final String valor = this.datos.get(i).valor;
        this.datos.remove(i);
        return valor;
    }

    private String getValorByComodin() throws InterruptedException {
        String valor = null;
        final int i = 0;
        if (i < this.datos.size()) {
            valor = this.datos.get(i).valor;
            this.datos.remove(i);
        }
        return valor;
    }

    private int getIndexByNombreRobot(final String nombre) {
        int i = 0;
        for (final Dato d : this.datos) {
            if (d.NombreRobot.equals(nombre)) {
                return i;
            }
            ++i;
        }
        System.out.println("Algo anda mal en getIndexByNombreRobot");
        return i;
    }

    public void llegoMensaje(final int id, final Dato d) {
        this.lock.lock();
        try {
            this.datos.add(d);
            this.espera[id].signal();
            this.esperaCualquiera.signal();
        } finally {
            this.lock.unlock();
        }
    }

    public boolean estaNombreRobotEnDatos(final String nombre) {
        for (final Dato d : this.datos) {
            if (d.NombreRobot.equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    public Robot getRobot() {
        return this.r;
    }

    public void dormir() {
        this.lock.lock();
        try {
            this.pause.await();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exc ");
            throw new RuntimeException();
        } finally {
            this.lock.unlock();
        }
    }

    public void arrancar() {
        this.lock.lock();
        try {
            this.pause.signal();
        } finally {
            this.lock.unlock();
        }
    }

    public void despertar() {
        this.lock.lock();
        try {
            this.apretoF7 = true;
            for (int i = 0; i < this.cant_robots; ++i) {
                this.espera[i].signal();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void despertarPause() {
        this.lock.lock();
        try {
            this.sistemaPausado = false;
        } finally {
            this.lock.unlock();
        }
    }
}
