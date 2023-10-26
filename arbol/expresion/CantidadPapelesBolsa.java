package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.Tipo;

public class CantidadPapelesBolsa extends Expresion {

    public CantidadPapelesBolsa() {
        this.setT(new Tipo((byte) 83));
    }

    @Override
    public String getValue(final DeclaracionVariable DV) {
        synchronized (this) {
            return String.valueOf(this.getRobot().getPapeles());
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new CantidadPapelesBolsa();
    }
}