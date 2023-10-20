
package arbol.expresion;

import arbol.Programa;
import arbol.DeclaracionVariable;
import arbol.expresion.operador.Operador;

public class ExpresionBinaria extends Expresion {
    Expresion E1;
    Expresion E2;
    Operador O;

    public ExpresionBinaria(final Operador O, final Expresion E1, final Expresion E2) {
        this.E1 = E1;
        this.E2 = E2;
        this.O = O;
    }

    @Override
    public String getValue(final DeclaracionVariable DV) throws Exception {
        synchronized (this) {
            this.E1.setRobot(this.getRobot());
            this.E2.setRobot(this.getRobot());
            final String E1Value = this.E1.getValue(DV);
            final String E2Value = this.E2.getValue(DV);
            final String res = this.O.resultado(E1Value, E2Value);
            return res;
        }
    }

    @Override
    public void setPrograma(final Programa P) {
        this.programa = P;
        this.E1.setPrograma(P);
        this.E2.setPrograma(P);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        synchronized (this) {
            System.out.println("Entre al clone de expresion binaria");
            final Expresion E3 = (Expresion) this.E1.clone();
            final Expresion E4 = (Expresion) this.E2.clone();
            return new ExpresionBinaria(this.O, E3, E4);
        }
    }
}
