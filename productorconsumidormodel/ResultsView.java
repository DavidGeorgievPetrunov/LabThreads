package productorconsumidormodel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class ResultsView extends JPanel {

    private ProductorConsumidorView view;

    JTextField productoresArrancados;
    JTextField consumidoresArrancados;
    JTextField productoresFinalizados;
    JTextField consumidoresFinalizados;

    JLabel productoresArrancadosL;
    JLabel consumidoresArrancadosL;
    JLabel productoresFinalizadosL;
    JLabel consumidoresFinalizadosL;

    JLabel tiempoCrearThreadsL;
    JLabel tiempoArrancarThreadsL;
    JLabel productoresTiempoFinalL;
    JLabel consumidoresTiempoFinalL;

    JTextField tiempoCrearThreads;
    JTextField tiempoArrancarThreads;
    JTextField productoresTiempoFinal;
    JTextField consumidoresTiempoFinal;

    JTable contadorConsumidores;
    JLabel contadorProductoresL;
    JLabel rViewL;
    JLabel contadorConsumidoresL;
    JTable contadorProductores;

    JScrollPane contadorConsumidoresSc;
    JScrollPane contadorProductoresSc;

    public ResultsView(ProductorConsumidorView view) {
        this.view = view;
        setLayout(new GridBagLayout());
        configureResultsView();
    }

    public void configureResultsView() {
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

    public void crearComponentes() {
        this.rViewL = new JLabel("RESULTADOS");

        this.productoresArrancadosL = new JLabel("Productores arrancados");
        this.consumidoresArrancadosL = new JLabel("Consumidores arrancados");
        this.productoresFinalizadosL = new JLabel("Productores finalizados");
        this.consumidoresFinalizadosL = new JLabel("Consumidores finalizados");

        this.productoresArrancados = new JTextField("");
        this.consumidoresArrancados = new JTextField("");
        this.productoresFinalizados = new JTextField("");
        this.consumidoresFinalizados = new JTextField("");

        this.tiempoCrearThreadsL = new JLabel("Tiempo Crear Threads");
        this.tiempoArrancarThreadsL = new JLabel("Tiempo Arrancar Threads");
        this.productoresTiempoFinalL = new JLabel("Tiempo Final Productores");
        this.consumidoresTiempoFinalL = new JLabel("Tiempo Final Consumidores");

        this.tiempoCrearThreads = new JTextField("");
        this.tiempoArrancarThreads = new JTextField("");
        this.productoresTiempoFinal = new JTextField("");
        this.consumidoresTiempoFinal = new JTextField("");

        this.contadorProductoresL = new JLabel("Producto producido: ");
        this.contadorConsumidoresL = new JLabel("Producto consumido: ");

        DefaultTableModel modelC = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que las celdas no sean editables
            }
        };
        modelC.addColumn("Producto");
        modelC.addColumn("Cantidad");

        DefaultTableModel modelP = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que las celdas no sean editables
            }
        };
        modelP.addColumn("Producto");
        modelP.addColumn("Cantidad");

        this.contadorConsumidores = new JTable(modelC);
        this.contadorConsumidores.addMouseListener((MouseListener) this.view);
        this.contadorProductores = new JTable(modelP);
        this.contadorProductores.addMouseListener((MouseListener) this.view);

        this.contadorConsumidoresSc = new JScrollPane(contadorConsumidores);
        this.contadorProductoresSc = new JScrollPane(contadorProductores);

    }

    private void añadirComponentes(GridBagConstraints c) {
        c.gridwidth = 2;
        this.rViewL.setForeground(Color.BLUE);
        this.rViewL.setBackground(Color.BLACK);
        this.rViewL.setOpaque(true);
        this.rViewL.setHorizontalAlignment(JLabel.CENTER);
        this.rViewL.setVerticalAlignment(JLabel.CENTER);

        this.add(this.rViewL, c);

        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy++;
        this.tiempoCrearThreadsL.setOpaque(true);
        this.add(this.tiempoCrearThreadsL, c);
        c.gridx = 1;
        this.add(this.tiempoCrearThreads, c);

        c.gridx = 0;
        c.gridy++;
        this.tiempoArrancarThreadsL.setOpaque(true);
        this.add(this.tiempoArrancarThreadsL, c);
        c.gridx = 1;
        this.add(this.tiempoArrancarThreads, c);

        c.gridx = 0;
        c.gridy++;
        this.productoresTiempoFinalL.setOpaque(true);
        this.add(this.productoresTiempoFinalL, c);
        c.gridx = 1;
        this.add(this.productoresTiempoFinal, c);

        c.gridx = 0;
        c.gridy++;
        this.consumidoresTiempoFinalL.setOpaque(true);
        this.add(this.consumidoresTiempoFinalL, c);
        c.gridx = 1;
        this.add(this.consumidoresTiempoFinal, c);

        c.gridx = 0;
        c.gridy++;
        this.contadorProductoresL.setOpaque(true);
        this.add(contadorProductoresL, c);
        c.gridx = 1;
        c.ipadx = 0;
        this.contadorConsumidoresL.setOpaque(true);
        this.add(this.contadorConsumidoresL, c);

        c.gridx = 0;
        c.ipady = 40;
        c.gridy++;
        this.add(this.contadorProductoresSc, c);
        c.gridx = 1;
        this.add(contadorConsumidoresSc, c);

        c.gridx = 0;
        c.gridy++;
        c.ipady = 0;
        this.productoresArrancadosL.setOpaque(true);
        this.add(this.productoresArrancadosL, c);
        c.gridx = 1;
        this.add(this.productoresArrancados, c);

        c.gridx = 0;
        c.gridy++;
        this.consumidoresArrancadosL.setOpaque(true);
        this.add(this.consumidoresArrancadosL, c);
        c.gridx = 1;
        this.add(this.consumidoresArrancados, c);

        c.gridx = 0;
        c.gridy++;
        this.productoresFinalizadosL.setOpaque(true);
        this.add(this.productoresFinalizadosL, c);
        c.gridx = 1;
        this.add(this.productoresFinalizados, c);

        c.gridx = 0;
        c.gridy++;
        this.consumidoresFinalizadosL.setOpaque(true);
        this.add(this.consumidoresFinalizadosL, c);
        c.gridx = 1;
        this.add(this.consumidoresFinalizados, c);

    }

}
