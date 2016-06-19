package pessoas.model;

import java.io.Serializable;

public class Pessoa implements Comparable<Pessoa>, Serializable{
    private String nome;
    private String telefone;


    public Pessoa(String pNome, String pTelefone){
        this.nome = pNome;
        this.telefone = pTelefone;
    }


    public String getNome(){
        return this.nome;
    }

    public String getTelefone(){
        return this.telefone;
    }

    @Override
    public String toString() {
        return "" + this.nome
                + ","+ this.telefone;
    }

    public int compareTo(Pessoa o) {
        return nome.compareTo(o.nome);
    }
}  