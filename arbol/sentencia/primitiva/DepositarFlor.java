
package arbol.sentencia.primitiva;

public class DepositarFlor extends Primitiva {
    int ciclo;

    public DepositarFlor() {
        this.ciclo = 1;
    }

    public int getCiclo() {
        return this.ciclo;
    }

    @Override
    public void ejecutar() throws Exception {
        synchronized (this) {
            this.getRobot().depositarFlor();
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new DepositarFlor();
    }
}
