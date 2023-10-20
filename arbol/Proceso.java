
package arbol;

import java.util.ArrayList;

public class Proceso extends AST {
    Identificador I;
    ArrayList<ParametroFormal> PF;
    DeclaracionProcesos DP;
    DeclaracionVariable DV;
    Cuerpo C;

    public Proceso(final Identificador I, final ArrayList<ParametroFormal> PF, final DeclaracionProcesos DP,
            final DeclaracionVariable DV, final Cuerpo C) {
        this.I = I;
        this.PF = PF;
        this.DP = DP;
        this.DV = DV;
        this.C = C;
    }

    public Cuerpo getC() {
        return this.C;
    }

    public void setC(final Cuerpo C) {
        this.C = C;
    }

    public DeclaracionProcesos getDP() {
        return this.DP;
    }

    public void setDP(final DeclaracionProcesos DP) {
        this.DP = DP;
    }

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

    public ArrayList<ParametroFormal> getPF() {
        return this.PF;
    }

    public void setPF(final ArrayList<ParametroFormal> PF) {
        this.PF = PF;
    }
}
