
package arbol.expresion.operador.relacional;

import arbol.expresion.operador.Operador;

public class MenorIgual extends Operador {
    @Override
    public String resultado(final String Op1, final String Op2) {
        int Op1x = Integer.parseInt(Op1);
        int Op2x = Integer.parseInt(Op2);
        return (Op1x <= Op2x) ? "V" : "F";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new MenorIgual();
    }
}
