
package arbol.expresion.operador.relacional;

import arbol.expresion.operador.Operador;

public class Igual extends Operador {
    String c;

    @Override
    public String resultado(final String Op1, final String Op2) {
        if (Op1.equals(Op2)) {
            this.c = "V";
        } else {
            this.c = "F";
        }
        return this.c;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Igual();
    }
}
