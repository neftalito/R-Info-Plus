
package arbol.llamada;

import arbol.expresion.Expresion;
import java.util.ArrayList;
import form.Robot;
import arbol.DeclaracionVariable;
import arbol.AST;

public abstract class Llamada extends AST {
    DeclaracionVariable DV;
    Robot r;

    public DeclaracionVariable getDV() {
        return this.DV;
    }

    public void setDV(final DeclaracionVariable DV) {
        this.DV = DV;
    }

    public Robot getRobot() {
        return this.r;
    }

    public void setRobot(final Robot r) {
        this.r = r;
    }

    public void ejecutar(final ArrayList<Expresion> E) throws Exception {
    }

    public Llamada nuevo() throws Exception {
        throw new Exception("error en nuevo llamada , similar al clone");
    }
}
