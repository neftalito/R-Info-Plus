
package arbol.sentencia.primitiva;

public class IniciarRobot extends Primitiva {
    int x;
    int y;

    IniciarRobot(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void ejecutar() throws Exception {
        this.getRobot().iniciar(this.x, this.y);
    }
}
