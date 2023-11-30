package productorconsumidormodel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TJTLVista extends JFrame implements ComponentListener, ActionListener, ItemListener, ChangeListener, MouseListener, Runnable {

    // Atributos
    TJTLController controller;
    ControlPanel cPanel;
    GeneralConfiguration gConf;
    ResultsView rView;

    private volatile boolean update = true;

    // Constructor
    public TJTLVista(TJTLController controller) {
        this.controller = controller;
        this.configureJFrame();
        this.setVisible(true);
        this.controller.resetConfig();
        this.cPanel.setParams();
        this.gConf.setParams();
        this.controller.defaultResults();

    }

    // Metodos Privados
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

    private void configureJFrame() {
        this.setTitle("Thread Lab");
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 800);
        this.addComponentsToPane(this.getContentPane());
    }

    private void updateUI() {
        SwingUtilities.invokeLater(() -> {
            LabResults res = this.controller.model.getResults();
            this.rView.tiempoArrancarThreads.setText(String.valueOf(res.tiempoArrancarThreads));
            this.rView.tiempoCrearThreads.setText(String.valueOf(res.tiempoCrearThreads));
            this.rView.consumidoresArrancados.setText(String.valueOf(res.consumidoresArrancados));
            this.rView.productoresArrancados.setText(String.valueOf(res.productoresArrancados));
            this.rView.consumidoresFinalizados.setText(String.valueOf(res.consumidoresFinalizados));
            this.rView.productoresFinalizados.setText(String.valueOf(res.productoresFinalizados));
            this.rView.consumidoresTiempoFinal.setText(String.valueOf(res.consumidoresTiempoFinal));
            this.rView.productoresTiempoFinal.setText(String.valueOf(res.productoresTiempoFinal));
            if (this.controller.getRowCount() >= 0) {

                this.controller.getResultsArray().add(this.controller.getRowCount(), this.controller.getResults());

                this.rView.contadorProductores.setValueAt(this.controller.getResults().contadorProductores, this.rView.contadorProductores.getRowCount() - 1, this.rView.contadorProductores.getColumnCount() - 1);
                this.rView.contadorConsumidores.setValueAt(this.controller.getResults().contadorConsumidores, this.rView.contadorConsumidores.getRowCount() - 1, this.rView.contadorConsumidores.getColumnCount() - 1);
            }
        });
    }

    // Metodos Override
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        switch (str) {
            case "Play": {
                try {
                    if (this.cPanel.playPause.isSelected()) {
                        this.controller.getLabParam().stop = false;
                        this.update = true;
                        controller.play();
                    } else {
                        this.controller.getLabParam().stop = true;
                        this.update = false;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(TJTLVista.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "Reset":
                this.controller.model.resetConfig();
                this.cPanel.setParams();
                this.gConf.setParams();
                break;
            case "Apply":
                this.controller.model.applyConfig();
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
        this.controller.getLabParamArray().get(row);
        LabResults oLabRes = this.controller.getResultsArray().get(row);
        LabParameters oLabParam = this.controller.getLabParamArray().get(row);

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

}
