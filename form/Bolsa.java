
package form;

public class Bolsa {
    private int papeles;
    private int flores;
    private boolean obstaculo;
    private boolean ocupado;

    public Bolsa() {
        this.papeles = 0;
        this.flores = 0;
        this.obstaculo = false;
        this.ocupado = false;
    }

    public int getPapeles() {
        return this.papeles;
    }

    public void setPapeles(final int algo) {
        this.papeles = algo;
    }

    public int getFlores() {
        return this.flores;
    }

    public void setFlores(final int algo) {
        this.flores = algo;
    }

    public boolean getObstaculo() {
        return this.obstaculo;
    }

    public void setObstaculo(final boolean algo) {
        this.obstaculo = algo;
    }

    public boolean isOcupado() {
        return this.ocupado;
    }

    public void ocupar() {
        this.ocupado = true;
    }

    public void desocupar() {
        this.ocupado = false;
    }
}
