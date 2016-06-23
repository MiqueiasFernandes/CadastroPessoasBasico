/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.presenter;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import pessoas.collection.IPessoaDAO;
import pessoas.model.LoginSingleton;
import pessoas.proxy.PessoaDAOProxy;
import pessoas.view.MainView;

/**
 *
 * @author mfernandes
 */
public class MainPresenter {

    private final MainView view;
    private final IPessoaDAO pessoas;

    public MainPresenter() throws FileNotFoundException, IOException, Exception {
        view = new MainView();

        LoginSingleton login = LoginSingleton.getInstancia();
        login.setView(view);
        login.carregaDAOUsuario();

        pessoas = new PessoaDAOProxy(view);

        view.setLocationRelativeTo(view.getParent());

        view.getAdicionarJItem().addActionListener((ActionEvent e) -> {
            adicionar(e);
        });

        view.getListarPessoasJItem().addActionListener((ActionEvent e) -> {
            try {
                listarPessoas(e);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro: " + ex);
            }
        });

        view.getConfigurarJItem().addActionListener((ActionEvent e) -> {
            try {
                configurar(e);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro: " + ex);
            }
        });

        view.getSairJItem().addActionListener((ActionEvent e) -> {
            sair(e);
        });

        view.setVisible(true);
    }

    void addFrame(JInternalFrame frame) {
        view.getjDesktopPane().add(frame);
        view.pack();
    }

    private void adicionar(ActionEvent e) {
        if (LoginSingleton.getInstancia().estaLogado()) {
            InclusaoPessoaPresenter presenterInclusaoPessoa = new InclusaoPessoaPresenter(pessoas);
            addFrame(presenterInclusaoPessoa.getView());
        } else {
            JOptionPane.showMessageDialog(view, "É necessario logar");
        }
    }

    private void listarPessoas(ActionEvent e) throws Exception {
        if (LoginSingleton.getInstancia().estaLogado()) {
            ListaPessoasPresenter listaPessoas = new ListaPessoasPresenter(pessoas);
            addFrame(listaPessoas.getView());
        } else {
            JOptionPane.showMessageDialog(view, "É necessario logar");
        }
    }

    private void configurar(ActionEvent e) throws Exception {
        if (LoginSingleton.getInstancia().estaLogado()) {
            ConfiguracaoPresenter configuracaoPresenter = new ConfiguracaoPresenter();
            addFrame(configuracaoPresenter.getView());
        } else {
            JOptionPane.showMessageDialog(view, "É necessario logar");
        }
    }

    private void sair(ActionEvent e) {
        view.setVisible(false);
        view.dispose();
    }

}
