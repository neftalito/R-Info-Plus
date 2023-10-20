
package arbol.expresion.operador.relacional;

import arbol.expresion.operador.Operador;

public class Mayor extends Operador {
    String c;

    @Override
    public String resultado(final String Op1, final String Op2) {
        final int Op1x = Integer.parseInt(Op1);
        final int Op2x = Integer.parseInt(Op2);
        if (Op1x > Op2x) {
            this.c = "V";
        } else {
            this.c = "F";
        }
        return this.c;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Mayor();
    }
}
