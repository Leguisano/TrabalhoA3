import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        boolean cont = true;
        int idDigitado=0,idA=0,idB=0;
        int escolha=0;
        int crecheEscolhida=0;
        LeituraGrafo grafo = new LeituraGrafo(20);

        grafo.carregarArquivo("grafo.txt");

        //grafo.imprimirRelatorio();
        //grafo.numeroDeConexoes();

        while(cont){
            System.out.println("----- Menu -----");
            System.out.println("1. Ver todas as conexões de creches");
            System.out.println("2. Ver conexão de creches específicas");
            System.out.println("3. Ver distância entre duas creches");
            System.out.println("4. Adicionar nova conexão");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = ler.nextInt();
        
            switch (escolha) {
                case 1:
                    grafo.numeroDeConexoes();
                    break;

                case 2:
                    System.out.println("\n--- Escolha a Creche ---");
                    grafo.opcoesMenu();
                    System.out.print("Digite o número da creche: ");
                    idDigitado = ler.nextInt();
                    grafo.conexaoEspecifica(idDigitado);
                    break;

                case 3:
                    System.out.println("\n--- Escolha a Primeira Creche ---");
                    grafo.opcoesMenu();
                    idA = ler.nextInt();
                    System.out.println("\n--- Escolha a Segunda Creche ---");
                    grafo.opcoesMenu();
                    idB = ler.nextInt();

                    double distancia = grafo.buscarDistancia(idA, idB);

                    if(distancia == -1){
                        System.out.println("Não há conexão direta entre as creches selecionadas.\n");
                    } else {
                        String nomeA = grafo.getNomePeloId(idA);
                        String nomeB = grafo.getNomePeloId(idB);
                        System.out.println("A distância entre " + nomeA + " e " + nomeB + " é de " + distancia + "km.\n");
                    }
                    break;

                case 4:
                    System.out.println("\n--- Adicionar Nova Conexão ---");
                    System.out.println("Escolha a Primeira Creche:");
                    grafo.opcoesMenu();
                    idA = ler.nextInt();
                    System.out.println("Escolha a Segunda Creche:");
                    grafo.opcoesMenu();
                    idB = ler.nextInt();

                    if(idA == idB){
                        System.out.println("Não é possível conectar uma creche a ela mesma.\n");
                        break;
                    }

                    if(grafo.existeConexao(idA, idB)){
                        System.out.println("Já existe uma conexão entre essas creches.\n");
                        break;
                    }
                    System.out.println("Digite a distância entre as creches (em km):");
                    double novaDistancia = ler.nextDouble();

                    grafo.adicionarCaminho(escolha, crecheEscolhida, novaDistancia);
                    System.out.println("Conexão adicionada com sucesso!\n");
                    
                    break;

                case 0:
                    cont = false;
                    System.out.println("Encerrando o programa.");
                    break;
            
                default:
                    System.out.println("Opção inválida. Tente novamente.\n");
                    break;
            }
        
        }
    }
}
