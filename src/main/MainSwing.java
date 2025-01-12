package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainSwing extends JFrame {
    private Route route;
    private List<Locality> localities;
    private DefaultTableModel tableModel;
    private JTable distanceTable;
    private JTextField locationInput;
    private JTextArea resultArea;
    private GraphPanel graphPanel;


    public MainSwing() {
        route = new Route();
        localities = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {
        setTitle("Caixeiro Viajante - Planejamento de Rotas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        graphPanel = new GraphPanel();
        graphPanel.setPreferredSize(new Dimension(600, 600));
        add(graphPanel, BorderLayout.EAST);

        // Painel Superior - Cadastro de Localidades
        JPanel topPanel = new JPanel(new BorderLayout());
        locationInput = new JTextField(10);
        JButton addLocationButton = new JButton("Adicionar main.Locality");
        addLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLocation();
            }
        });
        JPanel locationPanel = new JPanel();
        locationPanel.add(new JLabel("main.Locality: "));
        locationPanel.add(locationInput);
        locationPanel.add(addLocationButton);
        topPanel.add(locationPanel, BorderLayout.NORTH);

        // Tabela de Distâncias
        tableModel = new DefaultTableModel(new Object[]{"Origem", "Destino", "Distância"}, 0);
        distanceTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(distanceTable);
        topPanel.add(tableScroll, BorderLayout.CENTER);

        JButton addDistanceButton = new JButton("Adicionar Distância");
        addDistanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDistance();
            }
        });
        topPanel.add(addDistanceButton, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.CENTER);

        // Painel Inferior - Resultado
        JPanel bottomPanel = new JPanel(new BorderLayout());
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 25));
        JScrollPane resultScroll = new JScrollPane(resultArea);
        bottomPanel.add(resultScroll, BorderLayout.CENTER);

        JButton calculateButton = new JButton("Calcular melhor rota");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateRoute();
            }
        });
        bottomPanel.add(calculateButton, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addLocation() {
        String nome = locationInput.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um nome válido para a localidade.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Locality locality = new Locality(nome);
        localities.add(locality);
        route.addLocality(locality);
        graphPanel.addLocation(locality); // Atualiza o grafo visual
        locationInput.setText("");
        JOptionPane.showMessageDialog(this, "Localidade adicionada com sucesso!");
    }

    private void addDistance() {
        if (localities.size() < 2) {
            JOptionPane.showMessageDialog(this, "Você precisa adicionar pelo menos 2 localidades antes de definir distâncias.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String originName = JOptionPane.showInputDialog(this, "Digite o nome da localidade de origem:");
        String destinationName = JOptionPane.showInputDialog(this, "Digite o nome da localidade de destino:");
        String distanceStr = JOptionPane.showInputDialog(this, "Digite a distância entre as localidades:");

        if (originName == null || destinationName == null || distanceStr == null) {
            JOptionPane.showMessageDialog(this, "Operação cancelada ou informações incompletas.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Locality origin = findLocation(originName.trim());
        Locality destination = findLocation(destinationName.trim());
        int distance;

        try {
            distance = Integer.parseInt(distanceStr.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "A distância deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (origin == null || destination == null) {
            JOptionPane.showMessageDialog(this, "Uma ou ambas as localidades não foram encontradas.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        route.addEdge(origin, destination, distance);
        tableModel.addRow(new Object[]{origin.getName(), destination.getName(), distance});
        JOptionPane.showMessageDialog(this, "Distância adicionada com sucesso!");

        route.addEdge(origin, destination, distance);
        tableModel.addRow(new Object[]{origin.getName(), destination.getName(), distance});
        graphPanel.addEdge(origin, destination, distance);
        JOptionPane.showMessageDialog(this, "Distância adicionada com sucesso!");
    }

    private Locality findLocation(String nome) {
        return localities.stream().filter(l -> l.getName().equalsIgnoreCase(nome)).findFirst().orElse(null);
    }

    private void calculateRoute() {
        if (localities.size() < 2) {
            JOptionPane.showMessageDialog(this, "Você precisa adicionar pelo menos 2 localidades e definir as distâncias.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TravellingSalesman tsp = new TravellingSalesman();
        tsp.calculateRoute(route, localities.get(0));

        StringBuilder result = new StringBuilder();
        result.append("Melhor Rota:\n");
        for (Locality local : tsp.getBestRoute()) {
            result.append(local.getName()).append(" -> ");
        }
        result.append(localities.get(0).getName()).append("\n");
        result.append("Distância Total: ").append(tsp.getShorterDistance()).append(" km\n");

        resultArea.setText(result.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainSwing app = new MainSwing();
            app.setVisible(true);
        });
    }
}
