
package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.Tipo;

public class ValorBooleano extends Expresion {
    String spelling;

    public ValorBooleano(final String spelling) {
        this.spelling = spelling;
        this.setT(new Tipo((byte) 20));
    }

    @Override
    public String getValue(final DeclaracionVariable DV) {
        return this.spelling;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new ValorBooleano(this.spelling);
    }
}
