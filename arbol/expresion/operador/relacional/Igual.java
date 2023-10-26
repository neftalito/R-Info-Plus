
package arbol.expresion.operador.relacional;

import arbol.expresion.operador.Operador;

public class Igual extends Operador {
    @Override
    public String resultado(final String Op1, final String Op2) {
        return (Op1.equals(Op2)) ? "V" : "F";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Igual();
    }
}
