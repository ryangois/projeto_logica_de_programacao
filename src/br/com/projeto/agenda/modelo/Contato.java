package br.com.projeto.agenda.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Contato implements Comparable<Contato> {

    private static final AtomicLong geradorIds = new AtomicLong(1);

    private Long id;
    private String nome;
    private String sobrenome;
    private List<Telefone> telefones;

    public Contato(String nome, String sobrenome) {
        this.id = geradorIds.getAndIncrement();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefones = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void adicionarTelefone(Telefone telefone) {
        telefones.add(telefone);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(" | ").append(nome).append(" ").append(sobrenome).append("\n");

        for (Telefone telefone : telefones) {
            stringBuilder.append("(").append(telefone.getDdd()).append(") ").append(telefone.getNumero()).append(" ");
        }

        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Contato outroContato) {
        // Comparação por nome e sobrenome
        int resultadoNome = this.nome.compareTo(outroContato.nome);
        if (resultadoNome == 0) {
            return this.sobrenome.compareTo(outroContato.sobrenome);
        }
        return resultadoNome;
    }
}
