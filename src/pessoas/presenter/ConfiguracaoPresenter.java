/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import pessoas.log.ILogDAO;
import pessoas.log.LogDAOTXT;
import pessoas.log.LogSingleton;
import pessoas.view.ConfiguracaoView;
import pessoas.view.MainView;
import pessoas.view.UsuarioView;

/**
 *
 * @author mfernandes
 */
public class ConfiguracaoPresenter {

    private ConfiguracaoView view;

    public ConfiguracaoPresenter(MainView mainView) {
        this.view = new ConfiguracaoView();

        this.view.getUsuarioLbl().setText(LogSingleton.getInstancia().getUsuario().getNome());

        this.view.getAdicionarBtn().addActionListener((ActionEvent e) -> {
            adicionarUsuario(e);
        });

        this.view.getSairBtn().addActionListener((ActionEvent e) -> {
            sairBtn(e);
        });

        this.view.getTipoLogJComboBox().addItem(new LogDAOTXT());

        this.view.getTipoLogJComboBox().addActionListener((ActionEvent e) -> {
            tipoLogAlterado(e, view.getTipoLogJComboBox());
        });

        mainView.getjDesktopPane().add(view);
        this.view.setVisible(true);
    }

    void tipoLogAlterado(ActionEvent e, JComboBox<ILogDAO> jComboBox) {
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
