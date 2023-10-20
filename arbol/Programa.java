
package arbol;

import form.CodePanel;
import form.Ciudad;
import form.Robot;

public class Programa extends AST {
    Identificador I;
    DeclaracionRobots DR;
    DeclaracionProcesos DP;
    DeclaracionVariable DV;
    DeclaracionAreas DA;
    Cuerpo C;
    Robot R;
    Ciudad city;
    CodePanel codigo;

    public Programa(final Identificador I, final DeclaracionProcesos DP, final DeclaracionVariable DV,
            final DeclaracionRobots DR, final Ciudad city, final Cuerpo C, final DeclaracionAreas DA) {
        this.DP = null;
        this.setI(I);
        this.setC(C);
        if (DP != null) {
            this.setDP(DP);
        }
        if (DV != null) {
            this.setDV(DV);
        }
        this.setDR(DR);
        this.setDA(DA);
        this.city = city;
    }

    public DeclaracionAreas getDA() {
        return this.DA;
    }

    public void setDA(final DeclaracionAreas DA) {
        this.DA = DA;
    }

    public Cuerpo getC() {
        return this.C;
    }

    public DeclaracionProcesos getDP() {
        return this.DP;
    }

    public DeclaracionVariable getDV() {
        return this.DV;
    }

    public DeclaracionRobots getDR() {
        return this.DR;
    }

    public void setDR(final DeclaracionRobots DR) {
        this.DR = DR;
    }

    public Identificador getI() {
        return this.I;
    }

    public Robot getRobot() {
        return this.R;
    }

    public void setI(final Identificador I) {
        this.I = I;
    }

    public void setC(final Cuerpo C) {
        (this.C = C).setPrograma(this);
    }

    public void setDP(final DeclaracionProcesos DP) {
        this.DP = DP;
    }

    public void setDV(final DeclaracionVariable DV) {
        this.DV = DV;
    }

    public void setRobot(final Robot R) {
        this.R = R;
    }

    public Ciudad getCity() {
        return this.city;
    }

    public void setCity(final Ciudad c) {
        this.city = c;
    }

    public CodePanel getCodigo() {
        return this.codigo;
    }

    public void setCodigo(final CodePanel codigo) {
        this.codigo = codigo;
    }

    public void ejecutar() throws Exception {
        this.getC().ejecutar();
    }
}
