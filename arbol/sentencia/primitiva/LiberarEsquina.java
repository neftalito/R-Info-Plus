
package arbol.sentencia.primitiva;

import java.util.logging.Level;
import java.util.logging.Logger;
import arbol.expresion.Expresion;
import arbol.DeclaracionVariable;

public class LiberarEsquina extends Primitiva {
    int ciclo;
    private int Av;
    private int Ca;
    DeclaracionVariable DV;
    Expresion E3;
    Expresion E4;

    public int getCiclo() {
        return this.ciclo;
    }

    public LiberarEsquina(final Expresion E1, final Expresion E2, final DeclaracionVariable DV) throws Exception {
        this.ciclo = 1;
        this.DV = DV;
        this.E3 = E1;
        this.E4 = E2;
    }

    public int getAv() {
        return this.Av;
    }

    public void setAv(final int Av) {
        this.Av = Av;
    }

    public int getCa() {
        return this.Ca;
    }

    public void setCa(final int Ca) {
        this.Ca = Ca;
    }

    @Override
    public void ejecutar() throws Exception {
        synchronized (this) {
            this.DV = this.getRobot().getVariables();
            this.E3.setRobot(this.getRobot());
            this.E4.setRobot(this.getRobot());
            this.setAv(Integer.parseInt(this.E3.getValue(this.getDV())));
            this.setCa(Integer.parseInt(this.E4.getValue(this.getDV())));
            if (this.getAv() < 1 || this.getAv() > 100 || this.getCa() < 1 || this.getCa() > 100) {
                this.getPrograma().getCity()
                        .parseError("Se esperaban valores entre 1 y 100 en la primitiva LiberarEsquina");
                throw new Exception("Se esperaban valores entre 1(uno) y 100(cien) en la primitiva LiberarEsquina");
            }
            this.getRobot().liberarEsquina(this.getAv(), this.getCa());
        }
    }

    @Override
    public Object clone() {
        synchronized (this) {
            LiberarEsquina b = null;
            try {
                final Expresion ee1 = (Expresion) this.E3.clone();
                final Expresion ee2 = (Expresion) this.E4.clone();
                b = new LiberarEsquina(ee1, ee2, this.DV);
            } catch (Exception ex) {
                Logger.getLogger(BloquearEsquina.class.getName()).log(Level.SEVERE, null, ex);
            }
            return b;
        }
    }
}
