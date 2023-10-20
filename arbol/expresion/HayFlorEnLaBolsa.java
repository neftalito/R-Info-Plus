
package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.Tipo;

public class HayFlorEnLaBolsa extends Expresion {
    public HayFlorEnLaBolsa() {
        this.setT(new Tipo((byte) 20));
    }

    @Override
    public String getValue(final DeclaracionVariable DV) {
        synchronized (this) {
            if (this.getRobot().HayFlorEnLaBolsa()) {
                return "V";
            }
            return "F";
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new HayFlorEnLaBolsa();
    }
}
