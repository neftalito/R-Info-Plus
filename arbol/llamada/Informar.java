
package arbol.llamada;

import arbol.expresion.Expresion;
import java.util.ArrayList;
import arbol.DeclaracionVariable;

public class Informar extends Llamada {
    int ciclo;
    DeclaracionVariable DV;
    String msg;
    String str;

    public int getCiclo() {
        return this.ciclo;
    }

    public Informar(final DeclaracionVariable DV, final String s) {
        this.ciclo = 1;
        this.msg = "";
        this.str = "";
        this.DV = DV;
        this.str = s;
    }

    @Override
    public DeclaracionVariable getDV() {
        return this.DV;
    }

    @Override
    public void setDV(final DeclaracionVariable DV) {
        this.DV = DV;
    }

    @Override
    public void ejecutar(final ArrayList<Expresion> E) throws Exception {
        synchronized (this) {
            this.msg = this.getRobot().getNombre() + " informa: ";
            this.msg = this.msg + " " + this.str;
            for (final Expresion exp : E) {
                exp.setRobot(this.getRobot());
                this.msg = this.msg + " " + exp.getValue(this.DV) + " ";
            }
            this.getRobot().Informar(this.msg);
            this.msg = "";
        }
    }

    @Override
    public Llamada nuevo() throws Exception {
        return new Informar(this.DV, this.str);
    }
}
