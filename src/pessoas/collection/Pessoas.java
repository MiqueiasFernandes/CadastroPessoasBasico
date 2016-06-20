package pessoas.collection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import pessoas.model.Pessoa;

public final class Pessoas {

    private TreeSet<Pessoa> pessoas = null;
    private final File arquivo;

    public Pessoas() throws FileNotFoundException {
        if (pessoas == null) {
            pessoas = new TreeSet<Pessoa>();
        }
        arquivo = new File("data/pessoas.txt");
        carregaPessoas();
    }

    public void carregaPessoas() throws FileNotFoundException {
        Scanner scan = new Scanner(arquivo);
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
      //  arquivo.delete();
    }

    public boolean add(Pessoa p) throws IOException {
        boolean add = this.pessoas.add(p);
        FileWriter w = new FileWriter(arquivo, true);
        BufferedWriter bf = new BufferedWriter(w);
        bf.write(p.toString());
        bf.newLine();
        bf.close();

        return add;
    }

    public void addAll(Collection ps) {
        this.pessoas.addAll(ps);
    }

    public boolean contains(Pessoa p) {

        return this.pessoas.contains(p);
    }

    public TreeSet<Pessoa> getTreeSet() {
        return pessoas;
    }

    public Iterator<Pessoa> iterator() {
        return this.pessoas.iterator();
    }
}
