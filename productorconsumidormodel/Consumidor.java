package productorconsumidormodel;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumidor implements Runnable {

    private Producto producto;
    private ContadorSincronizado CCI;
    private ContadorSincronizado CCF;
    private LabParameters labParam;

    public Consumidor(Producto producto, ContadorSincronizado CCI, ContadorSincronizado CCF, LabParameters labParam) {
        this.labParam = labParam;
        this.producto = producto;
        this.CCI = CCI;
        this.CCF = CCF;

    }

    @Override
    public void run() {

        boolean randTime = this.labParam.consumidoresCB;
        int valorRand = this.labParam.consumidoresS;
        try {
            this.CCI.inc_syncro();
        } catch (InterruptedException ex) {
            Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < this.labParam.cantidadItemsxConsumidor; i++) {
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
                    Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Random rand = new Random();
                int numeroAleatorio = rand.nextInt(valorRand);
                try {
                    Thread.sleep(numeroAleatorio);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                this.producto.consumir(this.labParam.protectCriticalRegions, this.labParam.preventNegativeStocks);
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            this.CCF.inc_syncro();
        } catch (InterruptedException ex) {
            Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
