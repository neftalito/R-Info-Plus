
package arbol;

import form.Robot;
import arbol.sentencia.Sentencia;
import java.util.ArrayList;

public class Cuerpo extends AST {
    private ArrayList<Sentencia> S;
    DeclaracionVariable varAST;
    Robot rob;

    public Cuerpo(final ArrayList<Sentencia> S, final DeclaracionVariable varAST) {
        this.setS(S);
        this.varAST = varAST;
    }

    public void ejecutar() throws Exception {
        for (final Sentencia single : this.S) {
            single.setDV(this.varAST);
            single.ejecutar();
        }
    }

    public Robot getRobot() {
        return this.rob;
    }

    public void setRobot(final Robot rob) {
        this.rob = rob;
        for (final Sentencia single : this.S) {
            single.setRobot(this.rob);
        }
    }

    public ArrayList<Sentencia> getS() {
        return this.S;
    }

    public void setS(final ArrayList<Sentencia> S) {
        this.S = S;
    }

    @Override
    public void setPrograma(final Programa P) {
        this.programa = P;
        for (final Sentencia single : this.S) {
            single.setPrograma(P);
        }
    }

    public void setDV(final DeclaracionVariable DV) {
        for (final Sentencia single : this.S) {
            single.setDV(DV);
        }
    }
}
