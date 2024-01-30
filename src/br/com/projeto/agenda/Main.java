package br.com.projeto.agenda;

import br.com.projeto.agenda.io.AgendaManipulacao;
import br.com.projeto.agenda.modelo.Contato;
import br.com.projeto.agenda.modelo.Telefone;

import java.util.List;
import java.util.Scanner;

public class Main {
    // Códigos de cores ANSI
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void main(String[] args) {
        // Carregar contatos do arquivo no início da execução
        List<Contato> contatos = AgendaManipulacao.carregarDeArquivo();

        // Criar a instância da Agenda com os contatos carregados
        Agenda agenda = new Agenda(contatos);

        Scanner scanner = new Scanner(System.in);

        int escolha;
        do {
            System.out.println(BLUE + "##################");
            System.out.println(BLUE + "##### AGENDA #####");
//            System.out.println(">>>> Contatos <<<<");
//            agenda.exibirContatos();
            System.out.println(PURPLE + " >>>> Menu <<<<" + RESET);
            System.out.println(GREEN + "1" + RESET + " - Adicionar Contato");
            System.out.println(GREEN + "2" + RESET + " - Remover Contato");
            System.out.println(GREEN + "3" + RESET + " - Editar Contato");
            System.out.println(GREEN + "4" + RESET + " - Salvar e Sair");

            escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (escolha) {
                case 1:
                    // Lógica para adicionar contato
                    Contato novoContato = adicionarContato(scanner);
                    agenda.adicionarContato(novoContato);
                    break;
                case 2:
                    // Lógica para remover contato
                    System.out.println(YELLOW + "Informe o ID do contato a ser removido: ");
                    Long idRemover = scanner.nextLong();
                    agenda.removerContato(idRemover);
                    break;
                case 3:
                    // Lógica para editar contato
                    System.out.println(YELLOW + "Informe o ID do contato a ser editado: ");
                    Long idEditar = scanner.nextLong();
                    scanner.nextLine();
                    agenda.editarContato(idEditar, scanner);
                    break;
                case 4:
                    // Salvar contatos no arquivo antes de sair
                    AgendaManipulacao.salvarEmArquivo(agenda.getContatos());
                    System.out.println(YELLOW + "Saindo...");
                    break;
                default:
                    System.out.println(RED + "Opção inválida. Tente novamente.");
            }
        } while (escolha != 4);

        scanner.close();
    }

    private static Contato adicionarContato(Scanner scanner) {
        System.out.println(GREEN + "Informe o nome do contato: ");
        String nome = scanner.nextLine();

        System.out.println(GREEN + "Informe o sobrenome do contato: ");
        String sobrenome = scanner.nextLine();

        // Criar um novo objeto Contato
        Contato novoContato = new Contato(nome, sobrenome);

        // Adicionar telefones
        System.out.println(GREEN + "Adicionar telefones " + YELLOW + "(Digite '0' para encerrar): ");
        while (true) {
            System.out.println(GREEN + "Informe o DDD do telefone " + YELLOW + "(ou 0 para encerrar): ");
            String ddd = scanner.nextLine();

            if (ddd.equals("0")) {
                break;
            }

            System.out.println(GREEN + "Informe o número do telefone: ");
            Long numero = Long.parseLong(scanner.nextLine());

            Telefone telefone = new Telefone(ddd, numero);
            novoContato.adicionarTelefone(telefone);
        }

        return novoContato;
    }

}