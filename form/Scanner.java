
package form;

class Scanner {
    public char currentChar;
    public byte currentKind;
    public StringBuffer currentSpelling;
    String cadena;
    String parametro;
    int pos;
    int indent;
    int dedent;
    int numeroEspaciosLineaActual;
    int numeroEspaciosLineaAnterior;
    int fil;
    int col;
    boolean contarEspacios;

    Scanner(final String cadena) throws Exception {
        this.cadena = cadena + "\n";
        this.pos = 0;
        this.indent = 0;
        this.dedent = 0;
        this.numeroEspaciosLineaActual = 0;
        this.numeroEspaciosLineaAnterior = 0;
        this.fil = 1;
        this.col = 1;
        this.contarEspacios = true;
        try {
            this.nextchar();
        } catch (Exception e) {
            this.reportScanError(this.fil + " " + this.col + "FATAL Error!!!!!!!!!!!!!!!!!");
        }
    }

    private void reportScanError(final String str) throws Exception {
        throw new Exception(str);
    }

    public void nextchar() throws Exception {
        if (this.pos < this.cadena.length()) {
            this.currentChar = this.cadena.charAt(this.pos);
            ++this.pos;
            ++this.col;
            return;
        }
        throw new FinArchivo();
    }

    private void take(final char expectedChar) throws Exception {
        if (this.currentChar == expectedChar) {
            this.currentSpelling.append(this.currentChar);
            this.nextchar();
        } else {
            this.reportScanError("Error");
        }
    }

    public void takeIt() throws Exception {
        this.currentSpelling.append(this.currentChar);
        this.nextchar();
    }

    private boolean isDigit(final char c) {
        return Character.isDigit(c);
    }

    public boolean isLetter(final char c) {
        return Character.isLetter(c);
    }

    public byte scanToken() throws Exception {
        if (Token.spellings[61].equals(String.valueOf(this.currentChar))) {
            do {
                this.takeIt();
            } while (!Token.spellings[62].equals(String.valueOf(this.currentChar)));
            this.takeIt();
            return 74;
        }
        if (this.currentChar == '\'') {
            this.takeIt();
            return 78;
        }
        if (this.currentChar == '\"') {
            this.takeIt();
            return 80;
        }
        if (this.isLetter(this.currentChar)) {
            this.takeIt();
            while (this.isLetter(this.currentChar) || this.isDigit(this.currentChar) || this.isPunto(this.currentChar)
                    || this.isGuion(this.currentChar)) {
                this.takeIt();
            }
            return 0;
        }
        if (this.currentChar == '(') {
            this.takeIt();
            return 21;
        }
        if (this.currentChar == ';') {
            this.takeIt();
            return 60;
        }
        if (this.currentChar == ':') {
            this.takeIt();
            if (this.currentChar == '=') {
                this.takeIt();
                return 31;
            }
            return 28;
        } else {
            if (this.currentChar == ')') {
                this.takeIt();
                return 22;
            }
            if (this.currentChar == ',') {
                this.takeIt();
                return 24;
            }
            if (this.currentChar == '+') {
                this.takeIt();
                return 45;
            }
            if (this.currentChar == '-') {
                this.takeIt();
                return 46;
            }
            if (this.currentChar == '/') {
                this.takeIt();
                return 47;
            }
            if (this.currentChar == '*') {
                this.takeIt();
                return 48;
            }
            if (this.currentChar == '~') {
                this.takeIt();
                return 49;
            }
            if (this.currentChar == '&') {
                this.takeIt();
                return 50;
            }
            if (this.currentChar == '|') {
                this.takeIt();
                return 51;
            }
            if (this.currentChar == '%') {
                this.takeIt();
                return 81;
            }
            if (this.currentChar == '=') {
                this.takeIt();
                return 30;
            }
            if (this.currentChar == '<') {
                this.takeIt();
                if (this.currentChar == '=') {
                    this.takeIt();
                    return 56;
                }
                if (this.currentChar == '>') {
                    this.takeIt();
                    return 54;
                }
                return 52;
            } else if (this.currentChar == '>') {
                this.takeIt();
                if (this.currentChar == '=') {
                    this.takeIt();
                    return 55;
                }
                return 53;
            } else {
                if (this.isDigit(this.currentChar)) {
                    this.takeIt();
                    while (this.isDigit(this.currentChar)) {
                        this.takeIt();
                    }
                    return 23;
                }
                if (this.currentChar == '\"') {
                    this.nextchar();
                    while (this.currentChar != '\"') {
                        this.takeIt();
                    }
                    this.nextchar();
                    return 80;
                }
                System.out.println("ESPACIOS LIBRES");
                this.currentSpelling.append("ERROR scanToken");
                throw new Exception("ERROR scanToken");
            }
        }
    }

    public Token scan() throws Exception {
        try {
            if (this.currentChar == '\r') {
                this.nextchar();
            }
            if (this.currentChar == '\n') {
                this.numeroEspaciosLineaAnterior = this.numeroEspaciosLineaActual;
                this.col = 1;
                while (true) {
                    this.nextchar();
                    ++this.fil;
                    if (this.currentChar != '\n') {
                        this.numeroEspaciosLineaActual = 0;
                        while (this.currentChar == ' ') {
                            this.takeIt();
                            ++this.numeroEspaciosLineaActual;
                        }
                        if (this.currentChar != '\n') {
                            break;
                        }
                        continue;
                    }
                }
                if (Token.spellings[61].equals(String.valueOf(this.currentChar))) {
                    this.numeroEspaciosLineaActual = this.numeroEspaciosLineaAnterior;
                } else if (this.numeroEspaciosLineaActual % 2 == 0) {
                    if (this.numeroEspaciosLineaActual != this.numeroEspaciosLineaAnterior) {
                        if (this.numeroEspaciosLineaActual > this.numeroEspaciosLineaAnterior) {
                            this.indent = (this.numeroEspaciosLineaActual - this.numeroEspaciosLineaAnterior) / 2;
                        } else {
                            this.dedent = (this.numeroEspaciosLineaAnterior - this.numeroEspaciosLineaActual) / 2;
                        }
                    }
                } else {
                    this.reportScanError(
                            "Error de indentaci\u00f3n! cantidad de espacios : " + this.numeroEspaciosLineaActual
                                    + ", linumeroEspaciosLineaAnterior anterior : " + this.numeroEspaciosLineaAnterior);
                }
            }
            if (this.indent > 0) {
                --this.indent;
                return new Token((byte) 42, "INDENT");
            }
            if (this.dedent > 0) {
                --this.dedent;
                return new Token((byte) 43, "DEDENT");
            }
            this.currentSpelling = new StringBuffer("");
            this.currentKind = this.scanToken();
            while (this.currentChar == ' ') {
                this.nextchar();
            }
            return new Token(this.currentKind, this.currentSpelling.toString());
        } catch (FinArchivo e) {
            return new Token((byte) 26, "EOT");
        }
    }

    private boolean isPunto(final char cc) {
        return cc == '.';
    }

    private boolean isGuion(final char cc) {
        return cc == '_';
    }

    private boolean isComilla(final char cc) {
        return cc == '\'';
    }

    private boolean isComillaDoble(final char cc) {
        return cc == '"';
    }
}
