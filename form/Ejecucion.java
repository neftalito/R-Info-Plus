
package form;

import java.util.logging.Level;
import java.util.logging.Logger;
import arbol.Programa;

public class Ejecucion implements Runnable {
    Programa prgAST;
    MonitorActualizarVentana esperarRefresco;
    boolean pasoAPaso;
    CodePanel codigo;

    public Ejecucion(final Programa prg, final boolean paso_a_paso, final CodePanel code) {
        this.prgAST = null;
        this.esperarRefresco = MonitorActualizarVentana.getInstance();
        this.pasoAPaso = false;
        this.pasoAPaso = paso_a_paso;
        this.prgAST = prg;
        this.esperarRefresco.setApretoF7(!paso_a_paso);
        this.esperarRefresco.setPasoAPaso(paso_a_paso);
        this.codigo = code;
    }

    @Override
    public void run() {
        try {
            this.esperarRefresco.setEn_ejecucion(true);
            this.prgAST.ejecutar();
        } catch (Exception e) {
            System.out.println(e);
            this.prgAST.getCity().parseError(e.toString());
            try {
                this.codigo.doStopError();
                System.out.println("doStopError");
            } catch (Exception ex) {
                Logger.getLogger(Ejecucion.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.esperarRefresco.setEn_ejecucion(false);
            this.codigo.habilitarTodo();
        }
    }

    public void dormir() {
        this.esperarRefresco.dormir();
    }

    public void arrancar() {
        this.esperarRefresco.arrancar();
    }
}
