package productorconsumidormodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TJTLModel {

    // Atributos
    java.util.List<LabResults> labResArray;
    java.util.List<LabParameters> labParamArray;
    LabResults labRes;
    LabParameters labParam;
    TJTLController controller;
    Product producto;
    ProtectedCounter CCI;
    ProtectedCounter CCF;
    ProtectedCounter CPI;
    ProtectedCounter CPF;
    private long tiempoStartT;
    private long tiempoCreacionT;
    private long start;
    private ArrayList<Thread> threads;
    private boolean isProcessing = false;
    private long tiempoConsumidorFinal = 0;
    private long tiempoProductorFinal = 0;

    // Constructor
    public TJTLModel(Product producto, ProtectedCounter CCI, ProtectedCounter CCF, ProtectedCounter CPI, ProtectedCounter CPF, TJTLController controller) {
        this.labParamArray = new ArrayList<>();
        this.labResArray = new ArrayList<>();
        this.labRes = new LabResults();
        this.labParam = new LabParameters();
        this.controller = controller;
        this.producto = producto;
        this.CCI = CCI;
        this.CCF = CCF;
        this.CPI = CPI;
        this.CPF = CPF;
    }

    // Getters y Setters
    public LabParameters getConfig() {
        LabParameters oLabParam = new LabParameters();
        oLabParam.cantidadConsumidoresTF = this.labParam.cantidadConsumidoresTF;
        oLabParam.cantidadItemsxConsumidor = this.labParam.cantidadItemsxConsumidor;
        oLabParam.cantidadItemsxProductor = this.labParam.cantidadItemsxProductor;
        oLabParam.cantidadProductoresTF = this.labParam.cantidadProductoresTF;
        oLabParam.consumidoresS = this.labParam.consumidoresS;
        oLabParam.productoresS = this.labParam.productoresS;
        oLabParam.preventNegativeStocks = this.labParam.preventNegativeStocks;
        oLabParam.consumidoresCB = this.labParam.consumidoresCB;
        oLabParam.productoresCB = this.labParam.productoresCB;
        oLabParam.protectCriticalRegions = this.labParam.protectCriticalRegions;
        return oLabParam;

    }

    public LabParameters getLabParam() {
        return labParam;
    }

    public List<LabParameters> getLabParamArray() {
        return labParamArray;
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

    public List<LabResults> getLabResArray() {
        return labResArray;
    }

    // Metodos Publico
    public void applyConfig() {
        System.out.println(this.controller.view.cPanel.preventNegativeStocks.isSelected());
        this.controller.view.cPanel.applyConfig();
        this.controller.view.gConf.applyConfig();
    }

    private void crearNuevaFila() {
        DefaultTableModel newModelCC = (DefaultTableModel) this.controller.view.rView.contadorConsumidores.getModel();
        newModelCC.addRow(new Object[]{this.controller.view.gConf.nombreProducto.getText(), "0"});
        this.controller.view.rView.contadorConsumidores.repaint();

        DefaultTableModel newModelCP = (DefaultTableModel) this.controller.view.rView.contadorProductores.getModel();
        newModelCP.addRow(new Object[]{this.controller.view.gConf.nombreProducto.getText(), "0"});
        this.controller.view.rView.contadorProductores.repaint();
    }

    public void defaultResults() {
        this.labRes.consumidoresArrancados = 0;
        this.labRes.consumidoresFinalizados = 0;
        this.labRes.productoresArrancados = 0;
        this.labRes.productoresFinalizados = 0;
        this.labRes.tiempoArrancarThreads = 0;
        this.labRes.tiempoCrearThreads = 0;
        this.labRes.consumidoresTiempoFinal = 0;
        this.labRes.productoresTiempoFinal = 0;
        this.labRes.contadorConsumidores = 0;
        this.labRes.contadorProductores = 0;
        this.producto.setConsumedQuantity(0);
        this.producto.setProducedQuantity(0);
        this.producto.setValor(0);
        this.CCI.setValor(0);
        this.CCF.setValor(0);
        this.CPI.setValor(0);
        this.CPF.setValor(0);
    }

    public void play() throws InterruptedException {
        if (isProcessing == false) {
            isProcessing = true;
            this.start = System.currentTimeMillis();

            this.defaultResults();
            this.crearNuevaFila();
            this.labParamArray.add(this.controller.getRowCount(), this.getConfig());

            Thread processingThread = new Thread(() -> {
                threads = new ArrayList<>();

                for (int i = 0; i < this.labParam.cantidadProductoresTF; i++) {
                    Producer productor = new Producer(this.producto, CPI, CPF, this.labParam);
                    Thread hilo = new Thread(productor);
                    hilo.start();
                    threads.add(hilo);
                }

                for (int i = 0; i < this.labParam.cantidadConsumidoresTF; i++) {
                    Consumer consumidor = new Consumer(this.producto, CCI, CCF, this.labParam);
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
                        if (this.CCF.getValor() == this.CCI.getValor()) {
                            this.tiempoConsumidorFinal = System.currentTimeMillis() - this.start;
                        } else if (this.CPF.getValor() == this.CPI.getValor()) {
                            this.tiempoProductorFinal = System.currentTimeMillis() - this.start;
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

    public void resetConfig() {

        this.labParam.preventNegativeStocks = false;
        this.labParam.protectCriticalRegions = true;

        this.labParam.productoresS = 100;
        this.labParam.consumidoresS = 100;
        this.labParam.cantidadProductoresTF = 200;
        this.labParam.cantidadConsumidoresTF = 400;
        this.labParam.cantidadItemsxProductor = 200;
        this.labParam.cantidadItemsxConsumidor = 200;
        this.labParam.productoresCB = false;
        this.labParam.consumidoresCB = false;
    }

}
