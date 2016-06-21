/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogDAOTXT implements ILogDAO {

    private File arquivo;

    public LogDAOTXT() {
        arquivo = new File(LogSingleton.getInstancia().getLogpPath() + ".txt");
    }

    @Override
    public void append(String mensagem) throws IOException {
        FileWriter w = new FileWriter(arquivo, true);
        BufferedWriter bf = new BufferedWriter(w);
        bf.write(mensagem);
        bf.newLine();
        bf.close();
    }

    @Override
    public String toString() {
        return "Arquivo de texto";
    }

}
