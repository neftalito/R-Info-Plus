
package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.Tipo;

public class ValorNumerico extends Expresion {
    String spelling;

    public ValorNumerico(final String spelling) {
        this.spelling = spelling;
        this.setT(new Tipo((byte) 19));
    }

    @Override
    public String getValue(final DeclaracionVariable DV) {
        return this.spelling;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new ValorNumerico(this.spelling);
    }
}
