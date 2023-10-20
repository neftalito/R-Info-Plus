
package arbol;

public class ParametroFormal extends AST {
    Identificador I;
    Tipo T;
    String TA;
    public String value;

    public Identificador getI() {
        return this.I;
    }

    public void setI(final Identificador I) {
        this.I = I;
    }

    public Tipo getT() {
        return this.T;
    }

    public void setT(final Tipo T) {
        this.T = T;
    }

    public String getTA() {
        return this.TA;
    }

    public void setTA(final String TA) {
        this.TA = TA;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public ParametroFormal(final Identificador I, final Tipo T, final String TA) {
        this.I = I;
        this.T = T;
        this.TA = TA;
    }
}
