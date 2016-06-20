/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LogStrategyTEXT implements LogStrategy {

    private final File arquivo;
    private ArrayList<String> linhas;

    public LogStrategyTEXT() throws FileNotFoundException {
        arquivo = new File(LogSingleton.getInstancia().getLogpPath() + ".txt");
        linhas = new ArrayList<>();
        carregaLog();
    }

    public void carregaLog() throws FileNotFoundException {
        Scanner scanner = new Scanner(arquivo);
        while (scanner.hasNextLine()) {
            linhas.add(scanner.nextLine());
        }
    }

    @Override
    public String[] getLinhas() {
        return linhas.toArray(new String[0]);
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
