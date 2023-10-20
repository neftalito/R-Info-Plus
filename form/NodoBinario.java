
package form;

public class NodoBinario<T> {
    private T dato;
    private NodoBinario<T> hijoIzquierdo;
    private NodoBinario<T> hijoDerecho;

    public NodoBinario(final T dato) {
        this.dato = dato;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

    public T getDato() {
        return this.dato;
    }

    public NodoBinario<T> getHijoIzquierdo() {
        return this.hijoIzquierdo;
    }

    public NodoBinario<T> getHijoDerecho() {
        return this.hijoDerecho;
    }

    public void setDato(final T dato) {
        this.dato = dato;
    }

    public void setHijoIzquierdo(final NodoBinario<T> hijoIzq) {
        this.hijoIzquierdo = hijoIzq;
    }

    public void setHijoDerecho(final NodoBinario<T> hijoDer) {
        this.hijoDerecho = hijoDer;
    }
}
