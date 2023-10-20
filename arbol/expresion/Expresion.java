
package arbol.expresion;

import form.Robot;
import arbol.Identificador;
import arbol.Tipo;
import arbol.DeclaracionVariable;
import arbol.AST;

public abstract class Expresion extends AST {
    public DeclaracionVariable DV;
    public Tipo T;
    public Identificador I;
    public Robot r;

    public DeclaracionVariable getDV() {
        return this.DV;
    }

    public void setDV(final DeclaracionVariable DV) {
        this.DV = DV;
    }

    public Identificador getI() {
        return this.I;
    }

    public void setI(final Identificador I) {
        this.I = I;
    }

    public boolean ejecutar() {
        return true;
    }

    public String getValue(final DeclaracionVariable DV) throws Exception {
        return "undefinedd";
    }

    public Tipo getT() {
        return this.T;
    }

    public void setT(final Tipo T) {
        this.T = T;
    }

    public Robot getRobot() {
        return this.r;
    }

    public void setRobot(final Robot r) {
        this.r = r;
    }

    @Override
    public abstract Object clone() throws CloneNotSupportedException;
}
