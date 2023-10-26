
package arbol.sentencia;

import arbol.Programa;
import form.Robot;
import arbol.DeclaracionVariable;
import java.util.ArrayList;
import arbol.expresion.Expresion;

public class IteradorIncondicional extends Sentencia {
    Expresion E;
    ArrayList<Sentencia> S;

    public IteradorIncondicional(final Expresion E, final ArrayList<Sentencia> S, final DeclaracionVariable DV) {
        this.E = E;
        this.S = S;
        this.setDV(DV);
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
    public void ejecutar() throws Exception {
        this.E.setRobot(this.getRobot());

        // Antes hacía esto por cada vez, provocando que el repetir(cantFloresBolsa) o
        // repetir(cantFloresPapeles) y depositando, cambie el valor total y termine
        // antes de finalizar la ejecución
        int veces = Integer.parseInt(this.E.getValue(this.getDV()));

        for (int c = 0; c < veces; c++) {
            for (final Sentencia single : this.S) {
                single.setRobot(this.getRobot());
                single.ejecutar();
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        synchronized (this) {
            final ArrayList<Sentencia> ss = new ArrayList<Sentencia>();
            for (final Sentencia single : this.S) {
                System.out.println("Intentando clonar en Iterador Incondicional: " + single.toString());
                final Sentencia sen = (Sentencia) single.clone();
                ss.add(sen);
            }
            return new IteradorIncondicional(this.E, ss, this.getDV());
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
    public void setPrograma(final Programa P) {
        this.E.setPrograma(P);
        for (final Sentencia sen : this.S) {
            sen.setPrograma(P);
        }
    }
}
