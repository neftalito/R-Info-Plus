
package arbol.sentencia;

import arbol.Programa;
import arbol.Variable;
import arbol.DeclaracionVariable;
import arbol.expresion.Expresion;
import arbol.Identificador;

public class Asignacion extends Sentencia {
    Identificador I;
    Expresion E;
    DeclaracionVariable DV;

    public Asignacion(final Identificador I, final Expresion E, final DeclaracionVariable DV) {
        this.I = I;
        this.E = E;
        this.DV = DV;
    }

    @Override
    public void ejecutar() throws Exception {
        synchronized (this) {
            this.E.setRobot(this.getRobot());
            if (!this.DV.EstaParametro(this.I.toString())) {
                this.programa.getCity().parseError("Variable: " + this.I.toString() + "no encontrada");
                throw new Exception("Variable: " + this.I.toString() + "no encontrada");
            }
            final Variable tmp = this.DV.findByName(this.I.toString());
            final String eValue = this.E.getValue(this.DV);
            if (tmp.getT().toString().equals("boolean") && !eValue.equals("V") && !eValue.equals("F")) {
                this.programa.getCity().parseError(
                        "Se esperaba un valor booleano en la variable/parametro : " + tmp.getI().toString());
                throw new Exception(
                        "Se esperaba un valor booleano en la variable/parametro : " + tmp.getI().toString());
            }
            if (tmp.getT().toString().equals("numero")) {
                try {
                    System.out.println("e value vale:  " + eValue);
                    Integer.parseInt(eValue);
                } catch (Exception ex) {
                    this.programa.getCity().parseError(
                            "Se esperaba un valor numerico en la variable/parametro : " + tmp.getI().toString());
                    throw new Exception(
                            "Se esperaba un valor numerico en la variable/parametro : " + tmp.getI().toString());
                }
            }
            this.DV.findByName(this.I.toString()).setValue(eValue);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final Object obj = null;
        final Identificador II = new Identificador(this.I.toString());
        System.out.println("Intentando clonar expresion en asignacion");
        final Expresion EE = (Expresion) this.E.clone();
        return new Asignacion(II, EE, this.DV);
    }

    @Override
    public void setDV(final DeclaracionVariable varAST) {
        this.DV = varAST;
    }

    @Override
    public void setPrograma(final Programa P) {
        this.programa = P;
        this.E.setPrograma(P);
    }
}
