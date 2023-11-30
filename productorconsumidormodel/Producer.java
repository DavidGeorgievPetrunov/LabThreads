package productorconsumidormodel;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer implements Runnable {

    //Atributos
    public Product producto;
    private ProtectedCounter CPI;
    private ProtectedCounter CPF;
    private LabParameters labParam;

    //Clase
    public Producer(Product producto, ProtectedCounter CPI, ProtectedCounter CPF, LabParameters labParam) {
        this.labParam = labParam;
        this.producto = producto;
        this.CPI = CPI;
        this.CPF = CPF;
    }

    //Metodos Override
    @Override
    public void run() {

        boolean randTime = this.labParam.productoresCB;
        int valorRand = this.labParam.productoresS;
        try {
            if (this.labParam.protectCriticalRegions == true) {
                this.CPI.inc_syncro();
            } else {
                this.CPI.inc();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Random rand = new Random();
        for (int i = 0; i < this.labParam.cantidadItemsxProductor; i++) {
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
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                int numeroAleatorio = rand.nextInt(valorRand);
                try {
                    Thread.sleep(numeroAleatorio);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                this.producto.producir(this.labParam.protectCriticalRegions, this.labParam.preventNegativeStocks);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            if (this.labParam.protectCriticalRegions == true) {
                this.CPF.inc_syncro();
            } else {
                this.CPF.inc();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
