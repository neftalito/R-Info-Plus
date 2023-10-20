
package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.Tipo;

public class PosAv extends Expresion {
    public PosAv() {
        this.setT(new Tipo((byte) 19));
    }

    @Override
    public String getValue(final DeclaracionVariable DV) {
        return String.valueOf(this.getRobot().PosAv());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        synchronized (this) {
            final PosAv obj = new PosAv();
            obj.setT(new Tipo((byte) 19));
            return obj;
        }
    }
}
