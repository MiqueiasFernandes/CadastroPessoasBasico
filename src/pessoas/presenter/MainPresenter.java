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
import pessoas.collection.IPessoaDAO;
import pessoas.model.Login;
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

        Login.getInstancia().setView(view);
        Login.getInstancia().carregaDAOUsuario();

        pessoas = new PessoaDAOProxy(view);

        view.setLocationRelativeTo(view.getParent());

        view.getAdicionarJItem().addActionListener((ActionEvent e) -> {
            adicionar(e);
        });

        view.getListarPessoasJItem().addActionListener((ActionEvent e) -> {
            listarPessoas(e);
        });

        view.getConfigurarJItem().addActionListener((ActionEvent e) -> {
            configurar(e);
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
        InclusaoPessoaPresenter presenterInclusaoPessoa = new InclusaoPessoaPresenter(pessoas);
        addFrame(presenterInclusaoPessoa.getView());
    }

    private void listarPessoas(ActionEvent e) {
        ListaPessoasPresenter listaPessoas = new ListaPessoasPresenter(pessoas);
        addFrame(listaPessoas.getView());
    }

    private void configurar(ActionEvent e) {
        ConfiguracaoPresenter configuracaoPresenter = new ConfiguracaoPresenter();
        addFrame(configuracaoPresenter.getView());
    }

    private void sair(ActionEvent e) {
        view.setVisible(false);
        view.dispose();
    }

}
