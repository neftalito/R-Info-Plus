
package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.Tipo;

public class HayPapelEnLaBolsa extends Expresion {
    public HayPapelEnLaBolsa() {
        this.setT(new Tipo((byte) 20));
    }

    @Override
    public String getValue(final DeclaracionVariable DV) {
        if (this.getRobot().HayPapelEnLaBolsa()) {
            return "V";
        }
        return "F";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new HayPapelEnLaBolsa();
    }
}
