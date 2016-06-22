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

public class LogDAOXML implements ILogDAO {

    private File arquivo;

    @Override
    public void carregaArquivo(String path) throws IOException {
        if (arquivo == null) {
            arquivo = new File(path + ".xml");
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
        }
    }

    @Override
    public void append(String mensagem) throws Exception {
        if (arquivo != null) {
            FileWriter w = new FileWriter(arquivo, true);
            BufferedWriter bf = new BufferedWriter(w);
            bf.write(mensagem);
            bf.newLine();
            bf.close();
        } else {
            throw new IOException("Arquivo não foi carregado.");
        }
    }

    @Override
    public String toString() {
        return "Arquivo XML";
    }

    @Override
    public String getNomeCanonico() {
        return "pessoas.log.LogDAOXML";
    }

}
