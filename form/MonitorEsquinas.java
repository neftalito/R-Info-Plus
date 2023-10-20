
package form;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorEsquinas {
    private static MonitorEsquinas instance;
    private final ReentrantLock lock;
    final Condition condicion;
    MonitorActualizarVentana esperarRefresco;
    private boolean[][] libre;
    Condition[][] espera;
    final Condition barrera;

    private MonitorEsquinas() {
        this.lock = new ReentrantLock();
        this.condicion = this.lock.newCondition();
        this.esperarRefresco = MonitorActualizarVentana.getInstance();
        this.barrera = this.lock.newCondition();
        this.espera = new Condition[101][101];
        this.libre = new boolean[101][101];
        for (int i = 1; i <= 100; ++i) {
            for (int j = 1; j <= 100; ++j) {
                this.espera[i][j] = this.lock.newCondition();
                this.libre[i][j] = true;
            }
        }
    }

    public static MonitorEsquinas getInstance() {
        return MonitorEsquinas.instance;
    }

    public static MonitorEsquinas crearMonitorEsquinas() {
        return MonitorEsquinas.instance = new MonitorEsquinas();
    }

    public void bloquear(final int Av, final int Ca) {
        this.lock.lock();
        try {
            System.out.println("Entre al bloquear");
            if (this.libre[Av][Ca]) {
                this.libre[Av][Ca] = false;
            } else {
                this.esperarRefresco.esperaEsquina();
                while (!this.libre[Av][Ca]) {
                    this.espera[Av][Ca].await();
                }
                this.libre[Av][Ca] = false;
                this.esperarRefresco.setCant_ejecutandose(this.esperarRefresco.getCant_ejecutandose() + 1);
            }
            System.out.println("Sali del bloquear");
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exc ");
            throw new RuntimeException();
        } finally {
            this.lock.unlock();
        }
    }

    public void liberar(final int Av, final int Ca) {
        this.lock.lock();
        try {
            this.libre[Av][Ca] = true;
            this.espera[Av][Ca].signalAll();
        } finally {
            this.lock.unlock();
        }
    }
}
