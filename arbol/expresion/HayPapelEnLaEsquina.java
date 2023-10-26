
package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.Tipo;

public class HayPapelEnLaEsquina extends Expresion {
    public HayPapelEnLaEsquina() {
        this.setT(new Tipo((byte) 20));
    }

    @Override
    public String getValue(final DeclaracionVariable DV) {
        synchronized (this) {
            final int Av = this.getRobot().PosAv();
            final int Ca = this.getRobot().PosCa();
            return this.getRobot().getCity().HayPapelEnLaEsquina(Av, Ca) ? "V" : "F";
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new HayPapelEnLaEsquina();
    }
}
