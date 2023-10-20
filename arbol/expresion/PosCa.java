
package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.Tipo;

public class PosCa extends Expresion {
    public PosCa() {
        this.setT(new Tipo((byte) 19));
    }

    @Override
    public String getValue(final DeclaracionVariable DV) {
        return String.valueOf(this.getRobot().PosCa());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        synchronized (this) {
            final PosCa obj = new PosCa();
            obj.setT(new Tipo((byte) 19));
            return obj;
        }
    }
}
