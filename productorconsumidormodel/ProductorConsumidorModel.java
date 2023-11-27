package productorconsumidormodel;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class ProductorConsumidorModel {

    ProductorConsumidorController controller;
    Producto producto;
    ContadorSincronizado CCI;
    ContadorSincronizado CCF;
    ContadorSincronizado CPI;
    ContadorSincronizado CPF;
    private long tiempoStartT;
    private long tiempoCreacionT;
    private long start;
    private Thread processingThread;
    private ArrayList<Thread> threads;
    private boolean isProcessing = false;
    private long tiempoConsumidorFinal = 0;
    private long tiempoProductorFinal = 0;

    public ProductorConsumidorModel(Producto producto, ContadorSincronizado CCI, ContadorSincronizado CCF, ContadorSincronizado CPI, ContadorSincronizado CPF, ProductorConsumidorController controller) {
        this.controller = controller;
        this.producto = producto;
        this.CCI = CCI;
        this.CCF = CCF;
        this.CPI = CPI;
        this.CPF = CPF;
    }

    public void play() throws InterruptedException {
        if (isProcessing == false) {
            isProcessing = true;
            this.start = System.currentTimeMillis();

            this.defaultResults();
            this.crearNuevaFila();

            Thread processingThread = new Thread(() -> {
                threads = new ArrayList<>();

                for (int i = 0; i < this.controller.labParam.cantidadProductoresTF; i++) {
                    Productor productor = new Productor(this.producto, CPI, CPF, this.controller.labParam);
                    Thread hilo = new Thread(productor);
                    hilo.start();
                    threads.add(hilo);
                }

                for (int i = 0; i < this.controller.labParam.cantidadConsumidoresTF; i++) {
                    Consumidor consumidor = new Consumidor(this.producto, CCI, CCF, this.controller.labParam);
                    Thread hilo = new Thread(consumidor);
                    this.tiempoCreacionT = System.currentTimeMillis();
                    hilo.start();
                    this.tiempoStartT = System.currentTimeMillis();
                    threads.add(hilo);
                }

                this.tiempoCreacionT = this.tiempoCreacionT - this.start;
                this.tiempoStartT = this.tiempoStartT - this.start;

                for (Thread hilo : threads) {
                    try {
                        if(this.CCF.getValor() == this.CCI.getValor()){
                            this.tiempoConsumidorFinal = System.currentTimeMillis()-this.start;
                        } else if(this.CPF.getValor() == this.CPI.getValor()){
                            this.tiempoProductorFinal = System.currentTimeMillis()-this.start;
                        }
                        hilo.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                };
                isProcessing = false;
            });
            processingThread.start();
        }
    }

    public int getValor() {
        return this.producto.getValor();
    }

    public LabResults getResults() {
        LabResults newResults = new LabResults();
        newResults.consumidoresArrancados = this.CCI.getValor();
        newResults.consumidoresFinalizados = this.CCF.getValor();
        newResults.productoresArrancados = this.CPI.getValor();
        newResults.productoresFinalizados = this.CPF.getValor();
        newResults.tiempoArrancarThreads = this.tiempoStartT;
        newResults.tiempoCrearThreads = tiempoCreacionT;
        newResults.consumidoresTiempoFinal = this.tiempoConsumidorFinal;
        newResults.productoresTiempoFinal = this.tiempoProductorFinal;
        newResults.contadorConsumidores = this.producto.getConsumedQuantity();
        newResults.contadorProductores = this.producto.getProducedQuantity();

        return newResults;
    }

    public void defaultResults() {
        this.controller.labRes.consumidoresArrancados = 0;
        this.controller.labRes.consumidoresFinalizados = 0;
        this.controller.labRes.productoresArrancados = 0;
        this.controller.labRes.productoresFinalizados = 0;
        this.controller.labRes.tiempoArrancarThreads = 0;
        this.controller.labRes.tiempoCrearThreads = 0;
        this.controller.labRes.consumidoresTiempoFinal = 0;
        this.controller.labRes.productoresTiempoFinal = 0;
        this.controller.labRes.contadorConsumidores = 0;
        this.controller.labRes.contadorProductores = 0;
        this.producto.setConsumedQuantity(0);
        this.producto.setProducedQuantity(0);
        this.producto.setValor(0);
        this.CCI.setValor(0);
        this.CCF.setValor(0);
        this.CPI.setValor(0);
        this.CPF.setValor(0);
    }

    private void crearNuevaFila() {
        DefaultTableModel newModelCC = (DefaultTableModel) this.controller.view.rView.contadorConsumidores.getModel();
        newModelCC.addRow(new Object[]{this.controller.view.gConf.nombreProducto.getText(), "0"});
        this.controller.view.rView.contadorConsumidores.repaint();

        DefaultTableModel newModelCP = (DefaultTableModel) this.controller.view.rView.contadorProductores.getModel();
        newModelCP.addRow(new Object[]{this.controller.view.gConf.nombreProducto.getText(), "0"});
        this.controller.view.rView.contadorProductores.repaint();
    }
}
