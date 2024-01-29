package br.com.projeto.agenda.io;

import br.com.projeto.agenda.modelo.Contato;
import br.com.projeto.agenda.modelo.Telefone;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaManipulacao {

    private static final String NOME_ARQUIVO = "contatos.txt";

    public static void salvarEmArquivo(List<Contato> contatos) {
        // Ordena a lista de contatos em ordem alfabetica
        contatos.sort(Contato::compareTo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Contato contato : contatos) {
                writer.write(contato.getId() + ": " + contato.getNome() + " " + contato.getSobrenome() + ", ");

                // Adicionando telefones ao arquivo
                List<Telefone> telefones = contato.getTelefones();
                if (!telefones.isEmpty()) {
                    writer.write("(" + telefones.get(0).getDdd() + ") " + telefones.get(0).getNumero());
                    for (int i = 1; i < telefones.size(); i++) {
                        writer.write(" / (" + telefones.get(i).getDdd() + ") " + telefones.get(i).getNumero());
                    }
                }

                writer.newLine();
            }
            System.out.println("Agenda salva no arquivo " + NOME_ARQUIVO + ".");
        } catch (IOException e) {
            System.out.println("Erro ao salvar a agenda no arquivo: " + e.getMessage());
        }
    }

    public static List<Contato> carregarDeArquivo() {
        List<Contato> contatos = new ArrayList<>();

        File arquivo = new File(NOME_ARQUIVO);

        // Verifica se o arquivo existe, se não existir, cria-o
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
                System.out.println("Arquivo " + NOME_ARQUIVO + " criado.");
            } catch (IOException e) {
                System.out.println("Erro ao criar o arquivo " + NOME_ARQUIVO + ".");
                e.printStackTrace();
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(": ", 2); // Divisão em até 2 partes usando ": "

                // Verifica se há pelo menos dois elementos no array
                if (partes.length == 2) {
                    Long id = Long.parseLong(partes[0].trim());
                    String[] nomeSobrenomeTelefones = partes[1].trim().split(", ");

                    String[] nomeSobrenome = nomeSobrenomeTelefones[0].trim().split(" ");
                    Contato contato = new Contato(nomeSobrenome[0], nomeSobrenome[1]);

                    // Adicionando telefones ao contato
                    if (nomeSobrenomeTelefones.length > 1) {
                        String[] telefones = nomeSobrenomeTelefones[1].trim().split(" / ");
                        for (String telefoneStr : telefones) {
                            String[] dddNumero = telefoneStr.trim().split(" ");
                            Telefone telefone = new Telefone(dddNumero[0].replace("(", "").replace(")", ""),
                                    Long.parseLong(dddNumero[1]));
                            contato.getTelefones().add(telefone);
                        }
                    }

                    contatos.add(contato);
                } else {
                    System.out.println("Formato inválido na linha: " + linha);
                }
            }

            System.out.println("Agenda carregada do arquivo " + NOME_ARQUIVO + ".");
        } catch (IOException e) {
            System.out.println("Erro ao carregar a agenda do arquivo " + NOME_ARQUIVO + ".");
            e.printStackTrace();
        }

        return contatos;
    }
}