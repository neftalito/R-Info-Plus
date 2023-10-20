
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
        final boolean hayFlor = this.getRobot().getCity().HayFlorEnLaEsquina(Av, Ca);
        return hayFlor ? "V" : "F";
    }

    @Override
    public boolean ejecutar() {
        synchronized (this) {
            final String s1 = this.getValue(this.DV);
            final boolean res = s1.equals("V");
            return res;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new HayFlorEnLaEsquina();
    }
}
