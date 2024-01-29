package productorconsumidormodel;

import java.util.List;

public class TJTLController {

    // Atributos
    TJTLModel model;
    TJTLVista view;

    private Product producto;

    ProtectedCounter CCI;
    ProtectedCounter CCF;
    ProtectedCounter CPI;
    ProtectedCounter CPF;

    // Constructor
    public TJTLController() {
        this.CCI = new ProtectedCounter();
        this.CCF = new ProtectedCounter();
        this.CPI = new ProtectedCounter();
        this.CPF = new ProtectedCounter();
        this.producto = new Product();
        this.model = new TJTLModel(producto, CCI, CCF, CPI, CPF, this);
        this.view = new TJTLVista(this);
    }

    // Main
    public static void main(String[] args) {
        TJTLController controller = new TJTLController();

        Thread hilo = new Thread(controller.view);
        hilo.start();

    }

    // Getters y Setters
    public LabParameters getLabParam() {
        return this.model.getLabParam();
    }

    public List<LabParameters> getLabParamArray() {
        return this.model.getLabParamArray();
    }

    public LabResults getResults() {
        return this.model.getResults();
    }

    public List<LabResults> getResultsArray() {
        return this.model.getLabResArray();
    }

    public int getRowCount() {
        return (this.view.rView.contadorConsumidores.getRowCount() - 1);
    }

    //Metodos Publicos
    public void play() throws InterruptedException {
        this.model.play();
    }

    public void resetConfig() {
        this.model.resetConfig();
    }

    public void defaultResults() {
        this.model.defaultResults();
    }

}
