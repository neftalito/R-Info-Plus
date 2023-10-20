
package form;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorActualizarVentana {
    private CityScroll jsp;
    private static MonitorActualizarVentana instance;
    private final ReentrantLock lock;
    final Condition condicion;
    final Condition pause;
    private int cant_robots;
    private boolean en_ejecucion;
    private boolean[] resultado;
    Condition[] espera;
    private boolean apretoF7;
    final Condition barrera;
    private boolean pasoAPaso;
    private int bar;
    private int cant_ejecutandose;
    private boolean timerOn;
    private int dormidos;
    private int time;
    private boolean sistemaPausado;

    public int getBar() {
        return this.bar;
    }

    public void setBar(final int bar) {
        this.bar = bar;
    }

    public int getDormidos() {
        return this.dormidos;
    }

    public void setDormidos(final int dormidos) {
        this.dormidos = dormidos;
    }

    public boolean isTimerOn() {
        return this.timerOn;
    }

    public void setTimerOn(final boolean timerOn) {
        this.timerOn = timerOn;
    }

    public boolean isSistemaPausado() {
        return this.sistemaPausado;
    }

    public void setSistemaPausado(final boolean sistemaPausado) {
        this.sistemaPausado = sistemaPausado;
    }

    public void setSpeed(final int t) {
        this.time = t;
    }

    private MonitorActualizarVentana(final int cant) {
        this.lock = new ReentrantLock();
        this.condicion = this.lock.newCondition();
        this.pause = this.lock.newCondition();
        this.cant_robots = 0;
        this.en_ejecucion = false;
        this.apretoF7 = false;
        this.barrera = this.lock.newCondition();
        this.pasoAPaso = false;
        this.bar = 0;
        this.cant_ejecutandose = 0;
        this.timerOn = false;
        this.dormidos = 0;
        this.time = 165;
        this.sistemaPausado = false;
        this.setCant_robots(cant);
        this.setCant_ejecutandose(cant);
        this.setDormidos(0);
        this.espera = new Condition[cant];
        this.resultado = new boolean[cant];
        for (int i = 0; i < cant; ++i) {
            this.espera[i] = this.lock.newCondition();
            this.resultado[i] = false;
        }
    }

    public void setCityScroll(final CityScroll jsp) {
        this.jsp = jsp;
    }

    public void setCantidad(final int cant) {
        this.setCant_robots(cant);
        this.setCant_ejecutandose(cant);
        this.setDormidos(0);
        this.espera = new Condition[cant];
        this.resultado = new boolean[cant];
        for (int i = 0; i < cant; ++i) {
            this.espera[i] = this.lock.newCondition();
            this.resultado[i] = false;
        }
    }

    public void startTimer() {
        this.setTimerOn(true);
        this.waitAgain();
    }

    private void waitAgain() {
        try {
            Thread.sleep(this.time);
        } catch (InterruptedException ex) {
        }
        if (!this.sistemaPausado) {
            this.despertar();
        }
        if (this.isTimerOn()) {
            this.waitAgain();
        }
    }

    public int getCant_ejecutandose() {
        return this.cant_ejecutandose;
    }

    public void setCant_ejecutandose(final int cant_ejecutandose) {
        this.cant_ejecutandose = cant_ejecutandose;
    }

    public boolean isEn_ejecucion() {
        return this.en_ejecucion;
    }

    public void setEn_ejecucion(final boolean en_ejecucion) {
        this.en_ejecucion = en_ejecucion;
    }

    public boolean isPasoAPaso() {
        return this.pasoAPaso;
    }

    public void setPasoAPaso(final boolean pasoAPaso) {
        this.pasoAPaso = pasoAPaso;
    }

    public boolean isApretoF7() {
        return this.apretoF7;
    }

    public void setApretoF7(final boolean apretoF7) {
        this.apretoF7 = apretoF7;
    }

    public int getCant_robots() {
        return this.cant_robots;
    }

    public void setCant_robots(final int cant_robots) {
        this.cant_robots = cant_robots;
    }

    public static MonitorActualizarVentana getInstance() {
        return MonitorActualizarVentana.instance;
    }

    public static MonitorActualizarVentana crearMonitorActualizarVentana(final int cantidadClientes) {
        return MonitorActualizarVentana.instance = new MonitorActualizarVentana(cantidadClientes);
    }

    public void esperaMensaje() {
        this.lock.lock();
        try {
            if (this.getCant_ejecutandose() - this.getBar() == 1) {
                this.barrera.signalAll();
            }
            this.setCant_ejecutandose(this.getCant_ejecutandose() - 1);
            this.setDormidos(this.getDormidos() + 1);
        } finally {
            this.lock.unlock();
        }
    }

    public void esperaEsquina() {
        this.lock.lock();
        try {
            if (this.cant_ejecutandose - this.bar == 1) {
                this.barrera.signalAll();
            }
            --this.cant_ejecutandose;
        } finally {
            this.lock.unlock();
        }
    }

    public void esperar(final int id) {
        this.lock.lock();
        try {
            while (!this.apretoF7) {
                this.espera[id].await();
            }
            this.setBar(this.getBar() + 1);
            if (this.getBar() < this.getCant_ejecutandose()) {
                this.barrera.await();
            } else {
                this.barrera.signalAll();
                this.setApretoF7(false);
                this.setBar(0);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exc ");
            throw new RuntimeException();
        } finally {
            this.lock.unlock();
        }
    }

    public void pintarRuta(final Robot r) throws Exception {
        this.lock.lock();
        try {
            this.jsp.jc.dibujarRuta(r);
            this.jsp.city.form.compedos.iv.vent.dibujarRuta(r);
        } finally {
            this.lock.unlock();
        }
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
            this.setApretoF7(true);
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

    public boolean termine_ejecucion() {
        this.lock.lock();
        try {
            System.out.println("Termine ejecucion");
            boolean resultado = false;
            final int canti = this.getCant_ejecutandose();
            this.setCant_ejecutandose(this.getCant_ejecutandose() - 1);
            resultado = (this.getCant_ejecutandose() == 0 && this.getDormidos() == 0);
            System.out.println("ejecutandose 0 y dormidos 0,el resultado es : " + resultado);
            if (this.getBar() == this.getCant_ejecutandose()) {
                System.out.println("termine y desperte a todos ");
                this.barrera.signalAll();
                this.setBar(0);
            }
            return resultado;
        } finally {
            this.lock.unlock();
        }
    }

    public void pausar_ejecucion() {
        this.lock.lock();
        try {
            this.sistemaPausado = true;
        } finally {
            this.lock.unlock();
        }
    }
}
