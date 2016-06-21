/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.presenter;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import pessoas.collection.IPessoaDAO;
import pessoas.log.ILogDAO;
import pessoas.log.LogSingleton;
import pessoas.view.MainView;

/**
 *
 * @author mfernandes
 */
public class MainPresenter {

    private MainView view;
    private IPessoaDAO pessoas;
    private ILogDAO logDao;

    public MainPresenter() {
        view = new MainView();

        pessoas = carregaDAOPessoa();
        logDao = carregaDAOLog();

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

        //  LogSingleton.getInstancia().setPresenter(new LoginPresenter(view));
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
        ConfiguracaoPresenter configuracaoPresenter = new ConfiguracaoPresenter(view);
        addFrame(configuracaoPresenter.getView());
    }

    private void sair(ActionEvent e) {
        view.setVisible(false);
        view.dispose();
    }

    public IPessoaDAO carregaDAOPessoa() {
        IPessoaDAO classeDAO = null;
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("data/dao.properties");
            properties.load(fis);
            String dao = properties.getProperty("daoPessoa");

            Class classe = Class.forName(dao);
            Constructor constructor = classe.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();

            classeDAO = (IPessoaDAO) instance;

        } catch (Exception e) {
            try {
                throw new Exception("Não foi possível carregar a classe: \n\n\t" + e.getMessage()
                        + "\n\n Atribua uma clase válida à chave \"dao\", no arquivo dao.properties ");
            } catch (Exception ex) {

                JOptionPane.showMessageDialog(view, ex.getMessage());
                System.exit(0);
            }
        }
        return classeDAO;
    }

    public ILogDAO carregaDAOLog() {
        ILogDAO classeDAO = null;
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("data/dao.properties");
            properties.load(fis);
            String dao = properties.getProperty("daoLog");

            Class classe = Class.forName(dao);
            Constructor constructor = classe.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();

            classeDAO = (ILogDAO) instance;

        } catch (Exception e) {
            try {
                throw new Exception("Não foi possível carregar a classe: \n\n\t" + e.getMessage()
                        + "\n\n Atribua uma clase válida à chave \"dao\", no arquivo dao.properties ");
            } catch (Exception ex) {

                JOptionPane.showMessageDialog(view, ex.getMessage());
                System.exit(0);
            }
        }
        return classeDAO;
    }

}
