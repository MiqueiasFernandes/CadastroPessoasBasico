/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.log;

/**
 *
 * @author mfernandes
 */
public interface LogStrategy {

    public String[] getLinhas();

    public void append(String mensagem) throws Exception;

}
