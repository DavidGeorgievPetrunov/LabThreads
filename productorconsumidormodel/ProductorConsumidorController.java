package productorconsumidormodel;

import java.util.ArrayList;
import java.util.List;

public class ProductorConsumidorController {
    
    public List<LabResults> labResArray;

    public List<LabParameters> labParamArray;
    
    public LabResults labRes;

    public LabParameters labParam;

    public ProductorConsumidorModel model;

    public ProductorConsumidorView view;

    private Producto producto;

    ContadorSincronizado CCI;
    ContadorSincronizado CCF;
    ContadorSincronizado CPI;
    ContadorSincronizado CPF;

    public ProductorConsumidorController() {
        this.labParamArray = new ArrayList<>();
        this.labResArray = new ArrayList<>();
        this.labRes = new LabResults();
        this.labParam = new LabParameters();
        this.CCI = new ContadorSincronizado();
        this.CCF = new ContadorSincronizado();
        this.CPI = new ContadorSincronizado();
        this.CPF = new ContadorSincronizado();
        this.producto = new Producto();
        this.model = new ProductorConsumidorModel(producto, CCI, CCF, CPI, CPF, this);
        this.view = new ProductorConsumidorView(this);
    }

    public static void main(String[] args) {
        ProductorConsumidorController controller = new ProductorConsumidorController();

        Thread hilo = new Thread(controller.getView());
        hilo.start();
    }

    public void play() throws InterruptedException {
        this.model.play();
    }
    
    public ProductorConsumidorModel getModel() {
        return model;
    }

    public void setModel(ProductorConsumidorModel model) {
        this.model = model;
    }

    public ProductorConsumidorView getView() {
        return view;
    }

    public void setView(ProductorConsumidorView view) {
        this.view = view;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ContadorSincronizado getCCI() {
        return CCI;
    }

    public void setCCI(ContadorSincronizado CCI) {
        this.CCI = CCI;
    }

    public ContadorSincronizado getCCF() {
        return CCF;
    }

    public void setCCF(ContadorSincronizado CCF) {
        this.CCF = CCF;
    }

    public ContadorSincronizado getCPI() {
        return CPI;
    }

    public void setCPI(ContadorSincronizado CPI) {
        this.CPI = CPI;
    }

    public ContadorSincronizado getCPF() {
        return CPF;
    }

    public void setCPF(ContadorSincronizado CPF) {
        this.CPF = CPF;
    }

    public LabParameters getLabParameters(){
        return this.labParam;
    }
    
}
