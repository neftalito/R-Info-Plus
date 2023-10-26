
package arbol.expresion.operador.aritmetico;

import arbol.expresion.operador.Operador;

public class Suma extends Operador {
    @Override
    public String resultado(final String Op1, final String Op2) {
        int a = Integer.parseInt(Op1);
        int b = Integer.parseInt(Op2);
        return String.valueOf(a + b);
    }

    @Override
    public String resultado(final String Op1) {
        return Op1; // El resultado de una suma con un solo operando es el mismo operando
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Suma();
    }
}
