
package arbol.sentencia.primitiva;

public class TomarFlor extends Primitiva {
    int ciclo;

    public TomarFlor() {
        this.ciclo = 1;
    }

    public int getCiclo() {
        return this.ciclo;
    }

    @Override
    public void ejecutar() throws Exception {
        synchronized (this) {
            this.getRobot().tomarFlor();
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new TomarFlor();
    }
}
