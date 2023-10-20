
package arbol.sentencia.primitiva;

import java.io.IOException;
import java.net.UnknownHostException;

public class Derecha extends Primitiva {
    int ciclo;

    public Derecha() {
        this.ciclo = 1;
    }

    public int getCiclo() {
        return this.ciclo;
    }

    @Override
    public void ejecutar() throws UnknownHostException, IOException {
        this.getRobot().derecha();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final Derecha obj = new Derecha();
        return obj;
    }
}
