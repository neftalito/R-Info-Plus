
package arbol.sentencia.primitiva;

public class Mover extends Primitiva {
    int ciclo;

    public Mover() {
        this.ciclo = 1;
    }

    public int getCiclo() {
        return this.ciclo;
    }

    @Override
    public void ejecutar() throws Exception {
        this.programa.getCity().setOk(false);
        this.getRobot().mover();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Mover();
    }
}
