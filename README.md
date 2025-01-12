# <img width="35px" margin="0px" src="https://img.icons8.com/?size=100&id=Xq1jFvfU23Qc&format=png&color=000000"> Travelling-Salesman <img width="35px" margin="0px" src="https://img.icons8.com/?size=100&id=Xq1jFvfU23Qc&format=png&color=000000">

Este projeto implementa uma interface gráfica em Java Swing para resolver o **Problema do Caixeiro Viajante (Travelling Salesman Problem, TSP)**. O usuário pode adicionar localidades, definir distâncias entre elas e calcular a rota mais curta que visita todas as localidades e retorna à origem.

## Funcionalidades <sub><img width="30px" src="https://img.icons8.com/?size=100&id=CxXz8glzbSOX&format=png&color=000000"></sub>

- **Adicionar Localidades**: Permite ao usuário adicionar localidades que farão parte do grafo de rotas.
- **Adicionar Distâncias**: Permite definir as distâncias entre as localidades adicionadas.
- **Calcular Melhor Rota**: Utiliza o algoritmo de força bruta com backtracking para encontrar a rota mais curta.
- **Visualização Gráfica**: Mostra as localidades e as conexões no painel gráfico.

## Screenshot <sub><img width="30px" src="https://img.icons8.com/?size=100&id=xZiTPdO57ltQ&format=png&color=000000"></sub>

<img width="800px" src ="https://i.imgur.com/HWGOdBT.png">

## Estrutura do Código <sub><img width="30px" src="https://img.icons8.com/?size=100&id=jX4CPM-Sr2OS&format=png&color=000000"></sub>

### Classe `MainSwing`

- **Atributos**:
  - `Route route`: Objeto que armazena as localidades e distâncias.
  - `List<Locality> localities`: Lista das localidades adicionadas.
  - `DefaultTableModel tableModel`: Modelo da tabela que exibe as distâncias.
  - `JTable distanceTable`: Tabela que exibe as distâncias entre localidades.
  - `JTextField locationInput`: Campo de entrada para adicionar localidades.
  - `JTextArea resultArea`: Área de texto que mostra o resultado da rota calculada.
  - `GraphPanel graphPanel`: Painel personalizado para visualização gráfica das localidades e distâncias.

- **Métodos**:
  - `initComponents()`: Inicializa os componentes da interface.
  - `addLocation()`: Adiciona uma localidade à lista e ao grafo visual.
  - `addDistance()`: Adiciona uma distância entre duas localidades.
  - `findLocation(String nome)`: Encontra uma localidade pelo nome.
  - `calculateRoute()`: Calcula a melhor rota usando a classe `TravellingSalesman`.

### Classe `TravellingSalesman`

- **Métodos Principais**:
  - `calculateRoute(Route route, Locality origin)`: Calcula a rota mais curta a partir de uma localidade de origem.
  - `routeFinder(...)`: Função recursiva que utiliza backtracking para explorar todas as permutações de localidades.

### Classe `Route`

- Armazena as localidades e as distâncias entre elas.
- Métodos para adicionar localidades e definir distâncias entre elas.

### Classe `Locality`

- Representa uma localidade no grafo.
- Contém o nome da localidade e outros atributos relacionados.

### Classe `GraphPanel`

- Painel customizado que desenha as localidades e as conexões.
- Atualiza dinâmicamente com novas localidades e distâncias.

## Como Usar <sub><img width="30px" src="https://img.icons8.com/?size=100&id=jnU5ze2ojiku&format=png&color=000000"></sub>

1. Clone o repositório:
   ```bash
    git clone https://github.com/marceloaaps/Travelling-Salesman.git
   ```
2. Compile o código:
   ```bash
   javac MainSwing.java
   ```
3. Execute o programa:
   ```bash
   java MainSwing
   ```
4. Utilize a interface Swing para adicionar localidades, definir distâncias e calcular a melhor rota.

## Contribuições <sub><img width="30px" src="https://img.icons8.com/?size=100&id=qdiD1wZOvDsv&format=png&color=000000"></sub>

- [Lucas Matias](https://www.linkedin.com/in/lucas-matias-25330b24a/): Criação do painel Swing e estilização.

- [Marcelo Alexandre](https://www.linkedin.com/in/marcelo-alexandre-dev/): Criação algoritmo TSP e implementação no Swing de forma dinâmica.



