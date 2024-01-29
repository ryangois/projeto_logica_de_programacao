package br.com.projeto.agenda;

import br.com.projeto.agenda.io.AgendaManipulacao;
import br.com.projeto.agenda.modelo.Contato;
import br.com.projeto.agenda.modelo.Telefone;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Agenda {

    private List<Contato> contatos;

    public Agenda(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void adicionarContato(Contato contato) {
        if (!contatos.contains(contato)) {
            contatos.add(contato);
            AgendaManipulacao.salvarEmArquivo(contatos);
            System.out.println("Contato adicionado com sucesso!");
        } else {
            System.out.println("Erro: Já existe um contato com o mesmo ID.");
        }
    }

    public void removerContato(Long id) {
        Contato contatoRemover = null;
        for (Contato contato : contatos) {
            if (contato.getId().equals(id)) {
                contatoRemover = contato;
                break;
            }
        }

        if (contatoRemover != null) {
            contatos.remove(contatoRemover);
            AgendaManipulacao.salvarEmArquivo(contatos);
            System.out.println("Contato removido com sucesso!");
        } else {
            System.out.println("Erro: Contato não encontrado com o ID fornecido.");
        }
    }

    public void editarContato(Long id, Scanner scanner) {
        Contato contatoEditar = null;
        for (Contato contato : contatos) {
            if (contato.getId().equals(id)) {
                contatoEditar = contato;
                break;
            }
        }

        if (contatoEditar != null) {
            contatos.remove(contatoEditar);

            System.out.println("Informe o novo nome do contato: ");
            String novoNome = scanner.nextLine();

            System.out.println("Informe o novo sobrenome do contato: ");
            String novoSobrenome = scanner.nextLine();

            // Atualizar os dados do contato
            contatoEditar.setNome(novoNome);
            contatoEditar.setSobrenome(novoSobrenome);

            // Editar os telefones
            editarTelefones(scanner, contatoEditar);

            contatos.add(contatoEditar);
            AgendaManipulacao.salvarEmArquivo(contatos);
            System.out.println("Contato editado com sucesso!");
        } else {
            System.out.println("Erro: Contato não encontrado com o ID fornecido.");
        }
    }

    private void editarTelefones(Scanner scanner, Contato contato) {
        contato.getTelefones().clear(); // Limpar os telefones existentes

        System.out.println("Adicionar telefones (Digite '0' para encerrar): ");
        while (true) {
            System.out.println("Informe o DDD do telefone (ou 0 para encerrar): ");
            String ddd = scanner.nextLine();

            if (ddd.equals("0")) {
                break;
            }

            System.out.println("Informe o número do telefone: ");
            Long numero = Long.parseLong(scanner.nextLine());

            Telefone telefone = new Telefone(null, ddd, numero);
            contato.adicionarTelefone(telefone);
        }
    }

    public void exibirContatos() {
        for (Contato contato : contatos) {
            System.out.println(contato);
        }
    }

    public static void main(String[] args) {
        // Carregar contatos do arquivo no início da execução
        List<Contato> contatos = AgendaManipulacao.carregarDeArquivo();

        // Criar a instância da Agenda com os contatos carregados
        Agenda agenda = new Agenda(contatos);

        Scanner scanner = new Scanner(System.in);

        int escolha;
        do {
            System.out.println("##################");
            System.out.println("##### AGENDA #####");
//            System.out.println(">>>> Contatos <<<<");
//            agenda.exibirContatos();
            System.out.println(">>>> Menu <<<<");
            System.out.println("1 - Adicionar Contato");
            System.out.println("2 - Remover Contato");
            System.out.println("3 - Editar Contato");
            System.out.println("4 - Sair");

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
                    System.out.println("Informe o ID do contato a ser removido: ");
                    Long idRemover = scanner.nextLong();
                    agenda.removerContato(idRemover);
                    break;
                case 3:
                    // Lógica para editar contato
                    System.out.println("Informe o ID do contato a ser editado: ");
                    Long idEditar = scanner.nextLong();
                    scanner.nextLine();
                    agenda.editarContato(idEditar, scanner);
                    break;
                case 4:
                    // Salvar contatos no arquivo antes de sair
                    AgendaManipulacao.salvarEmArquivo(agenda.getContatos());
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 4);

        scanner.close();
    }

    private static Contato adicionarContato(Scanner scanner) {
        System.out.println("Informe o nome do contato: ");
        String nome = scanner.nextLine();

        System.out.println("Informe o sobrenome do contato: ");
        String sobrenome = scanner.nextLine();

        // Criar um novo objeto Contato
        Contato novoContato = new Contato(nome, sobrenome);

        // Adicionar telefones
        System.out.println("Adicionar telefones (Digite '0' para encerrar): ");
        while (true) {
            System.out.println("Informe o DDD do telefone (ou 0 para encerrar): ");
            String ddd = scanner.nextLine();

            if (ddd.equals("0")) {
                break;
            }

            System.out.println("Informe o número do telefone: ");
            Long numero = Long.parseLong(scanner.nextLine());

            Telefone telefone = new Telefone(null, ddd, numero);
            novoContato.adicionarTelefone(telefone);
        }

        return novoContato;
    }
}
