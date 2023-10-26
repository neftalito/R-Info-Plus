package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.Tipo;

public class CantidadFloresBolsa extends Expresion {

    public CantidadFloresBolsa() {
        this.setT(new Tipo((byte) 82));
    }

    @Override
    public String getValue(final DeclaracionVariable DV) {
        synchronized (this) {
            return String.valueOf(this.getRobot().getFlores());
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new CantidadFloresBolsa();
    }
}