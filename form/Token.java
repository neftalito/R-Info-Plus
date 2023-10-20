
package form;

public class Token {
    public byte kind;
    public String spelling;
    public static final byte IDENTIFIER = 0;
    public static final byte INICIAR = 1;
    public static final byte MOVER = 2;
    public static final byte DERECHA = 3;
    public static final byte TOMARFLOR = 4;
    public static final byte TOMARPAPEL = 5;
    public static final byte DEPOSITARFLOR = 6;
    public static final byte DEPOSITARPAPEL = 7;
    public static final byte POSAV = 8;
    public static final byte POSCA = 9;
    public static final byte HAYFLORENLAESQUINA = 10;
    public static final byte HAYFLORENLABOLSA = 11;
    public static final byte HAYPAPELENLAESQUINA = 12;
    public static final byte HAYPAPELENLABOLSA = 13;
    public static final byte INFORMAR = 14;
    public static final byte POS = 15;
    public static final byte COMENZAR = 16;
    public static final byte VARIABLES = 17;
    public static final byte FIN = 18;
    public static final byte NUMERO = 19;
    public static final byte BOOLEAN = 20;
    public static final byte LPAREN = 21;
    public static final byte RPAREN = 22;
    public static final byte INTLITERAL = 23;
    public static final byte COMA = 24;
    public static final byte ERROR = 25;
    public static final byte EOT = 26;
    public static final byte EOL = 27;
    public static final byte DOSPUNTOS = 28;
    public static final byte PROGRAMA = 29;
    public static final byte IGUAL = 30;
    public static final byte ASIGNACION = 31;
    public static final byte VERDADERO = 32;
    public static final byte FALSO = 33;
    public static final byte SI = 34;
    public static final byte FINSI = 35;
    public static final byte REPETIR = 36;
    public static final byte MIENTRAS = 37;
    public static final byte PROCESOS = 38;
    public static final byte PROCESO = 39;
    public static final byte FINREPETIR = 40;
    public static final byte FINMIENTRAS = 41;
    public static final byte INDENT = 42;
    public static final byte DEDENT = 43;
    public static final byte SINO = 44;
    public static final byte MAS = 45;
    public static final byte MENOS = 46;
    public static final byte DIV = 47;
    public static final byte MULT = 48;
    public static final byte NOT = 49;
    public static final byte AND = 50;
    public static final byte OR = 51;
    public static final byte MENOR = 52;
    public static final byte MAYOR = 53;
    public static final byte DISTINTO = 54;
    public static final byte MAYORIGUAL = 55;
    public static final byte MENORIGUAL = 56;
    public static final byte ENTRADASALIDA = 57;
    public static final byte SALIDA = 58;
    public static final byte ENTRADA = 59;
    public static final byte PUNTOYCOMA = 60;
    public static final byte LKEY = 61;
    public static final byte RKEY = 62;
    public static final byte HAYOBSTACULO = 63;
    public static final byte OPERADOR = 64;
    public static final byte ROBOTS = 65;
    public static final byte ROBOT = 66;
    public static final byte ENVIARMENSAJE = 67;
    public static final byte RECIBIRMENSAJE = 68;
    public static final byte AREAS = 69;
    public static final byte AREAC = 70;
    public static final byte AREAP = 71;
    public static final byte AREAPC = 72;
    public static final byte ASIGNARAREA = 73;
    public static final byte COMENTARIO = 74;
    public static final byte LEER = 75;
    public static final byte BLOQUEARESQUINA = 76;
    public static final byte LIBERARESQUINA = 77;
    public static final byte COMILLASSIMPLE = 78;
    public static final byte RANDOM = 79;

    //Propios
    public static final byte COMILLASDOBLES = 80;
    public static final String[] spellings;

    public Token(final byte kind, final String spelling) {
        this.kind = kind;
        this.spelling = spelling;
        if (this.kind == 0) {
            for (byte i = 1; i <= spellings.length-1; ++i) {
                if (this.spelling.equals(Token.spellings[i])) {
                    this.kind = i;
                    break;
                }
            }
        }
    }

    static {
        spellings = new String[] { "IDENTIFIER", "Iniciar", "mover", "derecha", "tomarFlor", "tomarPapel",
                "depositarFlor", "depositarPapel", "PosAv", "PosCa", "HayFlorEnLaEsquina", "HayFlorEnLaBolsa",
                "HayPapelEnLaEsquina", "HayPapelEnLaBolsa", "Informar", "Pos", "comenzar", "variables", "fin", "numero",
                "boolean", "(", ")", "INTLITERAL", ",", "ERROR", "EOT", "EOL", ":", "programa", "=", ":=", "V", "F",
                "si", "finSi", "repetir", "mientras", "procesos", "proceso", "finrepetir", "finmientras", "INDENT",
                "DEDENT", "sino", "+", "-", "/", "*", "~", "&", "|", "<", ">", "<>", ">=", "<=", "ES", "S", "E", ";",
                "{", "}", "HayObstaculo", "OP", "robots", "robot", "EnviarMensaje", "RecibirMensaje", "areas", "AreaC",
                "AreaP", "AreaPC", "AsignarArea", "COMENTARIO", "Leer", "BloquearEsquina", "LiberarEsquina", "'",
                "Random", "\"" };
    }
}
