
package form;

import javax.swing.text.StyleConstants;
import java.awt.Color;
import javax.swing.text.SimpleAttributeSet;

class StyledToken {
    String spelling;
    byte kind;
    int from;
    SimpleAttributeSet attributes;
    static byte IDENTIFIER;
    static byte OPERADOR;
    static byte LITERAL;
    static byte PRIMITIVA;
    static byte VARIABLE;
    static byte PALABRACLAVE;
    static byte TIPOVARIABLE;
    static byte COMENTARIO;
    static byte EOF;
    static byte AREA;
    public static final String[] spellings;
    public static final String[] primitivas;
    public static final String[] variables;
    public static final String[] palabrasClave;
    public static final String[] areas;
    public static final String[] tipoVariable;
    public static final String[] comentario;

    StyledToken(final byte currentKind, final int from, final String spelling, final boolean ok,
            final boolean comillas) {
        this.kind = currentKind;
        this.spelling = spelling;
        this.from = from;
        this.attributes = new SimpleAttributeSet();
        this.spelling = spelling.replace("\r", " ");
        if (ok) {
            StyleConstants.setForeground(this.attributes, new Color(100, 100, 100, 255));
        } else if (comillas) {
            StyleConstants.setForeground(this.attributes, new Color(253, 1, 254, 255));
        } else {
            if (this.kind == StyledToken.IDENTIFIER) {
                for (final String type : StyledToken.primitivas) {
                    if (type.equals(spelling)) {
                        this.kind = StyledToken.PRIMITIVA;
                        StyleConstants.setItalic(this.attributes, true);
                        break;
                    }
                }
            }
            if (this.kind == StyledToken.IDENTIFIER) {
                for (final String type : StyledToken.variables) {
                    if (type.equals(spelling)) {
                        this.kind = StyledToken.VARIABLE;
                        StyleConstants.setForeground(this.attributes, new Color(0, 153, 0, 255));
                    }
                }
            }
            if (this.kind == StyledToken.IDENTIFIER) {
                for (final String type : StyledToken.palabrasClave) {
                    if (type.equals(spelling)) {
                        this.kind = StyledToken.PALABRACLAVE;
                        StyleConstants.setBold(this.attributes, true);
                        StyleConstants.setForeground(this.attributes, Color.RED);
                        break;
                    }
                }
            }
            if (this.kind == StyledToken.IDENTIFIER) {
                if ("V".equals(spelling)) {
                    this.kind = StyledToken.LITERAL;
                }
                if ("F".equals(spelling)) {
                    this.kind = StyledToken.LITERAL;
                }
            }
            if (this.kind == StyledToken.IDENTIFIER) {
                for (final String type : StyledToken.tipoVariable) {
                    if (type.equals(spelling)) {
                        this.kind = StyledToken.TIPOVARIABLE;
                        StyleConstants.setForeground(this.attributes, new Color(0, 0, 230, 255));
                    }
                }
            }
            if (this.kind == StyledToken.IDENTIFIER) {
                for (final String type : StyledToken.areas) {
                    if (type.equals(spelling)) {
                        this.kind = StyledToken.AREA;
                        StyleConstants.setBold(this.attributes, true);
                    }
                }
            }
            if (this.kind == StyledToken.LITERAL) {
                StyleConstants.setForeground(this.attributes, new Color(253, 1, 254, 255));
            }
            if (this.kind == StyledToken.COMENTARIO) {
                StyleConstants.setForeground(this.attributes, new Color(100, 100, 100, 255));
            }
        }
    }

    public SimpleAttributeSet getAttributes() {
        return this.attributes;
    }

    public int getFrom() {
        return this.from;
    }

    public int getLength() {
        return this.spelling.length();
    }

    static {
        StyledToken.IDENTIFIER = 0;
        StyledToken.OPERADOR = 1;
        StyledToken.LITERAL = 2;
        StyledToken.PRIMITIVA = 3;
        StyledToken.VARIABLE = 4;
        StyledToken.PALABRACLAVE = 5;
        StyledToken.TIPOVARIABLE = 6;
        StyledToken.COMENTARIO = 7;
        StyledToken.EOF = 8;
        StyledToken.AREA = 9;
        spellings = new String[] { "IDENTIFICADOR", "OPERADOR", "LITERAL", "PRIMITIVA", "VARIABLE", "PALABRACLAVE",
                "FIN DE ARCHIVO" };
        primitivas = new String[] { "EnviarMensaje", "RecibirMensaje", "BloquearEsquina", "LiberarEsquina", "Pos",
                "Informar", "AsignarArea", "Iniciar", "Leer", "Random", "mover", "derecha", "tomarFlor", "tomarPapel",
                "depositarFlor", "depositarPapel" };
        variables = new String[] { "PosAv", "PosCa", "HayFlorEnLaEsquina", "HayFlorEnLaBolsa", "HayPapelEnLaEsquina",
                "HayPapelEnLaBolsa", "HayObstaculo" };
        palabrasClave = new String[] { "comenzar", "variables", "fin", "programa", "procesos", "proceso", "ES", "S",
                "E", "areas", "robots", "robot" };
        areas = new String[] { "AreaP", "AreaC", "AreaPC" };
        tipoVariable = new String[] { "numero", "boolean" };
        comentario = new String[] { "{", "}" };
    }
}
