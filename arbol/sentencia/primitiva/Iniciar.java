
package arbol.sentencia.primitiva;

import arbol.DeclaracionProcesos;
import form.Robot;
import form.EjecucionRobot;
import arbol.Cuerpo;
import arbol.Variable;
import arbol.sentencia.Sentencia;
import java.util.ArrayList;
import arbol.DeclaracionVariable;
import arbol.DeclaracionRobots;
import arbol.Identificador;
import arbol.RobotAST;

public class Iniciar extends Primitiva {
    RobotAST r;
    int x;
    int y;
    Identificador Ident;
    DeclaracionRobots robAST;
    DeclaracionVariable varAST;

    public Iniciar(final Identificador I, final int x, final int y, final DeclaracionRobots robAST,
            final DeclaracionVariable varAST) throws Exception {
        this.varAST = varAST;
        this.robAST = robAST;
        this.Ident = I;
        this.x = x;
        this.y = y;
        if (varAST.EstaVariable(I.toString())) {
            this.r = varAST.findByName(I.toString()).getR();
            System.out.println("para la variable " + I.toString() + " el robot es : " + this.r.getNombre());
            return;
        }
        throw new Exception("El robot tiene que estar declarado en las variables");
    }

    @Override
    public void ejecutar() throws Exception {
        Robot rob = null;
        Cuerpo cu = this.r.getCuerpo();
        final DeclaracionVariable dv = this.r.getDV();
        final DeclaracionProcesos procAST = this.r.getProcAST();
        final String nom = this.Ident.toString();
        if (this.programa.getCity().estaRobot(nom)) {
            System.out.println("El robot estaba " + nom + " y su tipo es : " + this.r.getNombre());
            rob = this.programa.getCity().getRobotByNombre(nom);
        } else {
            System.out.println("El robot no estaba : " + nom);
        }
        if (rob.esAreaVacia()) {
            final String msj = "El robot: " + rob.getNombre() + " no tiene area designada";
            this.getPrograma().getCity().parseError(msj);
            throw new Exception(msj);
        }
        final IniciarRobot ini = new IniciarRobot(this.x, this.y);
        ini.setRobot(rob);
        final ArrayList<Sentencia> sent = new ArrayList<Sentencia>();
        sent.add(ini);
        for (final Sentencia single : cu.getS()) {
            System.out.println("Sentencia clone es : " + single.toString());
            final Sentencia i = (Sentencia) single.clone();
            i.setPrograma(this.getPrograma());
            sent.add(i);
        }
        final ArrayList<Variable> dvs = new ArrayList<Variable>();
        for (final Variable b : dv.variables) {
            dvs.add((Variable) b.clone());
        }
        final DeclaracionVariable fv = new DeclaracionVariable(dvs);
        System.out.println("La cantidad de sentencias de este cuerpo es: " + sent.size());
        cu = new Cuerpo(sent, fv);
        cu.setRobot(rob);
        rob.setVariables(fv);
        rob.setProcAST(procAST);
        rob.setCuerpo(cu);
        rob.getCuerpo().setPrograma(this.getPrograma());
        final Runnable exec1 = new EjecucionRobot(rob, false, this.getPrograma().getCodigo());
        final Thread thread = new Thread(exec1);
        this.programa.getCity().agregarHilo(thread);
        thread.start();
    }
}
