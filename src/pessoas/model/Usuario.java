/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public class Usuario implements Comparable<Usuario> {

    private final String nome;
    private final String senha;
    private final boolean isadministrador;

    public Usuario(String nome, String senha, boolean isAdministrador) {
        this.nome = nome;
        this.senha = senha;
        this.isadministrador = isAdministrador;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAdministrador() {
        return isadministrador;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nome=" + nome + ", senha=" + senha + '}';
    }

    @Override
    public int compareTo(Usuario other) {
        return nome.compareTo(other.getNome());
    }

}
