/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.presenter;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import pessoas.log.LogSingleton;
import pessoas.model.Usuario;
import pessoas.view.UsuarioView;

/**
 *
 * @author mfernandes
 */
public class LoginPresenter {

    HashSet<Usuario> usuarios;
    LogSingleton logSingleton;
    Component view;

    public LoginPresenter(Component view) {
        this.view = view;
        usuarios = new HashSet<>();
        logSingleton = LogSingleton.getInstancia();
        listarUsuarios();
        while (usuarios.size() < 1) {
            addUsuario();
        }
        if (!logSingleton.estaLogado()) {
            login();
        }
    }

    private void listarUsuarios() {
        boolean flag = true;
        for (String linha : logSingleton.getLinhas()) {
            if (linha.contains("usuario adicionado ")) {
                usuarios.add(getUsuario(linha, flag));
                flag = false;
            }
        }
    }

    private Usuario getUsuario(String linha, boolean flag) {
        int inin = 20;
        int fimN = inin + linha.split(" ")[3].length();
        int iniS = fimN + 1;
        int fimS = iniS + +linha.split(" ")[4].length();

        Usuario usuario = new Usuario(
                linha.substring(inin, fimN),
                linha.substring(iniS, fimS),
                flag
        );
        return usuario;
    }

    public void addUsuario() {
        UsuarioView view = new UsuarioView();
        view.setTitle("Novo usuario");
        view.getProntoBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (LoginPresenter.validarCampos(view, view.getNomeTxt(), view.getSenhaTxt())) {
                    adicionar(e, view.getNomeTxt().getText(), view.getSenhaTxt().getText());
                }
                view.setVisible(false);
                view.dispose();
            }
        });
        view.setVisible(true);
        JOptionPane.showMessageDialog(view, "Você precisa estar logado como administrador para adicionar usuarios!");
    }

    private void adicionar(ActionEvent e, String nome, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nome)) {
                JOptionPane.showMessageDialog(view, "Já existe um usuario com este nome");
                return;
            }
        }
        Usuario usuario = new Usuario(nome, senha, usuarios.size() < 1);
        usuarios.add(usuario);
        logSingleton.addUsuario(usuario);
        JOptionPane.showMessageDialog(view, "usuaro " + nome + " adicionado com sucesso");
    }

    public void login() {

        JOptionPane.showMessageDialog(null, "Informe o nome de usuario e senha");

        UsuarioView userView = new UsuarioView();
        userView.setTitle("Login");
        userView.getProntoBtn().addActionListener((ActionEvent e) -> {
            if (validarCampos(view, userView.getNomeTxt(), userView.getSenhaTxt())) {
                logar(userView.getNomeTxt().getText(), userView.getSenhaTxt().getText());
                userView.setVisible(false);
                userView.dispose();
            }
        });
        userView.setVisible(true);
    }

    public boolean logar(String nome, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nome)) {
                if (usuario.comparaSenha(senha)) {
                    logSingleton.setUsuario(usuario);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(view, "Senha inválida");
                }
            }
        }
        JOptionPane.showMessageDialog(view, "Houve um erro ao Logar, Provavelmente este usuario não existe");
        return false;
    }

    public static boolean validarCampos(Component view, JTextField nome, JTextField senha) {

        if (nome.getText().isEmpty() || senha.getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Os campos não podem ser nulos");
            return false;
        }

        if (nome.getText().length() > 8) {
            JOptionPane.showMessageDialog(view, "O tamanho maximo de nome é 8");
            return false;
        }

        if (senha.getText().length() < 3) {
            JOptionPane.showMessageDialog(view, "O tamanho minimo de senha é 3");
            return false;
        }

        boolean containsLetra = false, containsNumber = false;

        for (char c : senha.getText().toCharArray()) {

            Character.isLetter(c);
            containsLetra = true;

            Character.isDigit(c);
            containsNumber = true;

            if (containsLetra && containsNumber) {
                return true;
            }
        }

        if (!containsLetra || !containsNumber) {
            JOptionPane.showMessageDialog(view, "Use letras e numeros na senha");
            return false;
        }
        return false;
    }

}
