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
import pessoas.collection.oserver.Observer;
import pessoas.model.Login;
import pessoas.model.Pessoa;
import pessoas.view.MainView;

/**
 *
 * @author mfernandes
 */
public final class PessoaDAOProxy extends IPessoaDAO {

    private final IPessoaDAO pessoaDAOReal;

    public PessoaDAOProxy(MainView view) throws FileNotFoundException, IOException {
        autenticar();
        pessoaDAOReal = carregaDAOPessoa(view);
    }

    public void autenticar() {
        Login.getInstancia().autenticar();
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
    public boolean add(Pessoa p) throws IOException {
        autenticar();
        return pessoaDAOReal.add(p);
    }

    @Override
    public void addAll(TreeSet pessoas) throws IOException {
        autenticar();
        pessoaDAOReal.addAll(pessoas);
    }

    @Override
    public void addObserver(Observer observer) {
        autenticar();
        pessoaDAOReal.addObserver(observer);
    }

    @Override
    public boolean altera(Pessoa pessoaExistente, String nomeAnterior) throws IOException {
        autenticar();
        return pessoaDAOReal.altera(pessoaExistente, nomeAnterior);
    }

    @Override
    public void carregaPessoas() throws FileNotFoundException {
        autenticar();
        pessoaDAOReal.carregaPessoas();
    }

    @Override
    public boolean contains(Pessoa p) {
        autenticar();
        return pessoaDAOReal.contains(p);
    }

    @Override
    public Pessoa getPessoaByName(String nome) {
        autenticar();
        return pessoaDAOReal.getPessoaByName(nome);
    }

    @Override
    public TreeSet getTreeSet() {
        autenticar();
        return pessoaDAOReal.getTreeSet();
    }

    @Override
    public boolean remove(String nome) throws IOException {
        autenticar();
        return pessoaDAOReal.remove(nome);
    }

}
