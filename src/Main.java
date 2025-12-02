import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        boolean cont = true;
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
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = ler.nextInt();
        
            switch (escolha) {
                case 1:
                    grafo.imprimirRelatorio();
                    break;

                case 2:
                    System.out.println("\n--- Escolha a Creche ---");
                    grafo.opcoesMenu();
                    System.out.print("Digite o número da creche: ");
                    int idDigitado = ler.nextInt();
                    grafo.conexaoEspecifica(idDigitado);
                    break;

                case 0:
                    cont = false;
                    System.out.println("Encerrando o programa.");
                    break;
            
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        
        }
    }
}
