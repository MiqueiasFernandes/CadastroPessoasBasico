package pessoas.collection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import pessoas.collection.oserver.Observer;
import pessoas.model.Pessoa;

public final class PessoaDAOTXT extends IPessoaDAO<PessoaDAOTXT> {

    private TreeSet<Pessoa> pessoas = null;
    private final File arquivo;

    private PessoaDAOTXT() throws FileNotFoundException {
        if (pessoas == null) {
            pessoas = new TreeSet<>();
        }
        arquivo = new File("data/pessoas.txt");
        carregaPessoas();
    }

    @Override
    public void addObserver(Observer observer) {
        observadores.add(observer);
    }

    @Override
    public void carregaPessoas() throws FileNotFoundException {
        Scanner scan = new Scanner(arquivo);
        pessoas.clear();
        while (scan.hasNextLine()) {
            String linha = scan.nextLine();
            Scanner scanline = new Scanner(linha);
            scanline.useDelimiter(",");
            while (scanline.hasNext()) {
                String nome = scanline.next();
                String telefone = scanline.next();
                pessoas.add(new Pessoa(nome, telefone));
            }
        }
    }

    @Override
    public boolean add(Pessoa p) throws IOException {
        boolean add = false;
        add = this.pessoas.add(p);
        if (add) {
            FileWriter w = new FileWriter(arquivo, true);
            BufferedWriter bf = new BufferedWriter(w);
            bf.write(p.toString());
            bf.newLine();
            bf.close();
        }
        notifyObservers();
        return add;
    }

    @Override
    public boolean remove(String nome) throws IOException {
        TreeSet<Pessoa> listaTemp = new TreeSet<Pessoa>();
        for (Iterator<Pessoa> it = pessoas.iterator(); it.hasNext();) {
            Pessoa pessoa = it.next();
            if (!pessoa.getNome().equals(nome)) {
                listaTemp.add(pessoa);
            }
        }
        boolean remove = limpaArquivo(arquivo);
        if (remove) {
            addAll(listaTemp);
        }
        return remove;
    }

    @Override
    public boolean altera(Pessoa pessoaExistente, String nomeAnterior) throws IOException {
        TreeSet<Pessoa> listaTemp = new TreeSet<Pessoa>();
        for (Iterator<Pessoa> it = pessoas.iterator(); it.hasNext();) {
            Pessoa pessoa = it.next();
            if (!pessoa.getNome().equals(nomeAnterior)) {
                listaTemp.add(pessoa);
            } else {
                listaTemp.add(pessoaExistente);
            }
        }
        boolean remove = limpaArquivo(arquivo);
        if (remove) {
            addAll(listaTemp);
        }
        return remove;
    }

    @Override
    public void addAll(TreeSet<Pessoa> pessoas) throws IOException {
        FileWriter w = new FileWriter(arquivo, true);
        BufferedWriter bf = new BufferedWriter(w);
        for (Iterator<Pessoa> it = pessoas.iterator(); it.hasNext();) {
            Pessoa pessoa = it.next();
            bf.write(pessoa.toString());
            bf.newLine();
        }
        bf.close();
        carregaPessoas();
        notifyObservers();
    }

    private boolean limpaArquivo(File arquivo) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(arquivo);
        writer.print("");
        writer.flush();
        writer.close();
        return !writer.checkError();
    }

    @Override
    public Pessoa getPessoaByName(String nome) {
        for (Iterator<Pessoa> it = pessoas.iterator(); it.hasNext();) {
            Pessoa pessoa = it.next();
            if (pessoa.getNome().equals(nome)) {
                return pessoa;
            }
        }
        return null;
    }

    @Override
    public boolean contains(Pessoa p) {
        return this.pessoas.contains(p);
    }

    @Override
    public TreeSet<Pessoa> getTreeSet() {
        return pessoas;
    }
}
