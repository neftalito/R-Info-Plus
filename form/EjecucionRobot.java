
package form;

import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class EjecucionRobot implements Runnable, PropertyChangeListener {
    Robot prgAST;
    MonitorActualizarVentana esperarRefresco;
    boolean pasoAPaso;
    CodePanel codigo;
    private final PropertyChangeSupport pcs;

    public EjecucionRobot(final Robot prg, final boolean paso_a_paso, final CodePanel code) {
        this.esperarRefresco = MonitorActualizarVentana.getInstance();
        this.pasoAPaso = false;
        this.pcs = new PropertyChangeSupport(this);
        this.pasoAPaso = paso_a_paso;
        this.prgAST = prg;
        this.codigo = code;
    }

    @Override
    public void run() {
        try {
            System.out.println("ejecutando robot: " + this.prgAST.getNombre());
            this.prgAST.setEstado("ejecutandose");
            this.prgAST.getCuerpo().ejecutar();
            if (this.esperarRefresco.termine_ejecucion()) {
                this.esperarRefresco.setEn_ejecucion(false);
                this.esperarRefresco.setApretoF7(false);
                this.esperarRefresco.setTimerOn(false);
                this.codigo.habilitarTodo();
                this.prgAST.getCity().Informar("Programa finalizado", 0);
            } else {
                System.out.println("No soy el ultimo, pero termine :" + this.prgAST.getNombre());
            }
            this.prgAST.setEstado("finalizado");
            System.out.println("nombre de robot : " + this.prgAST.getNombre() + " termino ");
        } catch (Exception e) {
            System.out.println(e);
            this.prgAST.setEstado("error");
            try {
                this.codigo.doStopError();
                this.prgAST.setEstado("error");
            } catch (Exception ex) {
                this.prgAST.setEstado("error");
                System.out.println(ex);
                Logger.getLogger(Ejecucion.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.codigo.habilitarTodo();
        }
    }

    public void dormir() {
        this.esperarRefresco.dormir();
    }

    public void arrancar() {
        this.esperarRefresco.arrancar();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        System.out.println("propertyChange event");
    }
}
