
package arbol.sentencia;

import arbol.DeclaracionVariable;
import java.util.ArrayList;
import arbol.expresion.Expresion;

public class Seleccion extends Sentencia {
    Expresion E;
    ArrayList<Sentencia> S1;
    ArrayList<Sentencia> S2;

    public Seleccion(final Expresion E, final ArrayList<Sentencia> S1, final ArrayList<Sentencia> S2,
            final DeclaracionVariable DV) {
        this.E = E;
        this.S1 = S1;
        this.S2 = S2;
        this.setDV(DV);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final ArrayList<Sentencia> S = new ArrayList<Sentencia>();
        for (final Sentencia sen : this.getS1()) {
            System.out.println("Sentencia  clonada : " + sen.toString());
            final Sentencia ss = (Sentencia) sen.clone();
            S.add(ss);
        }
        final ArrayList<Sentencia> SS2 = new ArrayList<Sentencia>();
        if (this.getS2() != null) {
            for (final Sentencia sen2 : this.getS2()) {
                System.out.println("Sentencia  clonada : " + sen2.toString());
                final Sentencia sss = (Sentencia) sen2.clone();
                SS2.add(sss);
            }
        }
        final Expresion EE = (Expresion) this.E.clone();
        return new Seleccion(EE, S, SS2, this.getDV());
    }

    public Expresion getE() {
        return this.E;
    }

    public void setE(final Expresion E) {
        this.E = E;
    }

    public ArrayList<Sentencia> getS1() {
        return this.S1;
    }

    public void setS1(final ArrayList<Sentencia> S1) {
        this.S1 = S1;
    }

    public ArrayList<Sentencia> getS2() {
        return this.S2;
    }

    public void setS2(final ArrayList<Sentencia> S2) {
        this.S2 = S2;
    }

    @Override
    public void ejecutar() throws Exception {
        synchronized (this) {
            this.E.setPrograma(this.getPrograma());
            this.E.setRobot(this.getRobot());
            this.E.setDV(this.getDV());
            if (this.E.getValue(this.getDV()).equals("V")) {
                for (final Sentencia single : this.S1) {
                    single.setPrograma(this.getPrograma());
                    single.setRobot(this.getRobot());
                    single.setDV(this.getDV());
                    single.ejecutar();
                }
            } else if (this.S2 != null) {
                for (final Sentencia single : this.S2) {
                    single.setPrograma(this.getPrograma());
                    single.setRobot(this.getRobot());
                    single.setDV(this.getDV());
                    single.ejecutar();
                }
            }
        }
    }
}
