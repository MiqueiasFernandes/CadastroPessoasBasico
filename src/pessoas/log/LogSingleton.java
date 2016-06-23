/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import pessoas.model.LoginSingleton;
import pessoas.model.Pessoa;
import pessoas.model.Usuario;

/**
 *
 * @author mfernandes
 */
public final class LogSingleton {

    private static final String logpPath = "data/log";
    private static LogSingleton instancia;
    private ILogDAO logdao;

    private LogSingleton() throws IOException, FileNotFoundException, ClassNotFoundException,
            NoSuchMethodException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        carregaDAOLog();
        carregaArquivo();
    }

    public static LogSingleton getInstancia()
            throws IOException, ClassNotFoundException, FileNotFoundException,
            NoSuchMethodException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        if (instancia == null) {
            instancia = new LogSingleton();
        }
        return instancia;
    }

    public void setLogdao(ILogDAO logdao) throws FileNotFoundException, IOException, Exception {
        this.logdao = logdao;
        carregaArquivo();
        ///persistir em arquivo
        String file = "data/dao.properties";
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));
        properties.put("daoLog", logdao.getNomeCanonico());
        properties.store(new FileOutputStream(file), "salvo automaticamente.");
        ///escrever log
        String log = "Tipo de arquivo de log alterado para " + this.logdao;
        log += " por " + LoginSingleton.getInstancia().getUsuarioLogado().getNome() + " em ";
        log += getTime();
        this.logdao.append(log);
    }

    public String getTime() {
        Locale ptBR = new Locale("pt", "BR");
        SimpleDateFormat formatador
                = new SimpleDateFormat("'('dd'/'MM'/'yyyy' 'HH':'mm':'ss')'", ptBR);
        String time = formatador.format(Calendar.getInstance().getTime());
        return time;
    }

    public void carregaDAOLog()
            throws FileNotFoundException, IOException, ClassNotFoundException,
            NoSuchMethodException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        ILogDAO classeDAO;
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("data/dao.properties");
        properties.load(fis);
        String dao = properties.getProperty("daoLog");

        Class classe = Class.forName(dao);
        Constructor constructor = classe.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();

        classeDAO = (ILogDAO) instance;
        logdao = classeDAO;
        carregaArquivo();
    }

    public void carregaArquivo() throws IOException {
        logdao.carregaArquivo(logpPath);
    }

    String getUsertTime() throws Exception {
        return " por " + LoginSingleton.getInstancia().getUsuarioLogado().getNome()
                + " em " + getTime();
    }

    public void importaContatos(int sucesso, int incompletos) throws Exception {
        this.logdao.append("Importação realizada: "
                + sucesso + " contatos com sucesso, "
                + incompletos + " incompletos "
                + getUsertTime());
    }

    public void exportaContatos(int sucesso) throws Exception {
        this.logdao.append("Importação realizada: "
                + sucesso + " contatos exportados. "
                + getUsertTime());
    }

    public void importaContatosFalha(Exception ex) throws Exception {
        this.logdao.append(
                "Falha " + ex.getMessage() + " durante a importação " + getUsertTime()
        );
    }

    public void exportaContatosFalha(Exception ex) throws Exception {
        this.logdao.append(
                "Falha " + ex.getMessage() + " durante a exportação " + getUsertTime()
        );
    }

    public void gerenciaContatoComSucesso(String operacao, String pessoa) throws Exception {
        this.logdao.append(operacao
                + " do contato " + pessoa
                + getUsertTime());
    }

    public void gerenciaContatoComInSucesso(String operacao, String pessoa, Exception falha) throws Exception {
        this.logdao.append("Ocorreu a falha " + falha.getMessage()
                + " ao realizar a " + operacao
                + " do contato " + pessoa
                + getUsertTime());
    }

    public void incluirContatoLog(String pessoa, Exception falha) throws Exception {
        if (falha == null) {
            gerenciaContatoComSucesso("inclusão", pessoa);
        } else {
            gerenciaContatoComInSucesso("inclusão", pessoa, falha);
        }
    }

    public void excluirContatoLog(String pessoa, Exception falha) throws Exception {
        if (falha == null) {
            gerenciaContatoComSucesso("exclusão", pessoa);
        } else {
            gerenciaContatoComInSucesso("exclusão", pessoa, falha);
        }
    }

    public void consultarContatoLog(String pessoa, Exception falha) throws Exception {
        if (falha == null) {
            gerenciaContatoComSucesso("consulta", pessoa);
        } else {
            gerenciaContatoComInSucesso("consulta", pessoa, falha);
        }
    }

    public void corrigirContatoLog(String pessoa, Exception falha) throws Exception {
        if (falha == null) {
            gerenciaContatoComSucesso("correção", pessoa);
        } else {
            gerenciaContatoComInSucesso("correção", pessoa, falha);
        }
    }

    public void addUsuario(Usuario usuario) throws Exception {
        String log = "novo usuario ";
        Usuario u = LoginSingleton.getInstancia().getUsuario();
        if (u != null) {
            log += usuario + " adicionado por ";
            log += LoginSingleton.getInstancia().getUsuarioLogado().getNome();
        }
        log += " em " + getTime();
        logdao.append(log);
    }

    public void loginUsuario(Usuario usuario, boolean saiu) throws Exception {
        String log = "usuario ";
        log += usuario + (saiu ? " fez logoff " : " entrou ");
        log += " em " + getTime();
        logdao.append(log);
    }

}
