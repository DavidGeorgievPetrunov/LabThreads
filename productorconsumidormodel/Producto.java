package productorconsumidormodel;

public class Producto extends ContadorSincronizado {

    private int valorMax;
    private int valorMin;
    private int producedQuantity;
    private int consumedQuantity;

    public Producto() {
        super();
        this.valorMax = 100000;
        this.valorMin = 0;

    }

    public int getProducedQuantity() {
        return producedQuantity;
    }

    public void setProducedQuantity(int producedQuantity) {
        this.producedQuantity = producedQuantity;
    }

    public int getConsumedQuantity() {
        return consumedQuantity;
    }

    public void setConsumedQuantity(int consumedQuantity) {
        this.consumedQuantity = consumedQuantity;
    }

    public void setValorMax(int valorMax) {
        this.valorMax = valorMax;
    }

    public void setValorMin(int valorMin) {
        this.valorMin = valorMin;
    }

    public void producir(boolean sync, boolean canNegative) throws InterruptedException {
        if (sync == true) {
            inc_syncro(canNegative);
        } else {
            inc(canNegative);
        }
    }

    public void consumir(boolean bool, boolean preventNegative) throws InterruptedException {
        if (bool == true) {
            dec_syncro(preventNegative);
        } else {
            dec(preventNegative);
        }
    }

    public synchronized void dec_syncro(boolean preventNegative) throws InterruptedException {
        if (preventNegative == false) {
            this.setConsumedQuantity(getConsumedQuantity() - 1);
            this.setValor(this.getValor() - 1);
        } else {
            while (this.getValor() <= this.valorMin) {
                wait();
            }
            this.setValor(this.getValor() - 1);

            this.setConsumedQuantity(getConsumedQuantity() - 1);
            notifyAll();
        }
    }

    public synchronized void inc_syncro(boolean preventNegative) throws InterruptedException {
        if (preventNegative == false) {
            this.setProducedQuantity(this.getProducedQuantity() + 1);
            this.setValor(this.getValor() + 1);
        } else {
            while (this.getValor() >= this.valorMax) {
                wait();
            }
            this.setValor(this.getValor() + 1);
            this.setProducedQuantity(this.getProducedQuantity() + 1);
            notifyAll();
        }
    }

    public void dec(boolean preventNegative) throws InterruptedException {
        if (preventNegative == false) {
            this.setConsumedQuantity(getConsumedQuantity() - 1);
            this.setValor(this.getValor() - 1);
        } else {
            while (this.getValor() <= this.valorMin) {
                wait();
            }
            this.setValor(this.getValor() - 1);
            this.setConsumedQuantity(getConsumedQuantity() - 1);
            notifyAll();
        }
    }

    public void inc(boolean preventNegative) throws InterruptedException {
        if (preventNegative == false) {
            this.setProducedQuantity(this.getProducedQuantity() + 1);
            this.setValor(this.getValor() + 1);
        } else {
            while (this.getValor() >= this.valorMax) {
                wait();
            }
            this.setValor(this.getValor() + 1);
            this.setProducedQuantity(this.getProducedQuantity() + 1);
            notifyAll();
        }

    }

}
