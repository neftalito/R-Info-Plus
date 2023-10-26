
package arbol.sentencia.primitiva;

import java.io.IOException;
import java.net.UnknownHostException;

public class Izquierda extends Primitiva {
    int ciclo;

    public Izquierda() {
        this.ciclo = 1;
    }

    public int getCiclo() {
        return this.ciclo;
    }

    @Override
    public void ejecutar() throws UnknownHostException, IOException {
        this.getRobot().izquierda();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Izquierda();
    }
}
