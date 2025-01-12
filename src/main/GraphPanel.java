package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphPanel extends JPanel {
    private final List<Locality> locations;
    private final List<Edge> edges;
    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    private final int NODE_RADIUS = 30; // Tamanho do nó (círculo)
    private final int REPULSION = 3000; // Constante de repulsão
    private final double SPRING_LENGTH = 150; // Comprimento ideal da aresta

    private final Point[] positions; // Coordenadas dinâmicas dos nós


    public GraphPanel() {
        this.locations = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.positions = new Point[50]; // Limite inicial de locations
        setBackground(Color.DARK_GRAY);
    }

    public void addLocation(Locality locality) {
        locations.add(locality);
        // Inicializa a posição do nó em coordenadas aleatórias
        positions[locations.size() - 1] = new Point((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT));
        repaint();
    }

    public void addEdge(Locality origin, Locality destination, int distancy) {
        edges.add(new Edge(origin, destination, distancy));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Atualizar as posições dos nós com base na simulação de forças
        updatePositions();

        // Desenhar edges
        g2d.setColor(Color.GREEN);
        for (Edge aresta : edges) {
            int origemIndex = locations.indexOf(aresta.origin);
            int destinoIndex = locations.indexOf(aresta.destination);
            if (origemIndex != -1 && destinoIndex != -1) {
                Point origemPos = positions[origemIndex];
                Point destinoPos = positions[destinoIndex];
                g2d.drawLine(origemPos.x, origemPos.y, destinoPos.x, destinoPos.y);
                g2d.drawString(String.valueOf(aresta.distancy),
                        (origemPos.x + destinoPos.x) / 2,
                        (origemPos.y + destinoPos.y) / 2);
            }
        }

        // Desenhar locations
        for (int i = 0; i < locations.size(); i++) {
            Point pos = positions[i];
            Locality localidade = locations.get(i);


            // Nó (círculo)
            g2d.setColor(Color.magenta);
            g2d.fillOval(pos.x - NODE_RADIUS / 2, pos.y - NODE_RADIUS / 2, NODE_RADIUS, NODE_RADIUS);

            // Nome do nó (rótulo) ao lado direito
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.setColor(Color.WHITE);
            g2d.drawString(localidade.getName(), pos.x + NODE_RADIUS, pos.y);
        }


    }

    private void updatePositions() {

        int padding = 50; // Margem adicional
        for (int i = 0; i < locations.size(); i++) {
            positions[i] = new Point(
                    (int) (Math.random() * (getWidth() - 2 * padding)) + padding,
                    (int) (Math.random() * (getHeight() - 2 * padding)) + padding
            );
        }
        int numLocalidades = locations.size();
        if (numLocalidades < 2) return;

        for (int i = 0; i < numLocalidades; i++) {
            Point p1 = positions[i];
            double deltaX = 0;
            double deltaY = 0;

            for (int j = 0; j < numLocalidades; j++) {
                if (i == j) continue;
                Point p2 = positions[j];
                double dx = p1.x - p2.x;
                double dy = p1.y - p2.y;
                double distanceSquared = dx * dx + dy * dy;
                double force = REPULSION / Math.max(distanceSquared, 0.01);
                deltaX += force * dx / Math.sqrt(distanceSquared);
                deltaY += force * dy / Math.sqrt(distanceSquared);
            }

            // Atração das edges (ajustado para considerar distâncias reais)
            for (Edge aresta : edges) {
                int origemIndex = locations.indexOf(aresta.origin);
                int destinoIndex = locations.indexOf(aresta.destination);
                if (origemIndex == i || destinoIndex == i) {
                    int otherIndex = (origemIndex == i) ? destinoIndex : origemIndex;
                    Point p2 = positions[otherIndex];

                    // Distância entre os nós
                    double dx = p2.x - p1.x;
                    double dy = p2.y - p1.y;
                    double currentDistance = Math.sqrt(dx * dx + dy * dy);

                    // Força de mola proporcional à diferença entre distância atual e desejada
                    double targetDistance = aresta.distancy; // Use a distância da aresta como referência
                    double force = (currentDistance - targetDistance) * 0.1; // Constante de ajuste da mola
                    deltaX += force * dx / currentDistance;
                    deltaY += force * dy / currentDistance;
                }
            }

            // Atualiza a posição do nó com base nas forças calculadas
            p1.x = Math.min(WIDTH - NODE_RADIUS, Math.max(NODE_RADIUS, (int) (p1.x + deltaX)));
            p1.y = Math.min(HEIGHT - NODE_RADIUS, Math.max(NODE_RADIUS, (int) (p1.y + deltaY)));
        }
    }


    private static class Edge {
        Locality origin;
        Locality destination;
        int distancy;

        Edge(Locality origin, Locality destination, int distancy) {
            this.origin = origin;
            this.destination = destination;
            this.distancy = distancy;
        }
    }
}
