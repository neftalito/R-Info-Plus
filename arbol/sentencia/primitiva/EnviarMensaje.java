
package arbol.sentencia.primitiva;

import arbol.DeclaracionVariable;
import arbol.expresion.Expresion;
import arbol.Identificador;

public class EnviarMensaje extends Primitiva {
    Identificador NombreRobot;
    Expresion E;

    public EnviarMensaje(final Expresion E, final DeclaracionVariable DV, final Identificador NombreRobot) {
        this.setDV(DV);
        this.NombreRobot = NombreRobot;
        this.E = E;
    }

    @Override
    public Object clone() {
        return new EnviarMensaje(this.E, this.getDV(), this.NombreRobot);
    }

    @Override
    public void ejecutar() throws Exception {
        synchronized (this) {
            final String nom = this.getRobot().getNombre();
            this.E.setDV(this.getDV());
            final String x = this.E.getValue(this.getDV());
            this.getPrograma().getCity().getRobotByNombre(this.NombreRobot.toString()).almacenarMensaje(nom, x);
        }
    }
}
