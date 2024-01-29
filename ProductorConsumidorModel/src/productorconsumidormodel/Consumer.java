package productorconsumidormodel;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {

    // Atributos
    
    private Product producto;
    private ProtectedCounter CCI;
    private ProtectedCounter CCF;
    private LabParameters labParam;

    //Constructor
    
    public Consumer(Product producto, ProtectedCounter CCI, ProtectedCounter CCF, LabParameters labParam) {
        this.labParam = labParam;
        this.producto = producto;
        this.CCI = CCI;
        this.CCF = CCF;

    }

    // Metodos Overrided
    @Override
    public void run() {

        boolean randTime = this.labParam.consumidoresCB;
        int valorRand = this.labParam.consumidoresS;
        try {
            if (this.labParam.protectCriticalRegions == true) {
                this.CCI.inc_syncro();
            } else {
                this.CCI.inc();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < this.labParam.cantidadItemsxConsumidor; i++) {
            while (this.labParam.stop) {
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (randTime == true) {
                try {
                    Thread.sleep(valorRand);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Random rand = new Random();
                int numeroAleatorio = rand.nextInt(valorRand);
                try {
                    Thread.sleep(numeroAleatorio);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                this.producto.consumir(this.labParam.protectCriticalRegions, this.labParam.preventNegativeStocks);
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            if (this.labParam.protectCriticalRegions == true) {
                this.CCF.inc_syncro();
            } else {
                this.CCF.inc();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
