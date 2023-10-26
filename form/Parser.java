
package form;

import arbol.sentencia.primitiva.Iniciar;
import arbol.sentencia.primitiva.AsignarArea;
import arbol.DeclaracionAreas;
import arbol.Programa;
import arbol.ParametroFormal;
import arbol.Proceso;
import arbol.RobotAST;
import arbol.DeclaracionProcesos;
import arbol.Cuerpo;
import arbol.sentencia.IteradorCondicional;
import arbol.sentencia.IteradorIncondicional;
import arbol.sentencia.Seleccion;
import arbol.sentencia.primitiva.Random;
import arbol.sentencia.primitiva.RecibirMensaje;
import arbol.sentencia.primitiva.EnviarMensaje;
import arbol.sentencia.primitiva.DepositarPapel;
import arbol.sentencia.primitiva.DepositarFlor;
import arbol.sentencia.primitiva.TomarPapel;
import arbol.sentencia.primitiva.TomarFlor;
import arbol.sentencia.primitiva.Derecha;
import arbol.sentencia.primitiva.Izquierda;
import arbol.sentencia.primitiva.Mover;
import arbol.sentencia.primitiva.Leer;
import arbol.sentencia.primitiva.BloquearEsquina;
import arbol.sentencia.primitiva.LiberarEsquina;
import arbol.sentencia.primitiva.Primitiva;
import arbol.llamada.IdentificadorLlamada;
import arbol.sentencia.Sentencia;
import arbol.sentencia.Asignacion;
import arbol.llamada.Pos;
import arbol.llamada.Informar;
import java.util.ArrayList;
import arbol.sentencia.Invocacion;

import javax.swing.JOptionPane;
import arbol.DeclaracionRobots;
import arbol.Tipo;
import arbol.Variable;
import arbol.Identificador;
import arbol.expresion.ExpresionBinaria;
import arbol.expresion.ExpresionUnaria;
import java.util.Stack;
import arbol.DeclaracionVariable;
import arbol.expresion.ValorBooleano;
import arbol.expresion.ValorNumerico;
import arbol.expresion.operador.relacional.MenorIgual;
import arbol.expresion.operador.relacional.Menor;
import arbol.expresion.operador.relacional.MayorIgual;
import arbol.expresion.operador.relacional.Mayor;
import arbol.expresion.operador.relacional.Igual;
import arbol.expresion.operador.relacional.Distinto;
import arbol.expresion.operador.booleano.Not;
import arbol.expresion.operador.booleano.Or;
import arbol.expresion.operador.booleano.And;
import arbol.expresion.operador.aritmetico.Suma;
import arbol.expresion.operador.aritmetico.Resta;
import arbol.expresion.operador.aritmetico.Multiplicacion;
import arbol.expresion.operador.aritmetico.Division;
import arbol.expresion.operador.aritmetico.Modulo;
import arbol.expresion.operador.Operador;
import arbol.expresion.HayObstaculo;
import arbol.expresion.HayPapelEnLaEsquina;
import arbol.expresion.HayFlorEnLaEsquina;
import arbol.expresion.HayPapelEnLaBolsa;
import arbol.expresion.HayFlorEnLaBolsa;
import arbol.expresion.PosCa;
import arbol.expresion.PosAv;
import arbol.expresion.CantidadFloresBolsa;
import arbol.expresion.CantidadPapelesBolsa;
import arbol.expresion.Expresion;

public class Parser {
    public Token currentToken;
    private Scanner scanner;
    Ciudad city;

    Parser(final String fuente, final Ciudad city) throws Exception {
        this.city = city;
        this.scanner = new Scanner(fuente);
        try {
            this.nextToken();
        } catch (Exception e) {
            this.reportParseError("Error in Parser!");
        }
    }

    public boolean isOperando(final Token token) {
        return token.kind == 23 || token.kind == 32 || token.kind == 33;
    }

    public boolean isVariableEntorno(final Token token) {
        switch (token.kind) {
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 63:
            case 82:
            case 83: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public boolean isOperador(final Token token) {
        boolean res = false;
        switch (token.kind) {
            case 30:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 81: {
                res = true;
                break;
            }
            default: {
                res = false;
                break;
            }
        }
        return res;
    }

    public boolean isParentesis(final Token token) {
        boolean res = false;
        switch (token.kind) {
            case 21:
            case 22: {
                res = true;
                break;
            }
            default: {
                res = false;
                break;
            }
        }
        return res;
    }

    public boolean thereIsHighOrEqualPrecedence(final Object o) {
        boolean res = true;
        final Token t = (Token) o;
        if (t.kind == 21 || (this.currentToken.kind == 49 && t.kind == 50)
                || (this.currentToken.kind == 49 && t.kind == 51)
                || ((this.currentToken.kind == 48 || this.currentToken.kind == 47 || this.currentToken.kind == 81)
                        && (t.kind == 45 || t.kind == 46))) {
            res = false;
        }
        return res;
    }

    public Expresion parseVariableEntorno(final Token t) {
        Expresion expAST = null;
        switch (t.kind) {
            case 8: {
                expAST = new PosAv();
                break;
            }
            case 9: {
                expAST = new PosCa();
                break;
            }
            case 11: {
                expAST = new HayFlorEnLaBolsa();
                break;
            }
            case 13: {
                expAST = new HayPapelEnLaBolsa();
                break;
            }
            case 10: {
                expAST = new HayFlorEnLaEsquina();
                break;
            }
            case 12: {
                expAST = new HayPapelEnLaEsquina();
                break;
            }
            case 63: {
                expAST = new HayObstaculo();
                break;
            }
            case 82: {
                expAST = new CantidadFloresBolsa();
                break;
            }
            case 83: {
                expAST = new CantidadPapelesBolsa();
                break;
            }
            default: {
                expAST = null;
                break;
            }
        }
        return expAST;
    }

    public Operador parseOperador(final Token t) {
        Operador expAST = null;
        switch (t.kind) {
            case 47: {
                expAST = new Division();
                break;
            }
            case 48: {
                expAST = new Multiplicacion();
                break;
            }
            case 46: {
                expAST = new Resta();
                break;
            }
            case 45: {
                expAST = new Suma();
                break;
            }
            case 50: {
                expAST = new And();
                break;
            }
            case 51: {
                expAST = new Or();
                break;
            }
            case 49: {
                expAST = new Not();
                break;
            }
            case 54: {
                expAST = new Distinto();
                break;
            }
            case 30: {
                expAST = new Igual();
                break;
            }
            case 53: {
                expAST = new Mayor();
                break;
            }
            case 55: {
                expAST = new MayorIgual();
                break;
            }
            case 52: {
                expAST = new Menor();
                break;
            }
            case 56: {
                expAST = new MenorIgual();
                break;
            }
            case 81: {
                expAST = new Modulo();
                break;
            }
            default: {
                expAST = null;
                break;
            }
        }
        return expAST;
    }

    public boolean isParametroFormal(final Token t) {
        return t.kind == 59 || t.kind == 58 || t.kind == 57;
    }

    public Expresion parseValorLiteral(final Token t) {
        Expresion expAST = null;
        switch (t.kind) {
            case 23: {
                expAST = new ValorNumerico(t.spelling);
                break;
            }
            case 32:
            case 33: {
                expAST = new ValorBooleano(t.spelling);
                break;
            }
            default: {
                expAST = null;
                break;
            }
        }
        return expAST;
    }

    public Expresion parseExpresion(final DeclaracionVariable DV) throws Exception {
        final Stack<Token> stackOperadores = new Stack<Token>();
        final Stack<Token> salida = new Stack<Token>();
        boolean error = false;
        Token t = null;
        final int fil = this.scanner.fil;
        while ((this.currentToken.kind == 0 || this.isVariableEntorno(this.currentToken)
                || this.isOperando(this.currentToken) || this.isOperador(this.currentToken)
                || this.isParentesis(this.currentToken)) && this.scanner.fil == fil) {
            Label_0313: {
                if (this.isVariableEntorno(this.currentToken) || this.isOperando(this.currentToken)
                        || this.currentToken.kind == 0) {
                    t = this.currentToken;
                    salida.push(t);
                } else if (this.isOperador(this.currentToken)) {
                    while (!stackOperadores.empty() && this.thereIsHighOrEqualPrecedence(stackOperadores.peek())) {
                        t = stackOperadores.pop();
                        salida.push(t);
                    }
                    stackOperadores.push(this.currentToken);
                } else if (this.currentToken.kind == 21) {
                    stackOperadores.push(this.currentToken);
                } else {
                    if (this.currentToken.kind != 22) {
                        throw new Exception("Situaci\u00f3n no contemplada en Parser.java: 254");
                    }
                    if (stackOperadores.empty()) {
                        break;
                    }
                    while (!stackOperadores.empty()) {
                        t = stackOperadores.pop();
                        if (t.kind != 21) {
                            salida.push(t);
                        }
                        if (t.kind == 21) {
                            break Label_0313;
                        }
                    }
                    error = true;
                }
            }
            if (error) {
                break;
            }
            this.nextToken();
        }
        while (!stackOperadores.empty()) {
            t = stackOperadores.pop();
            salida.push(t);
        }
        return this.procesarToken(salida, DV);
    }

    public Expresion procesarToken(final Stack<Token> pila, final DeclaracionVariable DV) throws Exception {
        Expresion expAST = null;
        final Token t = pila.pop();
        if (this.isOperador(t)) {
            Expresion E1 = null;
            Expresion E2 = null;
            if (!pila.isEmpty()) {
                E2 = this.procesarToken(pila, DV);
            }
            if (!pila.isEmpty() && !this.esUnario(t.kind)) {
                E1 = this.procesarToken(pila, DV);
            }
            if (E1 == null) {
                expAST = new ExpresionUnaria(this.parseOperador(t), E2);
            } else {
                expAST = new ExpresionBinaria(this.parseOperador(t), E1, E2);
            }
        } else if (this.isVariableEntorno(t)) {
            expAST = this.parseVariableEntorno(t);
        } else if (this.isOperando(t)) {
            expAST = this.parseValorLiteral(t);
        } else if (t.kind == 0) {
            expAST = new Variable(new Identificador(t.spelling), null, DV, null, null);
            expAST.setI(new Identificador(t.spelling));
        } else {
            System.out.println("Algo anda mal... Parser.java:394");
        }
        return expAST;
    }

    public void reportParseError(final String str) {
        JOptionPane.showMessageDialog(null,
                "l\u00ednea " + this.scanner.fil + " columna " + this.scanner.col + ": " + str,
                "Error de compilaci\u00f3n ", 0);
    }

    public void nextToken() throws Exception {
        do {
            this.currentToken = this.scanner.scan();
        } while (this.currentToken.kind == 74);
    }

    private void take(final byte expectedToken) throws Exception {
        if (this.currentToken.kind == expectedToken) {
            this.nextToken();
            return;
        }
        this.reportParseError("Se esperaba la palabra clave " + Token.spellings[expectedToken] + " en lugar de "
                + this.currentToken.spelling);
        throw new Exception();
    }

    public void takeIt() throws Exception {
        this.nextToken();
    }

    private Invocacion parseInformar(final DeclaracionVariable DV) throws Exception {
        final ArrayList<Expresion> E = new ArrayList<Expresion>();
        String mensaje = "";

        this.takeIt();
        this.take((byte) 21);

        if (this.currentToken.kind == 78 || this.currentToken.kind == 80) {
            this.takeIt();
            mensaje += this.currentToken.spelling;
            this.take((byte) 0);

            while (currentToken.kind == 0) {
                mensaje += " " + currentToken.spelling;
                takeIt(); // Para tener espacios en los informar
            }

            this.take((byte) (currentToken.kind == 78 ? 78 : 80));
            this.take((byte) 24);
        }
        do {
            Expresion exp = this.parseExpresion(DV);
            E.add(exp);

            if (this.currentToken.kind == 24) {
                this.takeIt();
            }
        } while (this.currentToken.kind != 22);

        this.take((byte) 22);

        return new Invocacion(new Informar(DV, mensaje), E, DV);
    }

    public Invocacion parsePos(final DeclaracionVariable DV) throws Exception {
        final ArrayList<Expresion> expArray = new ArrayList<Expresion>();
        this.takeIt();
        this.take((byte) 21);
        expArray.add(this.parseExpresion(DV));
        this.take((byte) 24);
        expArray.add(this.parseExpresion(DV));
        this.take((byte) 22);
        return new Invocacion(new Pos(DV), expArray, DV);
    }

    private Asignacion parseAsignacion(final String sVariable, final DeclaracionVariable DV) throws Exception {
        Identificador I = null;
        Expresion E = null;
        I = new Identificador(sVariable);
        E = this.parseExpresion(DV);
        return new Asignacion(I, E, DV);
    }

    private ArrayList<Sentencia> parseSecuenciaDeSentencias(final DeclaracionVariable DV) throws Exception {
        final ArrayList<Sentencia> senAST = new ArrayList<Sentencia>();
        Sentencia sen = null;
        while (this.currentToken.kind != 43 && this.currentToken.kind != 42 && this.currentToken.kind != 18) {
            sen = this.parseSentencia(DV);
            senAST.add(sen);
        }
        return senAST;
    }

    private Invocacion parseInvocacionProceso(final Token myToken, final DeclaracionVariable DV) throws Exception {
        final Identificador Ident = new Identificador(myToken.spelling);
        final ArrayList<Expresion> E = new ArrayList<Expresion>();
        Expresion exp = null;
        if (this.currentToken.kind == 21) {
            this.takeIt();
            do {
                exp = this.parseExpresion(DV);
                E.add(exp);
                if (this.currentToken.kind == 24) {
                    this.takeIt();
                }
            } while (this.currentToken.kind != 22);
            this.take((byte) 22);
        }
        return new Invocacion(new IdentificadorLlamada(Ident, DV), E, DV);
    }

    private Invocacion parseInvocacion(final Token myToken, final DeclaracionVariable DV) throws Exception {
        Invocacion invAST = null;
        switch (myToken.kind) {
            case 14: {
                invAST = this.parseInformar(DV);
                break;
            }
            case 15: {
                invAST = this.parsePos(DV);
                break;
            }
            case 0: {
                invAST = this.parseInvocacionProceso(myToken, DV);
                break;
            }
            default: {
                this.reportParseError("Se esperaba Invocaci\u00f3n");
                break;
            }
        }
        return invAST;
    }

    private Sentencia parseSentenciaSimple(final DeclaracionVariable DV) throws Exception {
        Sentencia senAST = null;
        switch (this.currentToken.kind) {
            case 14:
            case 15: {
                senAST = this.parseInvocacion(this.currentToken, DV);
                break;
            }
            case 0: {
                final Token sVariable = this.currentToken;
                this.takeIt();
                if (this.currentToken.kind == 31) {
                    this.takeIt();
                    senAST = this.parseAsignacion(sVariable.spelling, DV);
                    break;
                }
                senAST = this.parseInvocacion(sVariable, DV);
                break;
            }
            default: {
                throw new Exception("Se esperaba Sentencia Simple");
            }
        }
        return senAST;
    }

    private Primitiva parsePrimitiva(final DeclaracionVariable DV) throws Exception {
        Primitiva senAST = null;
        switch (this.currentToken.kind) {
            case 77: {
                this.takeIt();
                this.take((byte) 21);
                final Expresion E1 = this.parseExpresion(DV);
                this.take((byte) 24);
                final Expresion E2 = this.parseExpresion(DV);
                this.take((byte) 22);
                senAST = new LiberarEsquina(E1, E2, DV);
                break;
            }
            case 76: {
                this.takeIt();
                this.take((byte) 21);
                final Expresion E3 = this.parseExpresion(DV);
                this.take((byte) 24);
                final Expresion E4 = this.parseExpresion(DV);
                this.take((byte) 22);
                senAST = new BloquearEsquina(E3, E4, DV);
                break;
            }
            case 75: {
                this.takeIt();
                this.take((byte) 21);
                final Expresion Ex = this.parseExpresion(DV);
                this.take((byte) 22);
                senAST = new Leer(DV, Ex);
                break;
            }
            case 2: {
                senAST = new Mover();
                this.takeIt();
                break;
            }
            case 3: {
                senAST = new Derecha();
                this.takeIt();
                break;
            }
            case 4: {
                senAST = new TomarFlor();
                this.takeIt();
                break;
            }
            case 5: {
                senAST = new TomarPapel();
                this.takeIt();
                break;
            }
            case 6: {
                senAST = new DepositarFlor();
                this.takeIt();
                break;
            }
            case 7: {
                senAST = new DepositarPapel();
                this.takeIt();
                break;
            }
            case 67: {
                this.takeIt();
                this.take((byte) 21);
                final Expresion E5 = this.parseExpresion(DV);
                this.take((byte) 24);
                final Identificador I = new Identificador(this.currentToken.spelling);
                this.takeIt();
                this.take((byte) 22);
                senAST = new EnviarMensaje(E5, DV, I);
                break;
            }
            case 68: {
                this.takeIt();
                this.take((byte) 21);
                final Identificador I2 = new Identificador(this.currentToken.spelling);
                this.takeIt();
                this.take((byte) 24);
                final Identificador I = new Identificador(this.currentToken.spelling);
                this.takeIt();
                this.take((byte) 22);
                senAST = new RecibirMensaje(I2, DV, I);
                break;
            }
            case 79: {
                this.takeIt();
                this.take((byte) 21);
                final Identificador I3 = new Identificador(this.currentToken.spelling);
                this.takeIt();
                this.take((byte) 24);
                final Expresion E6 = this.parseExpresion(DV);
                this.take((byte) 24);
                final Expresion E7 = this.parseExpresion(DV);
                this.take((byte) 22);
                senAST = new Random(I3, DV, E6, E7);
                break;
            }
            case 84: {
                senAST = new Izquierda();
                this.takeIt();
                break;
            }
            default: {
                throw new Exception("Se esperaba una primitiva");
            }
        }
        return senAST;
    }

    private Expresion parseVariableSistema() throws Exception {
        Expresion senAST = null;
        switch (this.currentToken.kind) {
            case 9: {
                senAST = new PosCa();
                this.takeIt();
                break;
            }
            case 8: {
                senAST = new PosAv();
                this.takeIt();
                break;
            }
            case 10: {
                senAST = new HayFlorEnLaEsquina();
                this.takeIt();
                break;
            }
            case 12: {
                senAST = new HayPapelEnLaEsquina();
                this.takeIt();
                break;
            }
            case 13: {
                senAST = new HayPapelEnLaBolsa();
                this.takeIt();
                break;
            }
            case 11: {
                senAST = new HayFlorEnLaBolsa();
                this.takeIt();
                break;
            }
            case 82: {
                senAST = new CantidadFloresBolsa();
                this.takeIt();
                break;
            }
            case 83: {
                senAST = new CantidadPapelesBolsa();
                this.takeIt();
                break;
            }
            default: {
                throw new Exception("Se esperaba una variable del sistema");
            }
        }
        return senAST;
    }

    public Seleccion parseSi(final DeclaracionVariable DV) throws Exception {
        Expresion E = null;
        ArrayList<Sentencia> S1 = null;
        ArrayList<Sentencia> S2 = null;
        this.takeIt();
        E = this.parseExpresion(DV);
        this.take((byte) 42);
        S1 = this.parseSecuenciaDeSentencias(DV);
        this.take((byte) 43);
        if (this.currentToken.kind == 44) {
            this.takeIt();
            this.take((byte) 42);
            S2 = this.parseSecuenciaDeSentencias(DV);
            this.take((byte) 43);
        }
        return new Seleccion(E, S1, S2, DV);
    }

    private IteradorIncondicional parseRepetir(final DeclaracionVariable DV) throws Exception {
        Expresion E = null;
        ArrayList<Sentencia> S = null;
        this.takeIt();
        E = this.parseExpresion(DV);
        this.take((byte) 42);
        S = this.parseSecuenciaDeSentencias(DV);
        this.take((byte) 43);
        return new IteradorIncondicional(E, S, DV);
    }

    private IteradorCondicional parseMientras(final DeclaracionVariable DV) throws Exception {
        Expresion E = null;
        ArrayList<Sentencia> S1 = null;
        this.takeIt();
        E = this.parseExpresion(DV);
        this.take((byte) 42);
        S1 = this.parseSecuenciaDeSentencias(DV);
        this.take((byte) 43);
        return new IteradorCondicional(E, S1, DV);
    }

    private Sentencia parseSentenciaCompuesta(final DeclaracionVariable DV) throws Exception {
        Sentencia senAST = null;
        switch (this.currentToken.kind) {
            case 34: {
                senAST = this.parseSi(DV);
                break;
            }
            case 36: {
                senAST = this.parseRepetir(DV);
                break;
            }
            case 37: {
                senAST = this.parseMientras(DV);
                break;
            }
            default: {
                throw new Exception("Default en parse Sentencia Compuesta");
            }
        }
        return senAST;
    }

    private Sentencia parseSentencia(final DeclaracionVariable DV) throws Exception {
        Sentencia senAST = null;
        switch (this.currentToken.kind) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 67:
            case 68:
            case 75:
            case 76:
            case 77:
            case 79:
            case 84: {
                senAST = this.parsePrimitiva(DV);
                break;
            }
            case 0:
            case 14:
            case 15: {
                senAST = this.parseSentenciaSimple(DV);
                break;
            }
            case 34:
            case 36:
            case 37: {
                senAST = this.parseSentenciaCompuesta(DV);
                break;
            }
            case 18:
            case 43: {
                break;
            }
            default: {
                throw new Exception(
                        "Error de sentencia " + this.currentToken.spelling + "(" + this.currentToken.kind + ")");
            }
        }
        return senAST;
    }

    private DeclaracionVariable parseVariables(final DeclaracionRobots robAST) throws Exception {
        final ArrayList<Variable> varAST = new ArrayList<Variable>();
        String nombre = "pepe";
        String nombreTipoRobot = "";
        byte tipo = 2;
        this.takeIt();
        this.take((byte) 42);
        do {
            final ArrayList<String> lista = new ArrayList<String>();
            while (this.currentToken.kind == 0) {
                if (this.currentToken.kind == 0) {
                    nombre = this.currentToken.spelling;
                    this.takeIt();
                    lista.add(nombre);
                }
                if (this.currentToken.kind == 24) {
                    this.takeIt();
                }
            }
            this.take((byte) 28);
            switch (this.currentToken.kind) {
                case 19: {
                    tipo = this.currentToken.kind;
                    this.takeIt();
                    break;
                }
                case 20: {
                    tipo = this.currentToken.kind;
                    this.takeIt();
                    break;
                }
                default: {
                    if (robAST.estaRobot(this.currentToken.spelling)) {
                        tipo = 66;
                        nombreTipoRobot = this.currentToken.spelling;
                        this.takeIt();
                        break;
                    }
                    throw new Exception("Error de tipo en declaraci\u00f3n de variables");
                }
            }
            for (final String nombre2 : lista) {
                Variable var;
                if (tipo != 66) {
                    var = new Variable(new Identificador(nombre2), new Tipo(tipo), null, robAST, null);
                } else {
                    var = new Variable(new Identificador(nombre2), new Tipo(tipo), null, robAST, nombreTipoRobot);
                    this.city.addRobot(nombre2);
                }
                if (this.estaVariable(varAST, var)) {
                    throw new Exception("La Variable " + nombre2 + " ya existe");
                }
                varAST.add(var);
            }
        } while (this.currentToken.kind != 42 && this.currentToken.kind != 43);
        this.take((byte) 43);
        return new DeclaracionVariable(varAST);
    }

    public boolean estaVariable(final ArrayList<Variable> varAST, final Variable var) {
        for (int x = varAST.size(), i = 0; i < x; ++i) {
            if (varAST.get(i).getI().toString().equals(var.getI().toString())) {
                return true;
            }
        }
        return false;
    }

    private Cuerpo parseCuerpo(final DeclaracionVariable varAST) throws Exception {
        Cuerpo cueAST = null;
        ArrayList<Sentencia> senAST = null;
        this.take((byte) 16);
        this.take((byte) 42);
        senAST = this.parseSecuenciaDeSentencias(varAST);
        cueAST = new Cuerpo(senAST, varAST);
        this.take((byte) 43);
        this.take((byte) 18);
        return cueAST;
    }

    private Cuerpo parseCuerpoRobot(final DeclaracionVariable varAST, final DeclaracionProcesos procAST)
            throws Exception {
        Cuerpo cueAST = null;
        final ArrayList<Sentencia> senAST = new ArrayList<Sentencia>();
        this.take((byte) 16);
        this.take((byte) 42);
        senAST.addAll(this.parseSecuenciaDeSentencias(varAST));
        cueAST = new Cuerpo(senAST, varAST);
        this.take((byte) 43);
        this.take((byte) 18);
        return cueAST;
    }

    public DeclaracionRobots parseRobots(final DeclaracionProcesos procAST) throws Exception {
        final DeclaracionRobots robAST = null;
        final ArrayList<RobotAST> robArray = new ArrayList<RobotAST>();
        RobotAST aRobAST = null;
        this.takeIt();
        this.take((byte) 42);
        do {
            aRobAST = this.parseDeclaracionDeUnRobot(procAST);
            robArray.add(aRobAST);
        } while (this.currentToken.kind != 42 && this.currentToken.kind != 43);
        this.take((byte) 43);
        return new DeclaracionRobots(robArray);
    }

    public RobotAST parseDeclaracionDeUnRobot(final DeclaracionProcesos procAST) throws Exception {
        RobotAST rob = null;
        DeclaracionVariable varAST = new DeclaracionVariable(new ArrayList<Variable>());
        Cuerpo cueAST = null;
        this.take((byte) 66);
        final String nombre = this.currentToken.spelling;
        this.take((byte) 0);
        if (this.currentToken.kind == 17) {
            varAST = this.parseVariables(null);
        }
        cueAST = this.parseCuerpoRobot(varAST, procAST);
        rob = new RobotAST(cueAST, varAST, nombre, procAST);
        rob.setDV(varAST);
        rob.setCuerpo(cueAST);
        return rob;
    }

    private DeclaracionProcesos parseProcesos() throws Exception {
        DeclaracionProcesos procAST = null;
        Proceso aProcAST = null;
        final ArrayList<Proceso> procArray = new ArrayList<Proceso>();
        this.takeIt();
        this.take((byte) 42);
        do {
            aProcAST = this.parseDeclaracionDeUnProceso();
            if (!procArray.isEmpty()) {
                for (final Proceso p : procArray) {
                    if (p.getI().toString().equals(aProcAST.getI().toString())) {
                        throw new Exception(
                                "No se puede declarar procesos con el mismo nombre: " + aProcAST.getI().toString());
                    }
                }
            }
            procArray.add(aProcAST);
        } while (this.currentToken.kind != 42 && this.currentToken.kind != 43);
        this.take((byte) 43);
        procAST = new DeclaracionProcesos(procArray);
        return procAST;
    }

    private Proceso parseDeclaracionDeUnProceso() throws Exception {
        Identificador I = null;
        Cuerpo C = null;
        final ArrayList<ParametroFormal> PF = new ArrayList<ParametroFormal>();
        final DeclaracionProcesos DP = null;
        final DeclaracionVariable DV = new DeclaracionVariable(new ArrayList<Variable>());
        Proceso procAST = null;
        this.take((byte) 39);
        I = new Identificador(this.currentToken.spelling);
        this.take((byte) 0);
        C = this.encabezamientoDeProceso(DV, PF);
        procAST = new Proceso(I, PF, DP, DV, C);
        return procAST;
    }

    private Cuerpo encabezamientoDeProceso(final DeclaracionVariable DV, final ArrayList<ParametroFormal> PF)
            throws Exception {
        Cuerpo cueAST = null;
        Label_0217: {
            if (this.currentToken.kind == 21) {
                this.takeIt();
                while (this.isParametroFormal(this.currentToken)) {
                    final String TA = this.currentToken.spelling;
                    this.takeIt();
                    final String Ident = this.currentToken.spelling;
                    this.take((byte) 0);
                    final Identificador I = new Identificador(Ident);
                    this.take((byte) 28);
                    if (this.currentToken.kind != 19 && this.currentToken.kind != 20) {
                        throw new Exception("Definir tipo para el parametro formal: " + Ident);
                    }
                    final Tipo T = new Tipo(this.currentToken.kind);
                    this.takeIt();
                    final ParametroFormal PFormal = new ParametroFormal(I, T, TA);
                    PF.add(PFormal);
                    if (this.currentToken.kind == 60) {
                        this.takeIt();
                    }
                    if (this.currentToken.kind == 22) {
                        this.take((byte) 22);
                        break Label_0217;
                    }
                }
                throw new Exception("Se esperaba Parametro formal");
            }
        }
        if (this.currentToken.kind == 17) {
            final DeclaracionVariable DV2 = this.parseVariables(null);
            for (int x = DV2.variables.size(), i = 0; i < x; ++i) {
                if (DV.EstaVariable(DV2.variables.get(i).getI().toString())) {
                    throw new Exception("No se puede declarar una variable con el mismo nombre que algun parametros");
                }
                DV.variables.add(DV2.variables.get(i));
            }
        }
        cueAST = this.parseCuerpo(DV);
        return cueAST;
    }

    public Programa parseProgram(final Ciudad city) throws Exception {
        this.city = city;
        Programa proAST = null;
        Identificador ideAST = null;
        DeclaracionVariable varAST = new DeclaracionVariable(new ArrayList<Variable>());
        DeclaracionProcesos procAST = new DeclaracionProcesos(new ArrayList<Proceso>());
        DeclaracionRobots robAST = new DeclaracionRobots(new ArrayList<RobotAST>());
        DeclaracionAreas areasAST = new DeclaracionAreas(new ArrayList<Area>());
        Cuerpo cueAST = null;
        this.take((byte) 29);
        System.out.println("Inicio Parse Programa");
        ideAST = new Identificador(this.currentToken.spelling);
        this.take((byte) 0);
        if (this.currentToken.kind == 38) {
            procAST = this.parseProcesos();
        }
        System.out.println("Inicio Parse Procesos");
        if (this.currentToken.kind == 69) {
            areasAST = this.parseAreas();
        }
        System.out.println("Fin Parse Areas");
        if (this.currentToken.kind == 65) {
            robAST = this.parseRobots(procAST);
        }
        System.out.println("Fin Parse Robots");
        if (this.currentToken.kind == 17) {
            varAST = this.parseVariables(robAST);
        }
        System.out.println("Fin Parse Variables");
        cueAST = this.parseCuerpoPrograma(varAST, robAST);
        System.out.println("Pase Parse Cuerpo");
        proAST = new Programa(ideAST, procAST, varAST, robAST, this.city, cueAST, areasAST);
        return proAST;
    }

    public DeclaracionAreas parseAreas() throws Exception {
        this.takeIt();
        this.take((byte) 42);
        while (this.currentToken.kind == 0) {
            final ArrayList<Expresion> E = new ArrayList<Expresion>();
            final String cadena = this.currentToken.spelling;
            this.takeIt();
            this.take((byte) 28);
            final String tipoArea = this.currentToken.spelling;
            this.takeIt();
            this.take((byte) 21);
            E.add(this.parseExpresion(null));
            this.take((byte) 24);
            E.add(this.parseExpresion(null));
            this.take((byte) 24);
            E.add(this.parseExpresion(null));
            this.take((byte) 24);
            E.add(this.parseExpresion(null));
            this.take((byte) 22);
            final int x1 = Integer.valueOf(E.get(0).getValue(null));
            final int x2 = Integer.valueOf(E.get(1).getValue(null));
            final int x3 = Integer.valueOf(E.get(2).getValue(null));
            final int x4 = Integer.valueOf(E.get(3).getValue(null));
            if (x3 < x1 || x4 < x2 || x1 < 1 || x1 > 100 || x2 < 1 || x1 > 100 || x3 < 1 || x1 > 100 || x4 < 1
                    || x1 > 100) {
                throw new Exception("valores no validos para el \u00e1rea");
            }
            final String s = tipoArea;
            Area area = null;
            switch (s) {
                case "AreaC": {
                    area = new AreaC(x1, x2, x3, x4, cadena);
                    break;
                }
                case "AreaP": {
                    area = new AreaP(x1, x2, x3, x4, cadena);
                    break;
                }
                case "AreaPC": {
                    area = new AreaPC(x1, x2, x3, x4, cadena);
                    break;
                }
                default: {
                    throw new Exception("Se esperaba un tipo de \u00e1rea");
                }
            }
            for (final Area a : this.city.areas) {
                if (a.getName().equals(cadena)) {
                    throw new Exception("No se puede declarar dos areas con el mismo nombre");
                }
                if (x2 <= a.getCa2() && x2 >= a.getCa1() && x1 <= a.getAv2() && x1 >= a.getAv1()) {
                    throw new Exception("No se puede declarar el \u00e1rea: " + cadena
                            + ", al menos una calle coincide con el \u00e1rea: " + a.getName());
                }
            }
            this.city.areas.add(area);
        }
        this.take((byte) 43);
        return new DeclaracionAreas(this.city.areas);
    }

    public Programa parse() throws Exception {
        Programa prgAST = null;
        prgAST = this.parseProgram(this.city);
        return prgAST;
    }

    private boolean esUnario(final byte kind) {
        return kind == 49;
    }

    private Cuerpo parseCuerpoPrograma(final DeclaracionVariable varAST, final DeclaracionRobots robAST)
            throws Exception {
        final ArrayList<Sentencia> senAST = new ArrayList<Sentencia>();
        this.take((byte) 16);
        this.take((byte) 42);
        while (this.currentToken.kind == 73) {
            this.takeIt();
            this.take((byte) 21);
            final Robot robo = this.city.getRobotByNombre(this.currentToken.spelling);
            this.takeIt();
            this.take((byte) 24);
            final Area area = this.city.getAreaByNombre(this.currentToken.spelling);
            this.takeIt();
            this.take((byte) 22);
            final Sentencia sen = new AsignarArea(robo, area);
            senAST.add(sen);
        }
        while (this.currentToken.kind == 1) {
            final ArrayList<Expresion> E = new ArrayList<Expresion>();
            this.takeIt();
            this.take((byte) 21);
            final Identificador nomRobot = new Identificador(this.currentToken.spelling);
            this.take((byte) 0);
            this.take((byte) 24);
            E.add(this.parseExpresion(varAST));
            this.take((byte) 24);
            E.add(this.parseExpresion(varAST));
            this.take((byte) 22);
            final Sentencia sen = new Iniciar(nomRobot, Integer.parseInt(E.get(0).getValue(varAST)),
                    Integer.parseInt(E.get(1).getValue(varAST)), robAST, varAST);
            senAST.add(sen);
        }
        this.take((byte) 43);
        this.take((byte) 18);
        return new Cuerpo(senAST, varAST);
    }
}
