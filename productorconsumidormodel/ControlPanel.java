package productorconsumidormodel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class ControlPanel extends JPanel {

    // Atributos
    JToggleButton playPause;
    TJTLVista view;
    JCheckBox protectCriticalRegions;
    JCheckBox preventNegativeStocks;
    JButton apply;
    JButton reset;
    JLabel cPanelL;

    // Constructor
    public ControlPanel(TJTLVista view) {
        this.view = view;
        setLayout(new GridBagLayout());
        configureControlPanel();
    }

    // Metodos Publicos
    public void applyConfig() {
        this.view.controller.getLabParam().preventNegativeStocks = this.preventNegativeStocks.isSelected();
        this.view.controller.getLabParam().protectCriticalRegions = this.protectCriticalRegions.isSelected();
    }

    public void setParams() {
        this.preventNegativeStocks.setSelected(this.view.controller.getLabParam().preventNegativeStocks);
        this.protectCriticalRegions.setSelected(this.view.controller.getLabParam().protectCriticalRegions);

    }

    // Metodos privados
    private void añadirComponentes(GridBagConstraints c) {

        this.cPanelL.setForeground(Color.BLUE);
        this.cPanelL.setBackground(Color.BLACK);
        this.cPanelL.setOpaque(true);
        this.cPanelL.setHorizontalAlignment(JLabel.CENTER);
        this.cPanelL.setVerticalAlignment(JLabel.CENTER);

        this.add(this.cPanelL, c);
        c.gridy++;
        this.add(this.playPause, c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy++;

        this.add(this.apply, c);

        c.gridx = 1;

        this.add(this.reset, c);

        c.gridx = 0;
        c.gridy++;
        this.add(this.preventNegativeStocks, c);
        c.gridx = 1;
        this.add(this.protectCriticalRegions, c);
    }

    private void configureControlPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 1;
        c.gridwidth = 2;

        crearComponentes();
        añadirComponentes(c);

    }

    private void crearComponentes() {
        this.cPanelL = new JLabel("PANEL DE CONTROL");
        this.playPause = new JToggleButton("Play");
        this.playPause.addActionListener(this.view);

        this.apply = new JButton("Apply");
        this.apply.addActionListener(this.view);
        this.reset = new JButton("Reset");
        this.reset.addActionListener(this.view);

        this.preventNegativeStocks = new JCheckBox("Prevenir Stock Negativo");
        this.preventNegativeStocks.addChangeListener(this.view);
        this.protectCriticalRegions = new JCheckBox("Regiones Criticas Protegidas");
        this.protectCriticalRegions.addChangeListener(this.view);
    }

}
