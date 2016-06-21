/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.log;

import pessoas.model.Usuario;
import pessoas.presenter.LoginPresenter;

/**
 *
 * @author mfernandes
 */
public final class LogSingleton {

    private static final String logpPath = "data/log";
    private static LogSingleton instancia;
    private ILogDAO logdao;
    private Usuario usuario;
    private LoginPresenter presenter;

    private LogSingleton() {
    }

    public static LogSingleton getInstancia() {
        if (instancia == null) {
            instancia = new LogSingleton();
        }
        return instancia;
    }

    public void setTipoLog(ILogDAO logdao) {
        this.logdao = logdao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static String getLogpPath() {
        return logpPath;
    }

    public boolean estaLogado() {

        if (usuario == null && presenter != null) {
            presenter.login();
        }

        return usuario != null;
    }

    public LoginPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(LoginPresenter presenter) {
        this.presenter = presenter;
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

    public void addUsuario(Usuario usuario) {

    }

}
