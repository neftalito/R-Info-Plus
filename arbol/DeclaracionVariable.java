
package arbol;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class DeclaracionVariable extends AST {
    public ArrayList<Variable> variables;

    public DeclaracionVariable(final ArrayList<Variable> var) {
        this.variables = var;
    }

    public void imprimir() {
        for (final Variable variable : this.variables) {
            System.out.println(variable.getI().toString());
        }
    }

    public boolean EstaParametro(final String act) throws Exception {
        int i;
        for (i = 0; i < this.variables.size(); ++i) {
            if (this.variables.get(i).getI().equals(act)) {
                return true;
            }
        }
        if (i == this.variables.size()) {
            this.reportError("Variable  '" + act + "'  no declarada.");
            throw new Exception("Variable '" + act + "' no declarada.");
        }
        return false;
    }

    public boolean EstaVariable(final String act) throws Exception {
        for (int i = 0; i < this.variables.size(); ++i) {
            if (this.variables.get(i).getI().equals(act)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void reportError(final String str) {
        JOptionPane.showMessageDialog(null, str, "ERROR", 0);
    }

    public Variable findByName(final String act) throws Exception {
        for (int i = 0; i < this.variables.size(); ++i) {
            if (this.variables.get(i).getI().toString().equals(act)) {
                return this.variables.get(i);
            }
        }
        this.reportError("Variable  '" + act + "'  no declarada.");
        throw new Exception("Variable '" + act + "' no declarada.");
    }
}
