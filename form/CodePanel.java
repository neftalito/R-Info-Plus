
package form;

import javax.swing.border.TitledBorder;
import arbol.Programa;

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JFileChooser;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CodePanel extends JPanel{
    private JToolBar toolBar;
    public MyTextPane text;
    Ciudad city;
    MonitorActualizarVentana esperarRefresco;
    private JButton saveButton;
    private JButton newButton;
    private JButton openButton;
    private JButton runButton;
    private JButton resetButton;
    private JButton stepButton;
    private JButton stopButton;
    private JComboBox speedCombo;
    private JButton pauseButton;
    private JButton compileButton;
    private JButton saveAsButton;
    private JButton loadCityButton;
    private JButton saveCityButton;
    JTextField NombreRobot;
    Thread thread;
    Thread threadVentana;
    Runnable exec1;
    Runnable exec2;
    Ejecucion exec;
    String path;

    String[] cityCoord;
    int[][][] cityMap = new int[100][100][2];

    public CodePanel(final Ciudad city) throws Exception {
        this.esperarRefresco = MonitorActualizarVentana.getInstance();
        this.NombreRobot = new JTextField(5);
        this.thread = null;
        this.path = "";
        this.city = city;
        this.setLayout(new BorderLayout());
        this.toolBar = new JToolBar();
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/newFile24.png"));
        (this.newButton = new JButton(icon)).setToolTipText("Nuevo");
        icon = new ImageIcon(this.getClass().getResource("/images/compile.png"));
        (this.compileButton = new JButton(icon)).setToolTipText("Compilar");
        icon = new ImageIcon(this.getClass().getResource("/images/openProject24.png"));
        (this.openButton = new JButton(icon)).setToolTipText("Abrir");
        icon = new ImageIcon(this.getClass().getResource("/images/guardar_como.png"));
        (this.saveAsButton = new JButton(icon)).setToolTipText("Guardar Como");
        icon = new ImageIcon(this.getClass().getResource("/images/save.png"));
        (this.saveButton = new JButton(icon)).setToolTipText("Guardar");
        icon = new ImageIcon(this.getClass().getResource("/images/runProject24.png"));
        (this.runButton = new JButton(icon)).setToolTipText("Ejecutar");
        icon = new ImageIcon(this.getClass().getResource("/images/escoba.png"));
        (this.resetButton = new JButton(icon)).setToolTipText("Reiniciar entorno");
        icon = new ImageIcon(this.getClass().getResource("/images/runProject25.png"));
        (this.stepButton = new JButton(icon)).setToolTipText("Ejecutar paso a paso");
        icon = new ImageIcon(this.getClass().getResource("/images/stop.png"));
        (this.stopButton = new JButton(icon)).setToolTipText("Parar programa");
        icon = new ImageIcon(this.getClass().getResource("/images/pause.png"));
        (this.pauseButton = new JButton(icon)).setToolTipText("Pausar programa");
        icon = new ImageIcon(this.getClass().getResource("/images/load_city_small.png"));
        (this.loadCityButton = new JButton(icon)).setToolTipText("Importar ciudad");
        icon = new ImageIcon(this.getClass().getResource("/images/load_city_small.png"));
        (this.saveCityButton = new JButton(icon)).setToolTipText("Exportar ciudad");
        try {
            this.path = new File(".").getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(InspectorVariables.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CodePanel.this.doNewCommand();
            }
        });
        this.openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    CodePanel.this.doOpenCommand();
                    CodePanel.this.saveButton.setEnabled(true);
                } catch (IOException ex) {
                    System.out.println("error en el abrir archivo");
                    Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        this.loadCityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    CodePanel.this.doLoadCityCommand();
                } catch (IOException ex) {
                    System.out.println("error en el abrir archivo");
                    Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        this.saveCityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CodePanel.this.doSaveCityCommand();
            }
        });

        this.saveAsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CodePanel.this.doSaveAsCommand();
            }
        });
        this.saveButton.setEnabled(false);
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CodePanel.this.doSaveCommand();
            }
        });
        this.stopButton.setEnabled(false);
        this.stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CodePanel.this.saveButton.setEnabled(false);
                CodePanel.this.newButton.setEnabled(true);
                CodePanel.this.openButton.setEnabled(true);
                CodePanel.this.runButton.setEnabled(false);
                CodePanel.this.resetButton.setEnabled(true);
                CodePanel.this.pauseButton.setEnabled(false);
                try {
                    CodePanel.this.doStopStepByStep();
                } catch (Exception ex) {
                    Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.runButton.setEnabled(false);
        this.runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    CodePanel.this.runButton.setEnabled(false);
                    CodePanel.this.stepButton.setEnabled(false);
                    CodePanel.this.doRunCommand();
                } catch (Exception ex) {
                    Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.stepButton.setEnabled(false);
        this.stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    CodePanel.this.saveButton.setEnabled(false);
                    CodePanel.this.newButton.setEnabled(false);
                    CodePanel.this.openButton.setEnabled(false);
                    CodePanel.this.runButton.setEnabled(false);
                    CodePanel.this.resetButton.setEnabled(false);
                    CodePanel.this.stepButton.setEnabled(true);
                    CodePanel.this.pauseButton.setEnabled(true);
                    CodePanel.this.doStepCommand();
                } catch (Exception ex) {
                    Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    CodePanel.this.doResetCommand();
                } catch (Exception ex) {
                    Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.pauseButton.setEnabled(false);
        this.pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    CodePanel.this.doPauseCommand();
                } catch (Exception ex) {
                    Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.compileButton.setEnabled(true);
        this.compileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    CodePanel.this.doCompileCommand();
                } catch (Exception ex) {
                    Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        (this.speedCombo = new JComboBox()).setMaximumSize(new Dimension(80, 34));
        this.speedCombo.addItem("Insta");
        this.speedCombo.addItem("Max");
        this.speedCombo.addItem("Med");
        this.speedCombo.addItem("Min");
        this.toolBar.add(this.newButton);
        this.toolBar.add(this.openButton);
        this.toolBar.add(this.saveAsButton);
        this.toolBar.add(this.saveButton);
        this.toolBar.add(this.compileButton);
        this.toolBar.add(this.runButton);
        this.toolBar.add(this.speedCombo);
        this.toolBar.add(this.resetButton);
        this.toolBar.add(this.stepButton);
        this.toolBar.add(this.stopButton);
        this.toolBar.add(this.pauseButton);
        this.toolBar.addSeparator(new Dimension(20, 10));
        this.toolBar.add(this.loadCityButton);
        this.toolBar.add(this.saveCityButton);
        this.toolBar.add(new JPanel());
        this.add(this.toolBar, "North");
        (this.text = new MyTextPane(this.city)).setP(this);
        this.add(this.text, "Center");
    }

    public void doNewCommand() {
        this.text.setText("");
        this.city.areas = new ArrayList<Area>();
        this.city.form.jsp.refresh();
    }

    public void doPauseCommand() {
        this.city.form.monitorCola.pausar_ejecucion();
        this.city.form.monitorCola.setEn_ejecucion(true);
        this.habilitarTodo();
        this.runButton.setEnabled(true);
        this.compileButton.setEnabled(false);
    }

    private void formKeyPressed(final KeyEvent evt) {
    }

    public void doStepCommand() throws Exception {
        this.compileButton.setEnabled(false);
        this.pauseButton.setEnabled(false);
        this.stopButton.setEnabled(true);
        this.esperarRefresco.setPasoAPaso(true);
        this.esperarRefresco.setApretoF7(false);
        this.esperarRefresco.setTimerOn(false);
        if (!this.esperarRefresco.isEn_ejecucion()) {
            this.thread.start();
            this.threadVentana.start();
            this.stepButton.setEnabled(true);
        } else {
            this.esperarRefresco.despertar();
        }
    }

    public void doStopStepByStep() throws Exception {
        this.city.form.monitorCola.termine_ejecucion();
        this.city.form.monitorCola.setEn_ejecucion(false);
        this.city.form.monitorCola.setSistemaPausado(false);
        for (final Thread t : this.city.hilos) {
            t.interrupt();
        }
        this.city.hilos = new ArrayList<Thread>();
        for (final Robot rrr : this.city.robots) {
            rrr.setEstado("finalizado");
        }
        this.thread.interrupt();
        this.habilitarTodo();
    }

    public void doStopError() throws Exception {
        this.city.form.monitorCola.termine_ejecucion();
        this.city.form.monitorCola.setEn_ejecucion(false);
        this.city.form.monitorCola.setSistemaPausado(false);
        for (final Thread t : this.city.hilos) {
            t.interrupt();
        }
        this.city.hilos = new ArrayList<Thread>();
        for (final Robot rrr : this.city.robots) {
            rrr.setEstado("interrumpido");
        }
        this.thread.interrupt();
        this.habilitarTodo();
    }

    public void doResetCommand() throws Exception {
        for (int i = 0; i <= 100; ++i) {
            for (int j = 0; j <= 100; ++j) {
                this.city.ciudad[i][j] = new Bolsa();
            }
        }
        for (final Robot rr : this.city.robots) {
            rr.reset();
        }
        this.city.form.jsp.refresh();
    }

    public void doOpenCommand() throws IOException {
        final JFileChooser chooser = new JFileChooser(this.path);
        final int status = chooser.showOpenDialog(this);
        if (status == 0) {
            final File f = chooser.getSelectedFile();
            this.path = f.getAbsolutePath();
            try {
                final FileReader fin = new FileReader(f);
                final BufferedReader br = new BufferedReader(fin);
                final char[] buffer = new char[4096];
                String txt = "";
                int len;
                while ((len = br.read(buffer, 0, buffer.length)) != -1) {
                    txt += new String(buffer, 0, len);
                }
                txt = txt.replace("\r", "");
                this.text.setText(txt);
            } catch (Exception exc) {
                System.out.println("Exception en el doOpenCommand");
            }
        }
    }

    public void doLoadCityCommand() throws IOException {
        final JFileChooser chooser = new JFileChooser(this.path);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Mapa de ciudad para R-info", "rcity"));
        chooser.setAcceptAllFileFilterUsed(false);
        final int status = chooser.showOpenDialog(this);
        if (status == 0) {
            final File f = chooser.getSelectedFile();
            this.path = f.getAbsolutePath();
            try {
                final FileReader fin = new FileReader(f);
                final BufferedReader br = new BufferedReader(fin);
                final char[] buffer = new char[4096];
                String txt = "";
                int len;
                while ((len = br.read(buffer, 0, buffer.length)) != -1) {
                    txt += new String(buffer, 0, len);
                }
                txt = txt.replace("\r", "");

                String[] allCoords = txt.split("_");

                for (String coord : allCoords) {
                    String[] data = coord.split("\\*");

                    String[] c = data[0].split("-");
                    String[] o = data[1].split("-");

                    // System.out.println("Int c0: " + Integer.parseInt(c[0]));
                    // System.out.println("Int c1: " + Integer.parseInt(c[1]));
                    // System.out.println("Int o0: " + Integer.parseInt(o[0]));
                    // System.out.println("Int o1: " + Integer.parseInt(o[1]));
                    this.city.ciudad[Integer.parseInt(c[0])][Integer.parseInt(c[1])].setFlores(Integer.parseInt(o[0]));
                    this.city.ciudad[Integer.parseInt(c[0])][Integer.parseInt(c[1])].setPapeles(Integer.parseInt(o[1]));
                }

                br.close(); // Añadido
                this.city.form.jsp.refresh();
            } catch (Exception exc) {
                System.out.println("Exception en el doLoadCityCommand");
                System.out.println(exc.getMessage());
            }
        }
    }

    public void doSaveCityCommand() {
        StringBuilder data = new StringBuilder();
        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                if (this.city.ciudad[i][j].getFlores() > 0 || this.city.ciudad[i][j].getPapeles() > 0) {
                    data.append(i)
                            .append("-")
                            .append(j)
                            .append("*")
                            .append(this.city.ciudad[i][j].getFlores())
                            .append("-")
                            .append(this.city.ciudad[i][j].getPapeles())
                            .append("_");
                }
            }
        }

        if (data.length() > 0) {
            data.deleteCharAt(data.length() - 1);

            final JFileChooser chooser = new JFileChooser(this.path);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Mapa de ciudad para R-info", "rcity"));
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showSaveDialog(null) == 0) {
                final File fFileName = chooser.getSelectedFile();
                Writer out = null;
                try {
                    try {
                        out = new OutputStreamWriter(new FileOutputStream(fFileName.toString().replaceFirst("[.][^.]+$", "") + ".rcity"), "UTF-8");
                        out.write(data.toString());
                    } finally {
                        out.close();
                    }
                } catch (Exception ex) {
                }
            }
        }

        // System.out.println(data);
    }

    public void doSaveCommand() {
        if (this.path.equals("") || this.path.isEmpty()) {
            this.doSaveAsCommand();
        } else {
            FileWriter fichero = null;
            PrintWriter pw = null;
            try {
                final int i = JOptionPane.showConfirmDialog(this,
                        "¿Realmente desea guardar en el archivo " + this.path + " ?", "Confirmar", 0);
                if (i == 0) {
                    fichero = new FileWriter(this.path);
                    pw = new PrintWriter(fichero);
                    pw.println(this.text.getText());
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    if (null != fichero) {
                        fichero.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } finally {
                try {
                    if (null != fichero) {
                        fichero.close();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void doSaveAsCommand() {
        final JFileChooser chooser = new JFileChooser(this.path);
        if (chooser.showSaveDialog(this.text) == 0) {
            final File fFileName = chooser.getSelectedFile();
            Writer out = null;
            try {
                try {
                    out = new OutputStreamWriter(new FileOutputStream(fFileName), "UTF-8");
                    out.write(this.text.getText());
                } finally {
                    out.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    public void doCompileCommand() throws Exception {
        try {
            final Programa[] prgAST = { null };
            final String code = this.text.getText();
            final MonitorEsquinas esquinas = MonitorEsquinas.crearMonitorEsquinas();
            if (code.length() > 0) {
                this.city.robots = new ArrayList<Robot>();
                this.city.areas = new ArrayList<Area>();
                final Parser[] parser = { null };
                try {
                    if (this.city.getCantidad_robots() > 0) {
                        this.city.form.c.conf.remove(9);
                        (this.city.form.c.conf.JP = new TablaRobot(this.city)).setBorder(new TitledBorder("ROBOTS"));
                        this.city.form.c.conf.add(this.city.form.c.conf.JP, this.city.form.c.conf.gbc);
                        this.city.form.c.conf.JP.repaint();
                    }
                    //parser[0] = new Parser(code, this.city);
                    //prgAST[0] = parser[0].parse();
                    this.city.robots = new ArrayList<Robot>();
                    this.city.areas = new ArrayList<Area>();
                    this.city.form.c.conf.remove(9);
                    (this.city.form.c.conf.JP = new TablaRobot(this.city)).setBorder(new TitledBorder("ROBOTS"));
                    this.city.form.c.conf.add(this.city.form.c.conf.JP, this.city.form.c.conf.gbc);
                    this.city.form.c.conf.JP.repaint();
                    this.city.form.c.conf.repaint();
                    this.city.form.compedos.iv.tempPanel.removeAll();
                    ((InspectorVariables.RobotsEnEjecucion) this.city.form.compedos.iv.tempPanelRobots).removeAll();
                    this.city.form.compedos.iv.datos_robots = new ArrayList<Datos>();
                    this.city.form.compedos.iv.tempPanel.add(this.city.form.compedos.iv.form());
                    this.city.form.compedos.iv.tempPanelRobots = this.city.form.compedos.iv.form2();
                    this.city.form.compedos.iv.repaint();
                    this.city.robots = new ArrayList<Robot>();
                    this.city.areas = new ArrayList<Area>();
                    parser[0] = new Parser(code, this.city);
                    prgAST[0] = parser[0].parse();
                    try {
                        for (final Robot r : this.city.robots) {
                            r.crearMonitor(this.city.robots.size());
                        }
                        this.esperarRefresco.setCantidad(this.city.robots.size());
                        this.city.form.monitorCola.setCant_robots(this.city.robots.size());
                        System.out.println("cantidad de robots en la ciudad " + this.city.robots.size());
                        this.city.form.monitorCola.setCant_ejecutandose(this.city.robots.size());
                        System.out.println("cantidad ejecutandose " + this.city.robots.size());
                        prgAST[0].setCity(this.city);
                        prgAST[0].setCodigo(this);
                        this.exec1 = new Ejecucion(prgAST[0], false, this);
                        this.thread = new Thread(this.exec1);
                        this.exec2 = new ttt(this.esperarRefresco, this.speedCombo.getSelectedItem());
                        this.threadVentana = new Thread(this.exec2);
                        this.runButton.setEnabled(true);
                        this.stepButton.setEnabled(true);
                    } catch (Exception e) {
                        parser[0].reportParseError("Error en Ejecutar!!:\n" + e);
                        this.runButton.setEnabled(true);
                        this.habilitarTodo();
                    }
                } catch (Exception e) {
                    parser[0].reportParseError("Error en compilaci\u00f3n :\n" + e + "\n" + e.getStackTrace());
                    this.runButton.setEnabled(true);
                    this.habilitarTodo();
                }
                this.city.form.jsp.refresh();
            } else {
                JOptionPane.showMessageDialog(this, "No hay c\u00f3digo para compilar...");
                this.habilitarTodo();
            }
        } catch (Exception e2) {
            System.out.println("Error en catch");
            System.out.println(e2.toString());
        }
    }

    public void doRunCommand() throws Exception {
        this.stopButton.setEnabled(true);
        this.pauseButton.setEnabled(true);
        this.compileButton.setEnabled(false);
        if (this.esperarRefresco.isEn_ejecucion()) {
            this.esperarRefresco.despertarPause();
        } else {
            this.esperarRefresco.setTimerOn(true);
            this.thread.start();
            this.threadVentana.start();
            this.runButton.setEnabled(false);
        }
    }

    public void habilitarTodo() {
        this.saveButton.setEnabled(false);
        this.newButton.setEnabled(true);
        this.openButton.setEnabled(true);
        this.runButton.setEnabled(false);
        this.resetButton.setEnabled(true);
        this.stepButton.setEnabled(false);
        this.stopButton.setEnabled(false);
        this.pauseButton.setEnabled(false);
        this.compileButton.setEnabled(true);
    }
}
