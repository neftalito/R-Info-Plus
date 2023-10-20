
package arbol.sentencia.primitiva;

import arbol.Identificador;
import arbol.sentencia.Sentencia;

public abstract class Primitiva extends Sentencia {
    Identificador I;

    public Identificador getI() {
        return this.I;
    }

    public void setI(final Identificador I) {
        this.I = I;
    }
}
