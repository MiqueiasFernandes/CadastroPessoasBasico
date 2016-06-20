/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pessoas.view.MainView;

/**
 *
 * @author mfernandes
 */
public class MainPresenter {

    public static void main(String[] args) {
        final MainView view = new MainView();

        view.setLocationRelativeTo(view.getParent());

        view.getAdicionarJItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionar(e, view);
            }

        });

        view.getListarPessoasJItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPessoas(e, view);
            }
        });

        view.getSairJItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sair(e, view);
            }
        });

        view.setVisible(true);
    }

    private static void adicionar(ActionEvent e, MainView view) {
        InclusaoPessoaPresenter presenterInclusaoPessoa = new InclusaoPessoaPresenter(view);
    }

    private static void listarPessoas(ActionEvent e, MainView view) {
        ListaPessoasPresenter listaPessoas = new ListaPessoasPresenter(view);
    }

    private static void sair(ActionEvent e, MainView view) {
        view.setVisible(false);
        view.dispose();
    }

}
