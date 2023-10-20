
package form;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;

public class Compedos extends JPanel {
    Ciudad city;
    InspectorVariables iv;
    GridBagConstraints gbc;

    Compedos(final Ciudad city) {
        this.gbc = new GridBagConstraints();
        this.city = city;
        this.iv = new InspectorVariables(this.city);
        this.setLayout(new BorderLayout());
        this.add(this.iv, "North");
    }
}
