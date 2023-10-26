
package arbol.expresion.operador.booleano;

import arbol.expresion.operador.Operador;

public class Or extends Operador {
    @Override
    public String resultado(final String Op1, final String Op2) {
        return ("V".equals(Op1) || "V".equals(Op2)) ? "V" : "F";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Or();
    }
}
