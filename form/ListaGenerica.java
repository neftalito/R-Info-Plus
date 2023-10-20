
package form;

public abstract class ListaGenerica<T> {
    protected int tamanio;

    public abstract void comenzar();

    public abstract void proximo();

    public abstract boolean fin();

    public abstract T elemento();

    public abstract T elemento(final int p0);

    public abstract boolean agregar(final T p0);

    public abstract boolean agregar(final T p0, final int p1);

    public abstract boolean eliminar();

    public abstract boolean eliminar(final int p0);

    public abstract boolean esVacia();

    public abstract boolean incluye(final T p0);

    public abstract int tamanio();
}
