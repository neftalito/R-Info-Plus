
package arbol.sentencia;

import arbol.Programa;
import form.Robot;
import arbol.DeclaracionVariable;
import arbol.AST;

public abstract class Sentencia extends AST {
    DeclaracionVariable varAST;
    Robot r;

    public Robot getRobot() {
        return this.r;
    }

    public void setRobot(final Robot r) {
        this.r = r;
    }

    public void ejecutar() throws Exception {
    }

    public void setDV(final DeclaracionVariable varAST) {
        this.varAST = varAST;
    }

    public DeclaracionVariable getDV() {
        return this.varAST;
    }

    @Override
    public void setPrograma(final Programa P) {
        this.programa = P;
    }

    @Override
    public Programa getPrograma() {
        return this.programa;
    }
}
