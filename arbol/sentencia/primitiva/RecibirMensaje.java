
package arbol.sentencia.primitiva;

import arbol.DeclaracionVariable;
import arbol.Identificador;

public class RecibirMensaje extends Primitiva {
    Identificador NombreRobot;
    Identificador nombreVariable;

    public RecibirMensaje(final Identificador E, final DeclaracionVariable DV, final Identificador I) {
        this.setDV(DV);
        this.NombreRobot = I;
        this.nombreVariable = E;
    }

    @Override
    public Object clone() {
        System.out.println("Clone Recibir Mensaje");
        this.getDV().imprimir();
        System.out.println("--------");
        return new RecibirMensaje(this.nombreVariable, this.getDV(), this.NombreRobot);
    }

    @Override
    public void ejecutar() throws Exception {
        synchronized (this) {
            System.out.println("Ejecutar Recibir Mensaje");
            System.out.println("-------- Imprimir Variables DV");
            this.getDV().imprimir();
            System.out.println("-------- Fin Imprimir Variables DV");
            System.out.println("-------- Imprimir Variables Robot");
            this.getRobot().getVariables().imprimir();
            System.out.println("-------- Fin Imprimir Variables");
            if (!this.getDV().EstaVariable(this.nombreVariable.toString())) {
                this.getPrograma().getCity()
                        .parseError("La variable " + this.nombreVariable.toString() + " no esta declarada");
                throw new Exception("La variable " + this.nombreVariable.toString() + " no esta declarada");
            }
            final DeclaracionVariable aux = this.getRobot().getVariables();
            this.getRobot().setVariables(this.getDV());
            int id;
            if (!this.NombreRobot.toString().equals("*")) {
                id = this.getRobot().getCity().getRobotByNombre(this.NombreRobot.toString()).id;
            } else {
                id = -1;
            }
            this.getRobot().recibirMensaje(this.nombreVariable, id, this.NombreRobot);
            this.getRobot().setVariables(aux);
        }
    }
}
