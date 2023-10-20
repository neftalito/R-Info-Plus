
package arbol.sentencia.primitiva;

public class TomarPapel extends Primitiva {
    int ciclo;

    public TomarPapel() {
        this.ciclo = 1;
    }

    public int getCiclo() {
        return this.ciclo;
    }

    @Override
    public void ejecutar() throws Exception {
        synchronized (this) {
            this.getRobot().tomarPapel();
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final TomarPapel obj = new TomarPapel();
        return obj;
    }
}
