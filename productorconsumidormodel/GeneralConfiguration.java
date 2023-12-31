package productorconsumidormodel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class GeneralConfiguration extends JPanel {

    // Atributos 
    JSlider consumidoresS;
    JSlider productoresS;

    JLabel productoresSL;
    JLabel consumidoresSL;

    JLabel nombreProductoL;
    JTextField nombreProducto;

    TJTLVista view;

    JTextField cantidadProductoresTF;
    JTextField cantidadConsumidoresTF;
    JLabel cantidadProductoresL;
    JLabel cantidadConsumidoresL;
    JLabel gConfL;
    JTextField cantidadItemsxProductor;
    JLabel cantidadItemsxProductorL;
    JTextField cantidadItemsxConsumidor;
    JLabel cantidadItemsxConsumidorL;

    JCheckBox productoresCB;
    JCheckBox consumidoresCB;

    // Constructor
    public GeneralConfiguration(TJTLVista view) {
        this.view = view;
        setLayout(new GridBagLayout());
        configureGeneralConfiguration();
    }

    // Metodos Publicos
    public void applyConfig() {

        this.view.controller.getLabParam().productoresS = this.productoresS.getValue();
        this.view.controller.getLabParam().consumidoresS = this.consumidoresS.getValue();
        this.view.controller.getLabParam().cantidadProductoresTF = Integer.parseInt(this.cantidadProductoresTF.getText());
        this.view.controller.getLabParam().cantidadConsumidoresTF = Integer.parseInt(this.cantidadConsumidoresTF.getText());
        this.view.controller.getLabParam().cantidadItemsxProductor = Integer.parseInt(this.cantidadItemsxProductor.getText());
        this.view.controller.getLabParam().cantidadItemsxConsumidor = Integer.parseInt(this.cantidadItemsxConsumidor.getText());
        this.view.controller.getLabParam().productoresCB = this.productoresCB.isSelected();
        this.view.controller.getLabParam().consumidoresCB = this.consumidoresCB.isSelected();

    }

    public void setParams() {

        this.productoresS.setValue(this.view.controller.getLabParam().productoresS);
        this.consumidoresS.setValue(this.view.controller.getLabParam().consumidoresS);
        this.cantidadProductoresTF.setText(Integer.toString(this.view.controller.getLabParam().cantidadProductoresTF));
        this.cantidadConsumidoresTF.setText(Integer.toString(this.view.controller.getLabParam().cantidadConsumidoresTF));
        this.cantidadItemsxProductor.setText(Integer.toString(this.view.controller.getLabParam().cantidadItemsxProductor));
        this.cantidadItemsxConsumidor.setText(Integer.toString(this.view.controller.getLabParam().cantidadItemsxConsumidor));
        this.productoresCB.setSelected(this.view.controller.getLabParam().productoresCB);
        this.consumidoresCB.setSelected(this.view.controller.getLabParam().consumidoresCB);

    }

    //Metodos privados
    private void añadirComponentes(GridBagConstraints c) {

        c.gridwidth = 2;
        this.gConfL.setForeground(Color.BLUE);
        this.gConfL.setBackground(Color.BLACK);
        this.gConfL.setOpaque(true);
        this.gConfL.setHorizontalAlignment(JLabel.CENTER);
        this.gConfL.setVerticalAlignment(JLabel.CENTER);

        this.add(this.gConfL, c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy++;
        this.nombreProductoL.setOpaque(true);
        this.add(this.nombreProductoL, c);
        c.gridx = 1;
        this.add(this.nombreProducto, c);

        c.gridx = 0;
        c.gridy++;
        this.cantidadProductoresL.setOpaque(true);
        this.add(this.cantidadProductoresL, c);
        c.gridx = 1;
        this.cantidadConsumidoresL.setOpaque(true);
        this.add(this.cantidadConsumidoresL, c);

        c.gridx = 0;
        c.gridy++;
        this.add(this.cantidadProductoresTF, c);
        c.gridx = 1;
        this.add(this.cantidadConsumidoresTF, c);

        c.gridx = 0;
        c.gridy++;
        this.add(this.cantidadItemsxProductorL, c);
        c.gridx = 1;
        this.add(this.cantidadItemsxConsumidorL, c);

        c.gridx = 0;
        c.gridy++;
        this.add(this.cantidadItemsxProductor, c);
        c.gridx = 1;
        this.add(this.cantidadItemsxConsumidor, c);

        c.gridx = 0;
        c.gridy++;
        this.consumidoresSL.setOpaque(true);
        this.add(this.consumidoresSL, c);
        c.gridx = 1;
        this.add(this.consumidoresS, c);

        c.gridx = 0;
        c.gridy++;
        this.productoresSL.setOpaque(true);
        this.add(this.productoresSL, c);
        c.gridx = 1;
        this.add(this.productoresS, c);

        c.gridx = 0;
        c.gridy++;
        this.add(this.productoresCB, c);
        c.gridx = 1;
        this.add(this.consumidoresCB, c);

    }

    private void configureGeneralConfiguration() {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 1;
        c.gridwidth = 1;

        crearComponentes();
        añadirComponentes(c);

    }

    private void crearComponentes() {
        this.gConfL = new JLabel("PARAMETROS GENERALES");

        this.nombreProductoL = new JLabel("Nombre Producto:");
        this.nombreProducto = new JTextField("");

        this.cantidadProductoresTF = new JTextField("");
        this.cantidadProductoresL = new JLabel("Numero de threads productores");

        this.cantidadConsumidoresTF = new JTextField("");
        this.cantidadConsumidoresL = new JLabel("Numero de threads consumidores");

        this.cantidadItemsxProductor = new JTextField("");
        this.cantidadItemsxProductorL = new JLabel("ItemsxProductor");
        this.cantidadItemsxProductorL.setOpaque(true);

        this.cantidadItemsxConsumidor = new JTextField("");
        this.cantidadItemsxConsumidorL = new JLabel("ItemsxConsumidor");
        this.cantidadItemsxConsumidorL.setOpaque(true);

        this.consumidoresS = new JSlider(JSlider.HORIZONTAL, 1, 1000, 100);
        this.consumidoresS.addChangeListener(this.view);
        this.productoresS = new JSlider(JSlider.HORIZONTAL, 1, 1000, 100);
        this.productoresS.addChangeListener(this.view);

        this.productoresSL = new JLabel("Productores tiempo: " + productoresS.getValue());
        this.consumidoresSL = new JLabel("Consumidores tiempo: " + consumidoresS.getValue());
        this.productoresCB = new JCheckBox("Tiempo Fijo Productor");
        this.consumidoresCB = new JCheckBox("Tiempo Fijo Consumidor");
    }
}
