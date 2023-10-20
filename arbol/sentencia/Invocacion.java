
package arbol.sentencia;

import arbol.Programa;
import form.Robot;
import arbol.DeclaracionVariable;
import arbol.expresion.Expresion;
import java.util.ArrayList;
import arbol.llamada.Llamada;

public class Invocacion extends Sentencia {
    Llamada L;
    ArrayList<Expresion> E;

    public Invocacion(final Llamada L, final ArrayList<Expresion> E, final DeclaracionVariable DV) {
        this.L = null;
        this.E = null;
        this.L = L;
        this.E = E;
        this.varAST = DV;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        synchronized (this) {
            final ArrayList<Expresion> es = new ArrayList<Expresion>();
            for (final Expresion ee : this.E) {
                final Expresion e = (Expresion) ee.clone();
                e.setDV(this.varAST);
                es.add(e);
            }
            Llamada ll = null;
            try {
                ll = this.L.nuevo();
            } catch (Exception ex) {
                System.out.println("Algo anda mal en el clone de invocacion");
            }
            return new Invocacion(ll, es, this.getDV());
        }
    }

    @Override
    public void setRobot(final Robot r) {
        for (final Expresion ee : this.E) {
            ee.setRobot(r);
        }
        this.L.setRobot(r);
    }

    @Override
    public void ejecutar() throws Exception {
        synchronized (this) {
            for (final Expresion ee : this.E) {
                ee.setRobot(this.getRobot());
            }
            this.L.setDV(this.getDV());
            this.L.ejecutar(this.E);
        }
    }

    @Override
    public void setPrograma(final Programa P) {
        this.L.setPrograma(P);
        for (final Expresion exp : this.E) {
            exp.setPrograma(P);
        }
    }
}
