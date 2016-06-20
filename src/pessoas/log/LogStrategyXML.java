/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.log;

public class LogStrategyXML implements LogStrategy {

    @Override
    public String[] getLinhas() {
        return null;
    }

    @Override
    public void append(String mensagem) {

    }

    @Override
    public String toString() {
        return "Arquivo XML";
    }
}
