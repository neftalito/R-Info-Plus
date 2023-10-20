
package arbol;

import java.util.ArrayList;

public class DeclaracionRobots {
    public ArrayList<RobotAST> robots;

    public DeclaracionRobots(final ArrayList<RobotAST> robots) {
        this.robots = robots;
    }

    public ArrayList<RobotAST> getRobots() {
        return this.robots;
    }

    public void setRobots(final ArrayList<RobotAST> robots) {
        this.robots = robots;
    }

    public boolean estaRobot(final String spelling) {
        for (int i = 0; i < this.robots.size(); ++i) {
            if (this.robots.get(i).getNombre().equals(spelling)) {
                return true;
            }
        }
        return false;
    }

    public RobotAST getRobot(final String spelling) {
        final RobotAST proc = null;
        for (int i = 0; i < this.robots.size(); ++i) {
            if (this.robots.get(i).getNombre().equals(spelling)) {
                return this.robots.get(i);
            }
        }
        System.out.println("No devolvi nada en el getRobot del Declaracion Robots");
        return proc;
    }
}
