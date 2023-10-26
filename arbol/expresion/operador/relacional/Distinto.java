
package arbol.expresion.operador.relacional;

import arbol.expresion.operador.Operador;

public class Distinto extends Operador {
    @Override
    public String resultado(final String Op1, final String Op2) {
        return (Op1.equals(Op2)) ? "F" : "V";
        /*
         * Podría ser tambien
         * return (!Op1.equals(Op2)) ? "V" : "F";
         */
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        System.out.println("Entre al clone de distinto");
        return new Distinto();
    }
}
