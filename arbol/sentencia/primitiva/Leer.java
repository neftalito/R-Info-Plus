
package arbol.sentencia.primitiva;

import form.LeerVentana;
import arbol.DeclaracionVariable;
import arbol.expresion.Expresion;

public class Leer extends Primitiva {
    private Expresion E;
    private DeclaracionVariable DV;

    public Leer(final DeclaracionVariable DV, final Expresion E) throws Exception {
        this.setE(E);
        this.setDV(DV);
        DV.EstaParametro(E.getI().toString());
    }

    @Override
    public void ejecutar() throws Exception {
        final LeerVentana leerVentana = new LeerVentana(this.getDV(), this.getE());
    }

    public Expresion getE() {
        return this.E;
    }

    public void setE(final Expresion E) {
        this.E = E;
    }
}
