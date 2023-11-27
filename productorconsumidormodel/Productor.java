package productorconsumidormodel;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Productor implements Runnable {

    public Producto producto;
    private ContadorSincronizado CPI;
    private ContadorSincronizado CPF;
    private LabParameters labParam;

    public Productor(Producto producto, ContadorSincronizado CPI, ContadorSincronizado CPF, LabParameters labParam) {
        this.labParam = labParam;
        this.producto = producto;
        this.CPI = CPI;
        this.CPF = CPF;
    }

    @Override
    public void run() {

        boolean randTime = this.labParam.productoresCB;
        int valorRand = this.labParam.productoresS;
        try {
            this.CPI.inc_syncro();
        } catch (InterruptedException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
        }
        Random rand = new Random();
        for (int i = 0; i < this.labParam.cantidadItemsxProductor; i++) {
            while (this.labParam.stop) {
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (randTime == true) {
                try {
                    Thread.sleep(valorRand);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                int numeroAleatorio = rand.nextInt(valorRand);
                try {
                    Thread.sleep(numeroAleatorio);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                this.producto.producir(this.labParam.protectCriticalRegions, this.labParam.preventNegativeStocks);
            } catch (InterruptedException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            this.CPF.inc_syncro();
        } catch (InterruptedException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
