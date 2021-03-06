package pessoas.model;

import java.io.Serializable;

public class Pessoa implements Comparable<Pessoa>, Serializable {

    private final String nome;
    private final String telefone;

    public Pessoa(String pNome, String pTelefone) {
        this.nome = pNome;
        this.telefone = pTelefone;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    @Override
    public String toString() {
        return "" + this.nome
                + "," + this.telefone;
    }

    @Override
    public int compareTo(Pessoa pessoa) {
        return nome.compareTo(pessoa.nome);
    }
}
