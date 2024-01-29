package productorconsumidormodel;

public class Product extends ProtectedCounter {

    // Atributos
    private int valorMin;
    private int producedQuantity;
    private int consumedQuantity;

    // Constructor
    public Product() {
        super();
        this.valorMin = 0;

    }

    // Setters y Getters
    public int getConsumedQuantity() {
        return consumedQuantity;
    }

    public int getProducedQuantity() {
        return producedQuantity;
    }

    public void setConsumedQuantity(int consumedQuantity) {
        this.consumedQuantity = consumedQuantity;
    }

    public void setProducedQuantity(int producedQuantity) {
        this.producedQuantity = producedQuantity;
    }

    // Metodos publicos 
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
        }
    }

    public synchronized void inc_syncro(boolean preventNegative) throws InterruptedException {
        if (preventNegative == false) {
            this.setProducedQuantity(this.getProducedQuantity() + 1);
            this.setValor(this.getValor() + 1);
        } else {
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
            try {
                while (this.getValor() <= this.valorMin) {
                    wait();
                }
                this.setValor(this.getValor() - 1);
                this.setConsumedQuantity(getConsumedQuantity() - 1);
                notifyAll();
            } catch (InterruptedException e) {
                System.out.println("Exception value became negative: " + e);
            }
        }
    }

    public void inc(boolean preventNegative) throws InterruptedException {
        if (preventNegative == false) {
            this.setProducedQuantity(this.getProducedQuantity() + 1);
            this.setValor(this.getValor() + 1);
        } else {
            this.setValor(this.getValor() + 1);
            this.setProducedQuantity(this.getProducedQuantity() + 1);
            try {
                notifyAll();
            } catch (Exception e) {
                System.out.println("Exception notify: " + e);
            }
        }

    }

}
