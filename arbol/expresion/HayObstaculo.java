
package arbol.expresion;

import arbol.DeclaracionVariable;
import arbol.Tipo;

public class HayObstaculo extends Expresion {
    public HayObstaculo() {
        this.setT(new Tipo((byte) 20));
    }

    @Override
    public String getValue(final DeclaracionVariable DV) throws Exception {
        final int Av = this.getRobot().PosAv();
        final int Ca = this.getRobot().PosCa();
        if (this.getRobot().getCity().HayObstaculoEnLaEsquina(Av, Ca)) {
            return "V";
        }
        return "F";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final HayObstaculo obj = new HayObstaculo();
        return obj;
    }
}