
package arbol;

import form.Token;

public class Tipo extends AST {
    public byte tipo;

    public Tipo(final byte tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return Token.spellings[this.tipo];
    }

    public byte getTipo() {
        return this.tipo;
    }

    public void setTipo(final byte tipo) {
        this.tipo = tipo;
    }
}
