
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class LeituraGrafo {
    private int[][] matrizConexoes;

    private NoCreche inicioCreches;
    private NoDistancia inicioDistancias;

    private int capacidadeMaxima;
    private int contadorCreches;

    public LeituraGrafo(int capacidade){
        this.capacidadeMaxima = capacidade;
        this.contadorCreches = 0;
        this.matrizConexoes = new int[capacidade][capacidade];
        this.inicioCreches = null;
        this.inicioDistancias = null;
    }

    public void adicionarCreche(String nome){
        if(contadorCreches >= capacidadeMaxima){
            return;
        } 

        NoCreche novo = new NoCreche(contadorCreches, nome);
        if(inicioCreches == null){
            inicioCreches = novo;
        } else {
            NoCreche atual = inicioCreches;
            while(atual.prox != null){
                atual = atual.prox;
            }
            atual.prox = novo;
        }
        contadorCreches++;
    }

    public void adicionarCaminho(int origem, int destino, double distancia){
        matrizConexoes[origem][destino] = 1;
        matrizConexoes[destino][origem] = 1;

        NoDistancia novo = new NoDistancia(origem, destino, distancia);

        if(inicioDistancias == null){
            inicioDistancias = novo;
        } else {
            NoDistancia atual = inicioDistancias;
            while(atual.prox != null){
                atual = atual.prox;
            }
            atual.prox = novo;
        }

    }

    public void carregarArquivo(String arquivo){
        try(BufferedReader leitor = new BufferedReader(new FileReader(arquivo))){
            
            String linha = leitor.readLine();
            
            if(linha == null){
                return;
            }

            int capacidade = Integer.parseInt(linha.trim());
            for(int i = 0; i < capacidade; i++){
                linha = leitor.readLine();
                adicionarCreche(linha);
                }
            leitor.readLine();

            while((linha = leitor.readLine()) != null){

                if(linha.trim().isEmpty()) {
                    continue; 
                }
                String[] partes = linha.split(";");
                if(partes.length < 3){
                    continue;
                }
                int origem = Integer.parseInt(partes[0].trim());
                int destino = Integer.parseInt(partes[1].trim());
                double distancia = Double.parseDouble(partes[2].trim());

                adicionarCaminho(origem, destino, distancia);
            }
            System.out.println("Arquivo carregado com sucesso.");
        } catch (IOException e){
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public String getNomePeloId(int idProcurado) {
        NoCreche atual = inicioCreches;
        while(atual != null){
            if(atual.id == idProcurado){
                return atual.creche;
            }
            atual = atual.prox;
        }
        return "Desconhecido";
    }

    public void numeroDeConexoes(){
        System.out.println("\n=== Relatório de Conexões (Graus) ===");

        NoCreche atual = inicioCreches;
        while(atual != null){
            int idCreche = atual.id;
            int contador = 0;

            for(int coluna = 0; coluna < capacidadeMaxima; coluna++){
                if(matrizConexoes[idCreche][coluna] == 1){
                    contador++;
                }
            }
            System.out.println("A " + atual.creche + " possui " + contador + " conexões.");
            
            atual = atual.prox;
        }
    }

    public void opcoesMenu(){
        NoCreche atual = inicioCreches;
        while(atual != null){
            System.out.println(atual.id + " - " + atual.creche);
            atual = atual.prox;
        }
    }

    public void conexaoEspecifica(int idAlvo){
        if(idAlvo < 0 || idAlvo >= capacidadeMaxima){
            System.out.println("ID de creche inválido.");
            return;
        }
        String nomeAlvo = getNomePeloId(idAlvo);
        Vizinho[] listaVizinhos = new Vizinho[capacidadeMaxima];
        int qtdVizinhos = 0;

        NoDistancia viagem = inicioDistancias;
        
        while(viagem != null){
            if(viagem.origem == idAlvo){
                String nomeVizinho = getNomePeloId(viagem.destino);
                listaVizinhos[qtdVizinhos] = new Vizinho(nomeVizinho, viagem.distancia);
                qtdVizinhos++;
            } else if(viagem.destino == idAlvo){
                String nomeVizinho = getNomePeloId(viagem.origem);
                listaVizinhos[qtdVizinhos] = new Vizinho(nomeVizinho, viagem.distancia);
                qtdVizinhos++;
            }
            viagem = viagem.prox;
        }
        if(qtdVizinhos == 0){
            System.out.println("A creche " + nomeAlvo + " não possui conexões.");
            return;
        }

        for(int i = 0; i < qtdVizinhos; i++){
            for(int j = 0; j < qtdVizinhos - 1 -i; j++){
                if(listaVizinhos[j].distancia > listaVizinhos[j+1].distancia){
                    Vizinho temp = listaVizinhos[j];
                    listaVizinhos[j] = listaVizinhos[j+1];
                    listaVizinhos[j+1] = temp;
                }
            }
        }
        
        System.out.println("\nConexões da creche " + nomeAlvo + ":");
        for(int i = 0; i < qtdVizinhos; i++){
            System.out.println("- " + listaVizinhos[i].nome + " | Distância: " + listaVizinhos[i].distancia + "km");
        }

    }

    public double buscarDistancia(int origem, int destino){
        if(matrizConexoes[origem][destino] == 0){
            return -1;
        }

        NoDistancia atual = inicioDistancias;
        while(atual != null){
            boolean achouIda = (atual.origem == origem && atual.destino == destino);
            boolean achouVolta = (atual.origem == destino && atual.destino == origem);

            if(achouIda || achouVolta){
                return atual.distancia;
            }
            atual = atual.prox;

        }
        return -1;    
    }

    public boolean existeConexao(int origem, int destino){
        if(origem < 0 || origem >= capacidadeMaxima || destino < 0 || destino >= capacidadeMaxima){
            return false;
        }
        
        return matrizConexoes[origem][destino] == 1;
    }

    public void imprimirRelatorio(){
        System.out.println("=== Relatório do Grafo ===");

        NoDistancia viagem = inicioDistancias;
        while(viagem != null){
            String nomeOrigem = getNomePeloId(viagem.origem);
            String nomeDestino = getNomePeloId(viagem.destino);
            
            System.out.println("Rota: " + nomeOrigem + " <--> " + nomeDestino + " | Distância: " + viagem.distancia + "km\n");

            viagem = viagem.prox;
        }
    }
}

class Vizinho {
    String nome;
    double distancia;

    public Vizinho(String nome, double distancia) {
        this.nome = nome;
        this.distancia = distancia;
    }
}

