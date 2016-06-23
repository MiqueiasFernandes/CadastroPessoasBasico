/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.model;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import pessoas.collection.IUsuarioDAO;
import pessoas.log.LogSingleton;
import pessoas.view.MainView;
import pessoas.view.UsuarioView;

/**
 *
 * @author mfernandes
 */
public final class LoginSingleton {

    private IUsuarioDAO usuarios;
    private Usuario usuarioLogado = null;
    private static LoginSingleton instancia;
    private MainView view;

    private LoginSingleton() {
    }

    public static LoginSingleton getInstancia() {
        if (instancia == null) {
            instancia = new LoginSingleton();
        }
        return instancia;
    }

    public void carregaDAOUsuario() throws IOException, Exception {
        IUsuarioDAO classeDAO;

        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("data/dao.properties");
        properties.load(fis);
        String dao = properties.getProperty("daoUsuario");

        Class classe = Class.forName(dao);
        Constructor constructor = classe.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();

        classeDAO = (IUsuarioDAO) instance;

        usuarios = classeDAO;
        usuarios.carregaUsuarios();
        if (usuarios.count() < 1) {
            adicionaUsuario(true);
        }
    }

    public void adicionaUsuario(boolean administrador) {
        JOptionPane.showMessageDialog(view, "Informe um nome de usuario e uma senha");
        UsuarioView userView = new UsuarioView();
        userView.setTitle("Adicionar Usuario");
        userView.getProntoBtn().addActionListener((ActionEvent e) -> {
            if (validarCampos(userView.getNomeTxt(), userView.getSenhaTxt())) {
                try {
                    Usuario usuario = new Usuario(userView.getNomeTxt().getText(),
                            new String(userView.getSenhaTxt().getPassword()),
                            administrador);

                    usuarios.add(usuario);
                    userView.setVisible(false);
                    userView.dispose();

                    LogSingleton.getInstancia().addUsuario(usuario);
                    JOptionPane.showMessageDialog(view, "O usuario foi adicionado com sucesso");
                    autenticar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }
            }
        });
        view.getjDesktopPane().add(userView);
        userView.setVisible(true);
        userView.toFront();
    }

    public boolean estaLogado() {
        return usuarioLogado != null;
    }

    public Usuario getUsuarioLogado() throws Exception {
        autenticar();
        return usuarioLogado;
    }

    public Usuario getUsuario() {
        return usuarioLogado;
    }

    public void autenticar() throws Exception {
        if (!estaLogado()) {
            if (usuarios.count() > 0) {
                entrar();
            } else {
                JOptionPane.showMessageDialog(view, "É necessario cadastrar o administrador");
            }
        }
    }

    public void sair() throws Exception {
        LogSingleton.getInstancia().loginUsuario(usuarioLogado, true);
        usuarioLogado = null;
        JOptionPane.showMessageDialog(view, "Bye Bye");
        entrar();
    }

    public void entrar() {
        JOptionPane.showMessageDialog(view, "Informe o nome de usuario e senha para entrar");
        UsuarioView userView = new UsuarioView();
        userView.setTitle("Entrar");
        userView.getProntoBtn().addActionListener((ActionEvent e) -> {
            if (validarCampos(userView.getNomeTxt(), userView.getSenhaTxt())) {
                if (logar(userView.getNomeTxt().getText(),
                        new String(userView.getSenhaTxt().getPassword()))) {
                    userView.setVisible(false);
                    userView.dispose();
                    try {
                        LogSingleton.getInstancia().loginUsuario(usuarioLogado, false);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, "Erro ao persistir log de login\n" + ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "O login falhou, tente novamente");
                }
            }
        });
        view.getjDesktopPane().add(userView);
        userView.setVisible(true);
    }

    public boolean logar(String nome, String senha) {
        Usuario usuario = usuarios.getUsuarioByName(nome);
        if (usuario == null) {
            JOptionPane.showMessageDialog(view, "Usuario inválido");
            return false;
        }
        if (comparaSenhas(usuario.getSenha(), senha)) {
            usuarioLogado = usuario;
            return true;
        } else {
            JOptionPane.showMessageDialog(view, "Senha inválida");
        }
        return false;
    }

    private boolean comparaSenhas(String senha1, String senha2) {
        return senha1.equals(senha2);
    }

    public boolean validarCampos(JTextField nome, JTextField senha) {

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

    public void setView(MainView view) {
        this.view = view;
    }

}
