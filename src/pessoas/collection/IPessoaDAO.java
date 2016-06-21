package pessoas.collection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;
import pessoas.collection.oserver.Observer;
import pessoas.model.Pessoa;

public abstract class IPessoaDAO<T> {

    protected ArrayList<Observer> observadores = new ArrayList<>();

    public abstract boolean add(Pessoa p) throws IOException;

    public abstract void addAll(TreeSet<Pessoa> pessoas) throws IOException;

    public abstract void addObserver(Observer observer);

    public abstract boolean altera(Pessoa pessoaExistente, String nomeAnterior) throws IOException;

    public abstract void carregaPessoas() throws FileNotFoundException;

    public abstract boolean contains(Pessoa p);

    public abstract Pessoa getPessoaByName(String nome);

    public abstract TreeSet<Pessoa> getTreeSet();

    public void notifyObservers() {
        observadores.stream().forEach((observer) -> {
            observer.atualiza(this.getTreeSet());
        });
    }

    public abstract boolean remove(String nome) throws IOException;
}
