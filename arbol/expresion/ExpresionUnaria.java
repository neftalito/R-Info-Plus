
package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.expresion.operador.Operador;

public class ExpresionUnaria extends Expresion {
    Operador O;
    Expresion E;

    public ExpresionUnaria(final Operador O, final Expresion E) {
        this.O = O;
        (this.E = E).setPrograma(this.getPrograma());
    }

    @Override
    public String getValue(final DeclaracionVariable DV) throws Exception {
        this.O.setRobot(this.getRobot());
        this.E.setRobot(this.getRobot());
        return this.O.resultado(this.E.getValue(DV));
    }

    public Operador getO() {
        return this.O;
    }

    public void setO(final Operador O) {
        this.O = O;
    }

    public Expresion getE() {
        return this.E;
    }

    public void setE(final Expresion E) {
        this.E = E;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        synchronized (this) {
            System.out.println("Entre al clone de expresion unaria");
            final Expresion E3 = (Expresion) this.getE().clone();
            return new ExpresionUnaria(this.getO(), E3);
        }
    }
}
