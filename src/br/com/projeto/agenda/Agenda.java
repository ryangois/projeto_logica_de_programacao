package br.com.projeto.agenda;

import br.com.projeto.agenda.io.AgendaManipulacao;
import br.com.projeto.agenda.modelo.Contato;
import br.com.projeto.agenda.modelo.Telefone;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Agenda {
    // Códigos de cores ANSI
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    private List<Contato> contatos;

    public Agenda(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void adicionarContato(Contato contato) {
        if (!contatos.contains(contato) && !temContatoComMesmosTelefones(contato)) {
            contatos.add(contato);
            AgendaManipulacao.salvarEmArquivo(contatos);
            System.out.println(GREEN + "Contato adicionado com sucesso!");
        } else {
            System.out.println(RED + "Erro: Já existe um contato com o mesmo ID ou telefones duplicados.");
        }
    }

    private boolean temContatoComMesmosTelefones(Contato novoContato) {
        for (Contato contatoExistente : contatos) {
            if (contatoExistente.temMesmosTelefones(novoContato)) {
                return true;
            }
        }
        return false;
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
            System.out.println(GREEN + "Contato removido com sucesso!");
        } else {
            System.out.println(RED + "Erro: Contato não encontrado com o ID fornecido.");
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

            System.out.println(BLUE + "Informe o novo nome do contato: ");
            String novoNome = scanner.nextLine();

            System.out.println(BLUE + "Informe o novo sobrenome do contato: ");
            String novoSobrenome = scanner.nextLine();

            // Atualizar os dados do contato
            contatoEditar.setNome(novoNome);
            contatoEditar.setSobrenome(novoSobrenome);

            // Editar os telefones
            editarTelefones(scanner, contatoEditar);

            contatos.add(contatoEditar);
            AgendaManipulacao.salvarEmArquivo(contatos);
            System.out.println(GREEN + "Contato editado com sucesso!");
        } else {
            System.out.println(RED + "Erro: Contato não encontrado com o ID fornecido.");
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

            Telefone telefone = new Telefone(ddd, numero);
            contato.adicionarTelefone(telefone);
        }
    }
    public void exibirContatos() {
        for (Contato contato : contatos) {
            System.out.println(contato);
        }
    }
}
