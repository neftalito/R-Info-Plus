
package arbol.sentencia;

import form.Robot;
import arbol.Programa;
import arbol.DeclaracionVariable;
import java.util.ArrayList;
import arbol.expresion.Expresion;

public class IteradorCondicional extends Sentencia {
    Expresion E;
    ArrayList<Sentencia> S;

    public IteradorCondicional(final Expresion E, final ArrayList<Sentencia> S, final DeclaracionVariable DV) {
        this.E = E;
        this.S = S;
        this.setDV(DV);
    }

    @Override
    public void ejecutar() throws Exception {
        this.E.setPrograma(this.getPrograma());
        this.E.setRobot(this.getRobot());
        while (this.E.getValue(this.getDV()).equals("V")) {
            for (final Sentencia single : this.S) {
                single.setRobot(this.getRobot());
                single.ejecutar();
            }
        }
    }

    @Override
    public void setPrograma(final Programa P) {
        this.programa = P;
        this.E.setPrograma(P);
        for (final Sentencia sen : this.S) {
            sen.setPrograma(P);
        }
    }

    @Override
    public void setRobot(final Robot r) {
        super.setRobot(r);
        this.E.setRobot(r);
        for (final Sentencia s : this.S) {
            s.setRobot(r);
        }
    }

    @Override
    public void setDV(final DeclaracionVariable DV) {
        this.varAST = DV;
        for (final Sentencia s : this.S) {
            s.setDV(this.varAST);
        }
        this.E.setDV(DV);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        synchronized (this) {
            final ArrayList<Sentencia> ss = new ArrayList<Sentencia>();
            for (final Sentencia single : this.S) {
                System.out.println("Intentando clonar en Iterador Condicional: " + single.toString());
                final Sentencia sen = (Sentencia) single.clone();
                ss.add(sen);
            }
            System.out.println("Intentando clonar la expresion en iterador condicional");
            final Expresion e = (Expresion) this.E.clone();
            return new IteradorCondicional(e, ss, this.getDV());
        }
    }

    public Expresion getE() {
        return this.E;
    }

    public void setE(final Expresion E) {
        this.E = E;
    }

    public ArrayList<Sentencia> getS() {
        return this.S;
    }

    public void setS(final ArrayList<Sentencia> S) {
        this.S = S;
    }
}
