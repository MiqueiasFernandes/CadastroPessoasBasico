/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.presenter;

import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import pessoas.log.ILogDAO;
import pessoas.log.LogDAOTXT;
import pessoas.log.LogDAOXML;
import pessoas.log.LogSingleton;
import pessoas.model.LoginSingleton;
import pessoas.view.ConfiguracaoView;

/**
 *
 * @author mfernandes
 */
public class ConfiguracaoPresenter {

    private ConfiguracaoView view;
    private LoginSingleton login;

    public ConfiguracaoPresenter() throws Exception {
        this.view = new ConfiguracaoView();
        this.login = LoginSingleton.getInstancia();
        this.view.getUsuarioLbl().setText(login.getUsuarioLogado().getNome());

        this.view.getAdicionarBtn().addActionListener((ActionEvent e) -> {
            try {
                adicionarUsuario(e);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao adicionar usuario. \n" + ex);
            }
        });

        this.view.getSairBtn().addActionListener((ActionEvent e) -> {
            try {
                sairBtn(e);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao efetuar logout\n" + ex);
            }
        });

        this.view.getTipoLogJComboBox().addItem(new LogDAOTXT());
        this.view.getTipoLogJComboBox().addItem(new LogDAOXML());

        this.view.getTipoLogJComboBox().addActionListener((ActionEvent e) -> {
            tipoLogAlterado(e, view.getTipoLogJComboBox());
        });

        this.view.getAdminCheckBox().setEnabled(login.getUsuarioLogado().isAdministrador());
        this.view.setVisible(true);
    }

    void tipoLogAlterado(ActionEvent e, JComboBox<ILogDAO> jComboBox) {
        try {
            LogSingleton.getInstancia().setLogdao(jComboBox.getItemAt(jComboBox.getSelectedIndex()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(jComboBox, "Não foi possivel persistir as alterações. \n" + ex);
        }
    }

    public ConfiguracaoView getView() {
        return view;
    }

    public void sairBtn(ActionEvent e) throws Exception {
        login.sair();
        view.setVisible(false);
        view.dispose();
    }

    public void adicionarUsuario(ActionEvent e) throws Exception {
        if (login.getUsuarioLogado().isAdministrador()) {
            login.getInstancia().adicionaUsuario(false);
        } else {
            JOptionPane.showMessageDialog(view,
                    "Você precisa estar logado como administrador para adicionar usuarios!");
        }
    }

}
