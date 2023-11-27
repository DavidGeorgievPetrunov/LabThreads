package productorconsumidormodel;

public class ContadorSincronizado {

    private int valor;

    public ContadorSincronizado() {
        this.valor = 0;
    }

    public void inc(){
        this.valor++;
    }
    
    public void dec(){
        this.valor--;
    }
    
    public synchronized void inc_syncro() throws InterruptedException {
        this.valor++;
    }

    public synchronized void dec_syncro() throws InterruptedException {
        this.valor--;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor){
        this.valor = valor;
    }
}
