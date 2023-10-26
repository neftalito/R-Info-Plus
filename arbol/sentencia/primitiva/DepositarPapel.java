
package arbol.sentencia.primitiva;

public class DepositarPapel extends Primitiva {
    int ciclo;

    public DepositarPapel() {
        this.ciclo = 1;
    }

    public int getCiclo() {
        return this.ciclo;
    }

    @Override
    public void ejecutar() throws Exception {
        synchronized (this) {
            this.getRobot().depositarPapel();
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new DepositarPapel();
    }
}
