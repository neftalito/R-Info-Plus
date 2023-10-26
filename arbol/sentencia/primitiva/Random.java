
package arbol.sentencia.primitiva;

import java.util.logging.Level;
import java.util.logging.Logger;
import arbol.DeclaracionVariable;
import arbol.Identificador;
import arbol.expresion.Expresion;

public class Random extends Primitiva {
    Expresion E1;
    Expresion E2;

    public Random(final Identificador I, final DeclaracionVariable DV, final Expresion E1, final Expresion E2)
            throws Exception {
        this.setDV(DV);
        this.setI(I);
        this.setE1(E1);
        this.setE2(E2);
        final boolean ok = this.getDV().EstaParametro(I.toString());
        if (this.getDV().findByName(I.toString()).getT().tipo != 19) {
            throw new Exception("Se esperaba una variable de tipo n\u00famero en la primitiva random");
        }
    }

    @Override
    public void ejecutar() throws Exception {
        this.getE1().setRobot(this.getRobot());
        this.getE2().setRobot(this.getRobot());
        System.out.println("Entre 11");
        System.out.println(this.getE1().getValue(this.getDV()));
        final int desde = Integer.parseInt(this.getE1().getValue(this.getDV()));
        System.out.println("-1");
        final int hasta = Integer.parseInt(this.getE2().getValue(this.getDV()));
        System.out.println("-2");
        final int aux = (int) (Math.random() * (hasta - desde + 1) + desde);
        System.out.println("-3");
        final String str = String.valueOf(aux);
        System.out.println("-4");
        this.getDV().findByName(this.I.toString()).setValue(str);
        System.out.println("Salii11111");
    }

    public Expresion getE1() {
        return this.E1;
    }

    public void setE1(final Expresion E1) {
        this.E1 = E1;
    }

    public Expresion getE2() {
        return this.E2;
    }

    public void setE2(final Expresion E2) {
        this.E2 = E2;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            final Expresion E11 = (Expresion) this.getE1().clone();
            final Expresion E12 = (Expresion) this.getE2().clone();
            return new Random(this.getI(), this.getDV(), E11, E12);
        } catch (Exception ex) {
            Logger.getLogger(Random.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
