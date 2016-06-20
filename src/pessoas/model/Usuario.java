/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author mfernandes
 */
public class Usuario implements Comparable<Usuario> {

    private final String nome;
    private final String senha;
    private final boolean isAdministrador;

    public Usuario(String nome, String senha, boolean isAdministrador) {
        this.nome = nome;
        this.senha = senha;
        this.isAdministrador = isAdministrador;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAdministrador() {
        return isAdministrador;
    }

    public boolean comparaSenha(String senha) {
        return this.senha.equals(senha);
    }

    public String getHash() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        return hexString.toString();
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
