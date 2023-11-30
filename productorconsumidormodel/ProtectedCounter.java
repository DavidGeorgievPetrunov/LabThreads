package productorconsumidormodel;

public class ProtectedCounter {

    //Atributos
    private int valor;

    //Clase
    public ProtectedCounter() {
        this.valor = 0;
    }

    // Metodos Publicos
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
