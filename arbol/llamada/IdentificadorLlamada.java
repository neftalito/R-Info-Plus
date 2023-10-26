
package arbol.llamada;

import arbol.Proceso;
import arbol.Tipo;
import arbol.Variable;
import arbol.expresion.Expresion;
import java.util.ArrayList;
import arbol.DeclaracionVariable;
import arbol.DeclaracionProcesos;
import arbol.Identificador;

public class IdentificadorLlamada extends Llamada {
    Identificador I;
    DeclaracionProcesos DP;

    public IdentificadorLlamada(final Identificador I, final DeclaracionVariable DV) {
        this.I = null;
        this.DP = null;
        this.I = I;
        this.DV = DV;
    }

    @Override
    public Llamada nuevo() throws Exception {
        return new IdentificadorLlamada(this.I, this.DV);
    }

    @Override
    public void ejecutar(final ArrayList<Expresion> E) throws Exception {
        synchronized (this) {
            for (final Expresion exp : E) {
                exp.setRobot(this.getRobot());
            }
            final ArrayList<Variable> devolverValorOrigen = new ArrayList<Variable>();
            final ArrayList<Variable> devolverValorDestino = new ArrayList<Variable>();
            final String spelling = this.I.toString();
            Proceso proc = null;
            this.DP = this.getRobot().getProcAST();
            if (!this.DP.estaProceso(spelling)) {
                super.getPrograma().getCity().parseError("Error, instrucci\u00f3n desconocida: " + spelling);
                throw new Exception("Error, instrucci\u00f3n desconocida: " + spelling);
            }
            proc = this.DP.getProceso(spelling);
            if (proc.getPF().size() != E.size()) {
                super.programa.getCity()
                        .parseError("Los parametros no coinciden en el proceso " + proc.getI().toString());
                throw new Exception("Los parametros actuales no coinciden con los parametros formales");
            }
            if (proc.getPF().size() > 0) {
                for (int i = 0; i < proc.getPF().size(); ++i) {
                    if (proc.getPF().get(i).getTA().equals("ES")) {
                        try {
                            final Variable a = this.DV.findByName(E.get(i).getI().toString());
                            if (!this.DV.EstaParametro(a.getI().toString())) {
                                super.getPrograma().getCity()
                                        .parseError("Se esperaba una variable en la posici\u00f3n del parametro ES "
                                                + proc.getPF().get(i).getI().toString());
                                throw new Exception("Se esperaba una variable en la posici\u00f3n del parametro ES "
                                        + proc.getPF().get(i).getI().toString());
                            }
                            if (proc.getPF().get(i).getT().tipo != a.getT().tipo) {
                                super.getPrograma().getCity()
                                        .parseError("Se esperaba una variable de tipo "
                                                + proc.getPF().get(i).getT().toString() + " en el parametro "
                                                + proc.getPF().get(i).getI().toString());
                                throw new Exception(
                                        "Se esperaba una variable de tipo " + proc.getPF().get(i).getT().toString()
                                                + " en el parametro " + proc.getPF().get(i).getI().toString());
                            }
                        } catch (Exception ex) {
                            super.getPrograma().getCity().parseError("Se esperaba una variable de tipo "
                                    + proc.getPF().get(i).getT().toString() + " en la posici\u00f3n del parametro ES "
                                    + proc.getPF().get(i).getI().toString());
                            throw new Exception(ex.toString());
                        }
                    }
                    if (proc.getPF().get(i).getTA().equals("E")) {
                        if (E.get(i).getT() == null) {
                            if ((E.get(i).getValue(this.DV).equals("V") || E.get(i).getValue(this.DV).equals("F"))
                                    && proc.getPF().get(i).getT().toString().equals("numero")) {
                                super.getPrograma().getCity()
                                        .parseError("El proceso " + proc.getPF().get(i).getI().toString()
                                                + " esperaba recibir un parametro de tipo "
                                                + proc.getPF().get(i).getT().toString());
                                throw new Exception("El proceso " + proc.getPF().get(i).getI().toString()
                                        + " esperaba recibir un parametro de tipo "
                                        + proc.getPF().get(i).getT().toString());
                            }
                            if (!E.get(i).getValue(this.DV).equals("V") && !E.get(i).getValue(this.DV).equals("F")
                                    && proc.getPF().get(i).getT().toString().equals("boolean")) {
                                super.getPrograma().getCity()
                                        .parseError("El proceso " + proc.getPF().get(i).getI().toString()
                                                + " esperaba recibir un parametro de tipo "
                                                + proc.getPF().get(i).getT().toString());
                                throw new Exception("El proceso " + proc.getPF().get(i).getI().toString()
                                        + " esperaba recibir un parametro de tipo "
                                        + proc.getPF().get(i).getT().toString());
                            }
                        } else if (!proc.getPF().get(i).getT().toString().equals(E.get(i).getT().toString())) {
                            super.getPrograma().getCity()
                                    .parseError("El proceso " + proc.getPF().get(i).getI().toString()
                                            + " esperaba recibir un parametro de tipo "
                                            + proc.getPF().get(i).getT().toString());
                            throw new Exception("El proceso " + proc.getPF().get(i).getI().toString()
                                    + " esperaba recibir un parametro de tipo "
                                    + proc.getPF().get(i).getT().toString());
                        }
                    }
                }
            }
            if (E.size() > 0) {
                for (int aux = E.size(), j = 0; j < aux; ++j) {
                    final Variable var = new Variable(proc.getPF().get(j).getI(), proc.getPF().get(j).getT(), this.DV,
                            null, null);
                    var.setValue(E.get(j).getValue(this.DV));
                    this.DP.getProceso(spelling).getPF().get(j).setValue(E.get(j).getValue(this.DV));
                    if (this.DP.getProceso(spelling).getDV()
                            .EstaVariable(this.DP.getProceso(spelling).getPF().get(j).getI().toString())) {
                        this.DP.getProceso(spelling).getDV()
                                .findByName(this.DP.getProceso(spelling).getPF().get(j).getI().toString())
                                .setValue(E.get(j).getValue(this.DV));
                    } else {
                        // ! QUE ES ESTO
                        this.DP.getProceso(spelling).getDV().variables.add(var);
                        final Variable var2 = new Variable(new Identificador("pepe"), new Tipo((byte) 19), this.DV,
                                null, null);
                        var2.setValue("222222");
                        this.DP.getProceso(spelling).getDV().variables.add(var2);
                    }
                    if (proc.getPF().get(j).getTA().equals("ES")) {
                        devolverValorOrigen.add(var);
                        final Variable var3 = (Variable) E.get(j);
                        devolverValorDestino.add(var3);
                    }
                }
            }
            proc.getC().setPrograma(super.getPrograma());
            proc.getC().setRobot(this.getRobot());
            proc.getC().ejecutar();
            if (devolverValorOrigen.size() > 0) {
                for (int x = devolverValorOrigen.size(), k = 0; k < x; ++k) {
                    devolverValorDestino.get(k).setValue(devolverValorOrigen.get(k).getValue(proc.getDV()));
                }
            }
            if (devolverValorDestino.size() > 0) {
                for (int z = devolverValorOrigen.size(), k = 0; k < z; ++k) {
                    final Variable exp2 = devolverValorDestino.get(k);
                    final Variable exp3 = null;
                    this.DV.findByName(exp2.getI().toString()).setValue(exp2.getValor());
                }
            }
        }
    }
}
