
package arbol;

import java.util.ArrayList;

public class DeclaracionProcesos extends AST {
    public ArrayList<Proceso> procesos;

    public ArrayList<Proceso> getProcesos() {
        return this.procesos;
    }

    public void setProcesos(final ArrayList<Proceso> procesos) {
        this.procesos = procesos;
    }

    public DeclaracionProcesos(final ArrayList<Proceso> procesos) {
        this.procesos = procesos;
    }

    public boolean estaProceso(final String spelling) {
        for (int i = 0; i < this.procesos.size(); ++i) {
            if (this.procesos.get(i).I.spelling.equals(spelling)) {
                return true;
            }
        }
        return false;
    }

    public Proceso getProceso(final String spelling) {
        final Proceso proc = null;
        for (int i = 0; i < this.procesos.size(); ++i) {
            if (this.procesos.get(i).I.spelling.equals(spelling)) {
                return this.procesos.get(i);
            }
        }
        return proc;
    }
}
