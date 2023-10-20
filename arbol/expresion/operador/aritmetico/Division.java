
package arbol.expresion.operador.aritmetico;

import arbol.expresion.operador.Operador;

public class Division extends Operador {
    int c;

    @Override
    public String resultado(final String Op1, final String Op2) {
        final int a = Integer.parseInt(Op1);
        final int b = Integer.parseInt(Op2);
        this.c = a / b;
        return String.valueOf(this.c);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Division();
    }
}
