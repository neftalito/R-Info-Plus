
package arbol.llamada;

import arbol.DeclaracionVariable;
import arbol.expresion.Expresion;
import java.util.ArrayList;

public class Pos extends Llamada {
    int ciclo;

    public int getCiclo() {
        return this.ciclo;
    }

    @Override
    public Llamada nuevo() throws Exception {
        return new Pos(this.getDV());
    }

    @Override
    public void ejecutar(final ArrayList<Expresion> E) throws Exception {
        synchronized (this) {
            for (final Expresion exp : E) {
                exp.setRobot(this.getRobot());
            }
            final int x = Integer.parseInt(E.get(0).getValue(this.DV));
            final int y = Integer.parseInt(E.get(1).getValue(this.DV));
            if (x <= 0 || x >= 101 || y <= 0 || y >= 101) {
                super.getPrograma().getCity().parseError(
                        "Se esperaba valor numerico mayor a 0 (cero) en la instrucci\u00f3n Pos y menor a 100(cien)");
                throw new Exception(
                        "Se esperaba valor numerico mayor a 0 (cero) en la instrucci\u00f3n Pos y menor a 100(cien)");
            }
            if (this.getPrograma().getCity().HayObstaculo(x, y)) {
                super.getPrograma().getCity().parseError(
                        "No se puede ejecutar la instrucci\u00f3n Pos ya que en esa posici\u00f3n hay un Obstaculo");
                throw new Exception(
                        "No se puede ejecutar la instrucci\u00f3n Pos ya que en esa posici\u00f3n hay un Obstaculo");
            }
            this.getRobot().Pos(x, y);
        }
    }

    public Pos(final DeclaracionVariable DV) {
        this.ciclo = 1;
        this.DV = DV;
    }
}
