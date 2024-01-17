import java.io.*;
import java.util.*;

public class AgendaTelefonica {
    private String[] nomes;
    private String[] telefones;
    private int quantidadeContatos;

    public AgendaTelefonica(int capacidade) {
        nomes = new String[capacidade];
        telefones = new String[capacidade];
        quantidadeContatos = 0;
    }

    public void adicionarContato(String nome, String telefone) {
        if (quantidadeContatos < nomes.length) {
            nomes[quantidadeContatos] = nome;
            telefones[quantidadeContatos] = telefone;
            quantidadeContatos++;
            System.out.println("Contato adicionado: " + nome + " - " + telefone);
            salvarContatos();
        } else {
            System.out.println("A agenda está cheia. Não é possível adicionar mais contatos.");
        }
    }

    public void buscarContato(String nome) {
        boolean encontrado = false;
        for (int i = 0; i < quantidadeContatos; i++) {
            if (nomes[i].equalsIgnoreCase(nome)) {
                System.out.println("Telefone de " + nome + ": " + telefones[i]);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Contato não encontrado para o nome: " + nome);
        }
    }
    public void excluirContato(String nome) {
    for (int i = 0; i < quantidadeContatos; i++) {
        if (nomes[i].equalsIgnoreCase(nome)) {
            for (int j = i; j < quantidadeContatos - 1; j++) {
                nomes[j] = nomes[j + 1];
                telefones[j] = telefones[j + 1];
            }
            nomes[quantidadeContatos - 1] = null;
            telefones[quantidadeContatos - 1] = null;
            quantidadeContatos--;
            System.out.println("Contato excluído: " + nome);
            salvarContatos();
            return;
        }
    }
    System.out.println("Contato não encontrado para o nome: " + nome);
}


    public void salvarContatos() {
        try (PrintWriter writer = new PrintWriter("contatos.txt")) {
            for (int i = 0; i < quantidadeContatos; i++) {
                writer.println(nomes[i] + ";" + telefones[i]);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar contatos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        final int CAPACIDADE = 100; 
        AgendaTelefonica agenda = new AgendaTelefonica(CAPACIDADE);
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

       
        try (BufferedReader reader = new BufferedReader(new FileReader("contatos.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                String nome = partes[0];
                String telefone = partes[1];
                agenda.adicionarContato(nome, telefone);
            }
        } catch (IOException e) {
            System.out.println("Arquivo de contatos não encontrado ou erro ao ler o arquivo.");
        }

        while (opcao != 3) {
            System.out.println("\nSelecione a opção:");
            System.out.println("1 - Adicionar Contato");
            System.out.println("2 - Buscar Contato");
            System.out.println("3 - Excluir contato");
            System.out.println("4 - Sair");

            String entrada = scanner.nextLine();

            if (entrada.matches("\\d+")) {
                opcao = Integer.parseInt(entrada);

                switch (opcao) {
                    case 1:
                        System.out.println("Digite o nome do contato:");
                        String nome = scanner.nextLine();
                        System.out.println("Digite o telefone do contato:");
                        String telefone = scanner.nextLine();
                        agenda.adicionarContato(nome, telefone);
                        break;
                    case 2:
                        System.out.println("Digite o nome para buscar:");
                        String nomeBuscar = scanner.nextLine();
                        agenda.buscarContato(nomeBuscar);
                        break;
                    case 4:
                        System.out.println("Encerrando a agenda telefônica...");
                        break;
                    case 3:
                        System.out.println("Digite o nome do contato a ser excluído:");
                        String nomeExcluir = scanner.nextLine();
                        agenda.excluirContato(nomeExcluir);
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            } else {
                System.out.println("Por favor, digite um número válido.");
            }
        }

        scanner.close();
    }
}
