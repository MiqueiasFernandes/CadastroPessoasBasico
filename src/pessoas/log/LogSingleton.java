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
import pessoas.model.Login;
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
        log += " por " + Login.getInstancia().getUsuarioLogado().getNome() + " em ";
        log += getTime();
        this.logdao.append(log);
    }

    public String getTime() {
        Locale ptBR = new Locale("pt", "BR");
        SimpleDateFormat formatador
                = new SimpleDateFormat("dd'/'MM'/'yyyy' 'HH':'mm':'ss Z", ptBR);
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

    public void incluirContatoLog() {

    }

    public void excluirContatoLog() {

    }

    public void consultarContatoLog() {

    }

    public void importarContatoLog() {

    }

    public void exportarContatoLog() {

    }

    public void corrigirContatoLog() {

    }

    public void alterarTipoLog() {

    }

    public void addUsuario(Usuario usuario) throws Exception {
        String log = "novo usuario ";
        Usuario u = Login.getInstancia().getUsuario();
        if (u != null) {
            log += usuario + " adicionado por ";
            log += Login.getInstancia().getUsuarioLogado().getNome();
        }
        log += " em " + getTime();
        logdao.append(log);
    }

}
