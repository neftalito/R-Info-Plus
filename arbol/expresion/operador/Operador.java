
package arbol.expresion.operador;

import arbol.expresion.Expresion;

public abstract class Operador extends Expresion {
    public String resultado(final String Op1) throws Exception {
        return "undefined";
    }

    public String resultado(final String Op1, final String Op2) {
        return "undefined";
    }
}
