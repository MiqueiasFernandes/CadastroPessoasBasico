/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.log;

import java.io.IOException;

/**
 *
 * @author mfernandes
 */
public interface ILogDAO {

    public void append(String mensagem) throws Exception;

    public void carregaArquivo(String path) throws IOException;

    public String getNomeCanonico();
}
