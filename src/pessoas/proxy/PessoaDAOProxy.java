/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.proxy;

import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import pessoas.collection.IPessoaDAO;
import pessoas.collection.observer.Observer;
import pessoas.log.LogSingleton;
import pessoas.model.LoginSingleton;
import pessoas.model.Pessoa;
import pessoas.view.MainView;

/**
 *
 * @author mfernandes
 */
public final class PessoaDAOProxy extends IPessoaDAO {

    private final IPessoaDAO pessoaDAOReal;

    public PessoaDAOProxy(MainView view) throws FileNotFoundException, IOException, Exception {
        autenticar();
        pessoaDAOReal = carregaDAOPessoa(view);
    }

    public void autenticar() throws Exception {
        LoginSingleton.getInstancia().autenticar();
    }

    public IPessoaDAO carregaDAOPessoa(Component view) {
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

        } catch (IOException | ClassNotFoundException | NoSuchMethodException |
                SecurityException | InstantiationException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e) {
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

    @Override
    public boolean add(Pessoa pessoa) throws Exception {
        boolean add = false;
        try {
            autenticar();
            add = pessoaDAOReal.add(pessoa);
            if (add) {
                LogSingleton.getInstancia().incluirContatoLog(pessoa.getNome(), null);
            }
        } catch (Exception ex) {
            LogSingleton.getInstancia().incluirContatoLog(pessoa.getNome(), ex); ///log do erro
            throw new Exception(ex); ///apresentar para o usuario o erro
        }
        return add;
    }

    @Override
    public void addAll(TreeSet pessoas) throws Exception {
        try {
            autenticar();
            pessoaDAOReal.addAll(pessoas);

            //importacao
        } catch (Exception ex) {
            ///erro na importacao
            throw new Exception(ex);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        pessoaDAOReal.addObserver(observer);
    }

    @Override
    public boolean altera(Pessoa pessoaExistente, String nomeAnterior) throws Exception {
        autenticar();
        return pessoaDAOReal.altera(pessoaExistente, nomeAnterior);
    }

    @Override
    public void carregaPessoas() throws Exception {
        autenticar();
        pessoaDAOReal.carregaPessoas();
    }

    @Override
    public boolean contains(Pessoa pessoa) throws Exception {
        boolean ct = false;
        try {
            ct = pessoaDAOReal.contains(pessoa);
            LogSingleton.getInstancia().consultarContatoLog(pessoa.getNome(), null);
        } catch (Exception ex) {
            LogSingleton.getInstancia().consultarContatoLog(pessoa.getNome(), ex);
            throw new Exception(ex);
        }
        return ct;
    }

    @Override
    public Pessoa getPessoaByName(String nome) throws Exception {
        Pessoa pessoa;
        try {
            pessoa = pessoaDAOReal.getPessoaByName(nome);
            LogSingleton.getInstancia().consultarContatoLog(nome, null);
        } catch (Exception ex) {
            LogSingleton.getInstancia().consultarContatoLog(nome, ex);
            throw new Exception(ex);
        }
        return pessoa;
    }

    @Override
    public TreeSet getTreeSet() throws Exception {
        TreeSet treeSet = null;
        try {
            treeSet = pessoaDAOReal.getTreeSet();
            autenticar();
            LogSingleton.getInstancia().consultarContatoLog(" TODOS CONTATOS ", null);
        } catch (Exception ex) {
            LogSingleton.getInstancia().consultarContatoLog(" TODOS CONTATOS ", ex);
            throw new Exception(ex);
        }
        return treeSet;
    }

    @Override
    public boolean remove(String nome) throws Exception {
        boolean rm = false;
        try {
            autenticar();
            rm = pessoaDAOReal.remove(nome);
            if (rm) {
                LogSingleton.getInstancia().excluirContatoLog(nome, null);
            }
        } catch (Exception ex) {
            LogSingleton.getInstancia().excluirContatoLog(nome, ex);
            throw new Exception(ex);
        }
        return rm;
    }

}
