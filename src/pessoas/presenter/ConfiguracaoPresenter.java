/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import pessoas.log.LogStrategy;
import pessoas.log.LogStrategyTEXT;
import pessoas.log.LogStrategyXML;
import pessoas.log.LogSingleton;
import pessoas.model.Usuario;
import pessoas.view.ConfiguracaoView;
import pessoas.view.MainView;
import pessoas.view.UsuarioView;

/**
 *
 * @author mfernandes
 */
public class ConfiguracaoPresenter {

    ConfiguracaoView view;

    public ConfiguracaoPresenter(MainView mainView) {
        this.view = new ConfiguracaoView();

        this.view.getUsuarioLbl().setText(LogSingleton.getInstancia().getUsuario().getNome());

        this.view.getAdicionarBtn().addActionListener((ActionEvent e) -> {
            adicionarUsuario(e);
        });

        this.view.getSairBtn().addActionListener((ActionEvent e) -> {
            sairBtn(e);
        });

        try {
            this.view.getTipoLogJComboBox().addItem(new LogStrategyTEXT());
            this.view.getTipoLogJComboBox().addItem(new LogStrategyXML());
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(mainView, "ERRO " + ex);
        }

        this.view.getTipoLogJComboBox().addActionListener((ActionEvent e) -> {
            tipoLogAlterado(e, view.getTipoLogJComboBox());
        });

        this.view.setLocationRelativeTo(mainView);
        this.view.setVisible(true);
    }

    void tipoLogAlterado(ActionEvent e, JComboBox<LogStrategy> jComboBox) {
        LogSingleton.getInstancia().setTipoLog(jComboBox.getItemAt(jComboBox.getSelectedIndex()));
    }

    public ConfiguracaoView getView() {
        return view;
    }

    public void sairBtn(ActionEvent e) {
        LogSingleton.getInstancia().setUsuario(null);
    }

    public void adicionarUsuario(ActionEvent e) {

        UsuarioView view = new UsuarioView();

        view.getProntoBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (LoginPresenter.validarCampos(view, view.getNomeTxt(), view.getSenhaTxt())) {
                    adicionar(e, view.getNomeTxt().getText(), view.getSenhaTxt().getText());
                }

            }
        });

        view.setVisible(true);
    }

    void adicionar(ActionEvent e, String nome, String senha) {

        LogSingleton logSingleton = LogSingleton.getInstancia();

        if (logSingleton.estaLogado() && logSingleton.getUsuario().isAdministrador()) {

            logSingleton.getPresenter().addUsuario();

        } else {
            JOptionPane.showMessageDialog(view, "Você precisa estar logado como administrador para adicionar usuarios!");
        }
    }

}
