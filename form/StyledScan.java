
package form;

import java.util.ArrayList;

class StyledScan {
    Ciudad city;
    String text;
    char currentChar;
    byte currentKind;
    int pos;
    int col;
    StringBuffer currentSpelling;
    private int fil;
    boolean ok;
    boolean comillas;
    ArrayList<StyledToken> currentArea;

    public StyledScan(final String text, final Ciudad city) {
        this.currentArea = new ArrayList<StyledToken>();
        this.city = city;
        this.text = text.replace("\r", "");
        this.pos = 1;
        this.col = 0;
        this.currentChar = this.text.charAt(0);
        this.ok = false;
        this.comillas = false;
    }

    public void nextchar() {
        if (this.pos < this.text.length()) {
            this.currentChar = this.text.charAt(this.pos);
            ++this.pos;
            ++this.col;
        } else {
            this.currentChar = '#';
        }
    }

    public char peakNextChar() {
        if (this.pos + 1 < this.text.length()) {
            return this.text.charAt(this.pos + 1);
        }
        System.out.println("Fin de archivo en el peakNextChar");
        return '#';
    }

    private void take(final char expectedChar) throws Exception {
        if (this.currentChar == expectedChar) {
            this.currentSpelling.append(this.currentChar);
            this.nextchar();
            return;
        }
        throw new Exception("Se esperaba el caracter " + expectedChar);
    }

    public void takeIt() throws Exception {
        if (this.currentChar != '\r') {
            this.currentSpelling.append(this.currentChar);
        } else {
            System.out.println("Era barra r en el takeit");
        }
        this.nextchar();
    }

    public StyledToken scan() throws Exception {
        while (this.currentChar == '\n' || this.currentChar == ' ') {
            if (this.currentChar == '\n') {
            }
            this.nextchar();
        }
        this.currentSpelling = new StringBuffer("");
        final int from = this.pos - 1;
        this.currentKind = this.scanToken();
        final StyledToken myTkn = new StyledToken(this.currentKind, from, this.currentSpelling.toString(), this.ok,
                this.comillas);
        this.currentArea.add(myTkn);
        return myTkn;
    }

    public byte scanToken() throws Exception {
        if (Character.isLetter(this.currentChar)) {
            this.takeIt();
            while (Character.isLetter(this.currentChar) || Character.isDigit(this.currentChar)) {
                this.takeIt();
            }
            return StyledToken.IDENTIFIER;
        }
        if (Character.isDigit(this.currentChar)) {
            this.takeIt();
            while (Character.isDigit(this.currentChar)) {
                this.takeIt();
            }
            return StyledToken.LITERAL;
        }
        byte kind = -1;
        CaracterSwitch: {
            switch (this.currentChar) {
                case '(':
                case ')':
                case ',':
                case '+':
                case '/':
                case '-':
                case '*':
                case '~':
                case '&':
                case '|':
                case '=':
                case ';':
                case '%': {
                    kind = StyledToken.OPERADOR;
                    break;
                }
                case ':':
                case '>': {
                    if (this.peakNextChar() == '=') {
                        this.takeIt();
                        kind = StyledToken.OPERADOR;
                        break;
                    }
                    kind = StyledToken.OPERADOR;
                    break;
                }
                case '{': {
                    kind = StyledToken.COMENTARIO;
                    this.ok = true;
                    break;
                }
                case '\"':
                case '\'': {
                    kind = StyledToken.LITERAL;
                    this.comillas = !this.comillas;
                    break;
                }
                case '}': {
                    kind = StyledToken.COMENTARIO;
                    this.ok = false;
                    break;
                }
                case '<': {
                    switch (this.peakNextChar()) {
                        case '=':
                        case '>': {
                            this.takeIt();
                            kind = StyledToken.OPERADOR;
                            break CaracterSwitch;
                        }
                        default: {
                            kind = StyledToken.OPERADOR;
                            break CaracterSwitch;
                        }
                    }
                }
                case '#': {
                    kind = StyledToken.EOF;
                    break;
                }
            }
        }
        this.takeIt();
        return kind;
    }
}
