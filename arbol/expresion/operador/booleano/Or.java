
package arbol.expresion.operador.booleano;

import arbol.expresion.operador.Operador;

public class Or extends Operador {
    boolean c;

    @Override
    public String resultado(final String Op1, final String Op2) {
        final boolean a = "V".equals(Op1);
        final boolean b = "V".equals(Op2);
        this.c = (a | b);
        return this.c ? "V" : "F";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Or();
    }
}
