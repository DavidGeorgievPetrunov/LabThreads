package productorconsumidormodel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ProductorConsumidorView extends JFrame implements ComponentListener, ActionListener, ItemListener, ChangeListener, MouseListener, Runnable {

    ProductorConsumidorController controller;
    ControlPanel cPanel;
    GeneralConfiguration gConf;
    ResultsView rView;

    private volatile boolean update = true;

    public ProductorConsumidorView(ProductorConsumidorController controller) {
        this.controller = controller;

        this.configureJFrame();
        this.setVisible(true);
        this.restartParams();
        this.controller.model.defaultResults();
    }

    private void addComponentsToPane(Container panel) {

        GridBagConstraints c = new GridBagConstraints();
        panel.setBackground(Color.DARK_GRAY);

        colores();

        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 1;
        c.gridwidth = 2;

        this.cPanel = new ControlPanel(this);
        panel.add(this.cPanel, c);

        c.gridy = 4;
        this.gConf = new GeneralConfiguration(this);
        panel.add(this.gConf, c);

        this.rView = new ResultsView(this);
        c.gridy = 11;
        panel.add(rView, c);

    }

    private void configureJFrame() {
        this.setTitle("Thread Lab");
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 800);
        this.addComponentsToPane(this.getContentPane());
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (this.update == true) {
                    Thread.sleep(100);
                    updateUI();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUI() {
        SwingUtilities.invokeLater(() -> {
            this.controller.labRes = this.controller.model.getResults();
            this.rView.tiempoArrancarThreads.setText(String.valueOf(this.controller.labRes.tiempoArrancarThreads));
            this.rView.tiempoCrearThreads.setText(String.valueOf(this.controller.labRes.tiempoCrearThreads));
            this.rView.consumidoresArrancados.setText(String.valueOf(this.controller.labRes.consumidoresArrancados));
            this.rView.productoresArrancados.setText(String.valueOf(this.controller.labRes.productoresArrancados));
            this.rView.consumidoresFinalizados.setText(String.valueOf(controller.labRes.consumidoresFinalizados));
            this.rView.productoresFinalizados.setText(String.valueOf(controller.labRes.productoresFinalizados));
            this.rView.consumidoresTiempoFinal.setText(String.valueOf(controller.labRes.consumidoresTiempoFinal));
            this.rView.productoresTiempoFinal.setText(String.valueOf(controller.labRes.productoresTiempoFinal));
            if (this.rView.contadorProductores.getRowCount() >= 1) {

                this.controller.labResArray.add(this.rView.contadorConsumidores.getRowCount() - 1, this.controller.model.getResults());

                this.rView.contadorProductores.setValueAt(this.controller.labRes.contadorProductores, this.rView.contadorProductores.getRowCount() - 1, this.rView.contadorProductores.getColumnCount() - 1);
                this.rView.contadorConsumidores.setValueAt(this.controller.labRes.contadorConsumidores, this.rView.contadorConsumidores.getRowCount() - 1, this.rView.contadorConsumidores.getColumnCount() - 1);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        switch (str) {
            case "Play": {
                try {
                    if (this.cPanel.playPause.isSelected()) {
                        this.controller.labParam.stop = false;
                        this.update = true;
                        controller.play();
                        this.controller.labParamArray.add(this.controller.view.rView.contadorConsumidores.getRowCount() - 1, this.getLabParams());
                    } else {
                        this.controller.labParam.stop = true;
                        this.update = false;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProductorConsumidorView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "Reset":
                restartParams();
                break;
            case "Apply":
                updateValues();
                break;
            default:
                System.err.println("Acción NO tratada: " + e);
        }
    }

    @Override
    public void componentHidden(ComponentEvent ce) {
        System.out.println("Frame hidden");
    }

    @Override
    public void componentMoved(ComponentEvent ce) {
        System.out.println("Frame moved");
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        System.out.println("Frame resized");
    }

    @Override
    public void componentShown(ComponentEvent ce) {
        System.out.println("Frame Shown");
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        int estado = itemEvent.getStateChange();
        if (estado == ItemEvent.SELECTED) {
        } else {
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = this.rView.contadorConsumidores.rowAtPoint(e.getPoint());
        this.update = false;
        this.controller.labParamArray.get(row);
        LabResults oLabRes = this.controller.labResArray.get(row);
        LabParameters oLabParam = this.controller.labParamArray.get(row);

        this.gConf.nombreProducto.setText((String) this.rView.contadorConsumidores.getValueAt(row, 0));

        this.rView.tiempoArrancarThreads.setText(String.valueOf(oLabRes.tiempoArrancarThreads));
        this.rView.tiempoCrearThreads.setText(String.valueOf(oLabRes.tiempoCrearThreads));
        this.rView.consumidoresArrancados.setText(String.valueOf(oLabRes.consumidoresArrancados));
        this.rView.productoresArrancados.setText(String.valueOf(oLabRes.productoresArrancados));
        this.rView.consumidoresFinalizados.setText(String.valueOf(oLabRes.consumidoresFinalizados));
        this.rView.productoresFinalizados.setText(String.valueOf(oLabRes.productoresFinalizados));
        this.rView.consumidoresTiempoFinal.setText(String.valueOf(oLabRes.consumidoresTiempoFinal));
        this.rView.productoresTiempoFinal.setText(String.valueOf(oLabRes.productoresTiempoFinal));

        this.cPanel.preventNegativeStocks.setSelected(oLabParam.preventNegativeStocks);
        this.cPanel.protectCriticalRegions.setSelected(oLabParam.protectCriticalRegions);

        this.gConf.productoresS.setValue(oLabParam.productoresS);
        this.gConf.consumidoresS.setValue(oLabParam.consumidoresS);
        this.gConf.cantidadProductoresTF.setText(Integer.toString(oLabParam.cantidadProductoresTF));
        this.gConf.cantidadConsumidoresTF.setText(Integer.toString(oLabParam.cantidadConsumidoresTF));
        this.gConf.cantidadItemsxProductor.setText(Integer.toString(oLabParam.cantidadItemsxProductor));
        this.gConf.cantidadItemsxConsumidor.setText(Integer.toString(oLabParam.cantidadItemsxConsumidor));
        this.gConf.productoresCB.setSelected(oLabParam.productoresCB);
        this.gConf.consumidoresCB.setSelected(oLabParam.consumidoresCB);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Object source = e.getSource();

        if (source instanceof JSlider) {
            if (source.equals(this.gConf.productoresS)) {
                this.gConf.productoresSL.setText("Productores tiempo: " + this.gConf.productoresS.getValue());
            } else if (source.equals(this.gConf.consumidoresS)) {
                this.gConf.consumidoresSL.setText("Consumidores tiempo: " + this.gConf.consumidoresS.getValue());
            }
        }

    }

    public ProductorConsumidorController getController() {
        return controller;
    }

    public void setController(ProductorConsumidorController controller) {
        this.controller = controller;
    }

    private void colores() {
        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("ScrollPane.background", Color.DARK_GRAY);
        UIManager.put("ToggleButton.foreground", new Color(173, 216, 230));
        UIManager.put("ToggleButton.background", new Color(0, 0, 139));
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("Label.background", Color.DARK_GRAY);
        UIManager.put("Button.foreground", new Color(173, 216, 230));
        UIManager.put("Button.background", new Color(0, 0, 139));
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("TextField.background", Color.GRAY);
        UIManager.put("CheckBox.foreground", Color.WHITE);
        UIManager.put("CheckBox.background", Color.DARK_GRAY);
        UIManager.put("Slider.background", Color.DARK_GRAY);
        UIManager.put("Table.background", Color.blue);
        UIManager.put("Table.foreground", new Color(173, 216, 230));
    }

    public void restartParams() {

        this.controller.labParam.preventNegativeStocks = false;
        this.controller.labParam.protectCriticalRegions = true;

        this.cPanel.setParams();

        this.controller.labParam.productoresS = 100;
        this.controller.labParam.consumidoresS = 100;
        this.controller.labParam.cantidadProductoresTF = 200;
        this.controller.labParam.cantidadConsumidoresTF = 400;
        this.controller.labParam.cantidadItemsxProductor = 200;
        this.controller.labParam.cantidadItemsxConsumidor = 200;
        this.controller.labParam.productoresCB = false;
        this.controller.labParam.consumidoresCB = false;

        this.gConf.setParams();
    }

    private void updateValues() {
        this.cPanel.updateValues();
        this.gConf.updateValues();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private LabParameters getLabParams() {
        LabParameters oLabParam = new LabParameters();
        oLabParam.cantidadConsumidoresTF = this.controller.labParam.cantidadConsumidoresTF;
        oLabParam.cantidadItemsxConsumidor = this.controller.labParam.cantidadItemsxConsumidor;
        oLabParam.cantidadItemsxProductor = this.controller.labParam.cantidadItemsxProductor;
        oLabParam.cantidadProductoresTF = this.controller.labParam.cantidadProductoresTF;
        oLabParam.consumidoresS = this.controller.labParam.consumidoresS;
        oLabParam.productoresS = this.controller.labParam.productoresS;
        oLabParam.preventNegativeStocks = this.controller.labParam.preventNegativeStocks;
        oLabParam.consumidoresCB = this.controller.labParam.consumidoresCB;
        oLabParam.productoresCB = this.controller.labParam.productoresCB;
        oLabParam.protectCriticalRegions = this.controller.labParam.protectCriticalRegions;
        return oLabParam;

    }

}
