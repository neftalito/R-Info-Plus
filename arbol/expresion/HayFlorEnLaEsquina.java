
package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.Tipo;

public class HayFlorEnLaEsquina extends Expresion {
    public HayFlorEnLaEsquina() {
        this.setT(new Tipo((byte) 20));
    }

    @Override
    public String getValue(final DeclaracionVariable DV) {
        final int Av = this.getRobot().PosAv();
        final int Ca = this.getRobot().PosCa();
        return this.getRobot().getCity().HayFlorEnLaEsquina(Av, Ca) ? "V" : "F";
    }

    @Override
    public boolean ejecutar() {
        synchronized (this) {
            return getValue(this.DV).equals("V");
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new HayFlorEnLaEsquina();
    }
}
